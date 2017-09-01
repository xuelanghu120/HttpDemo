package com.huxin.common.http;


import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.huxin.common.db.DatabaseHelper;
import com.huxin.common.db.dao.CacheEntityDao;
import com.huxin.common.db.entity.NetWorkRsultEntity;
import com.huxin.common.http.builder.MapParamsConverter;
import com.huxin.common.http.builder.ParamEntity;
import com.huxin.common.http.builder.URLBuilder;
import com.huxin.common.http.builder.URLBuilderFactory;
import com.huxin.common.http.builder.URLBuilderHelper;
import com.huxin.common.http.callback.NetworkCallback;
import com.huxin.common.http.responser.AbstractResponser;
import com.huxin.common.http.upload.ProgressHelper;
import com.huxin.common.http.upload.UIProgressListener;
import com.huxin.common.utils.MMLogger;

import org.json.JSONObject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.RequestBody;


/**
 * 网络请求类
 */
public class HttpWork {

    private final String TAG = HttpWork.class.getSimpleName();

    private CacheEntityDao cacheEntityDao;
    private Context context;

    public synchronized static HttpWork getInstace(Context context) {
        HttpWork mInstance = new HttpWork(context);
        return mInstance;
    }

    private HttpWork(Context context) {
        this.context = context;
        this.cacheEntityDao = new CacheEntityDao(context);
    }

    public <T extends AbstractResponser> Flowable<T> get(ParamEntity paramEntity, final Class<T> rspClass, NetworkCallback<T> callback, boolean isNeedCache) {
        return req(paramEntity, rspClass, callback, null, false, isNeedCache);
    }

    /**
     * 正常的post请求
     *
     * @param paramEntity
     * @param rspClass
     * @param callback
     * @param isNeedCache
     * @param <T>
     * @return
     */
    public <T extends AbstractResponser> Flowable<T> post(ParamEntity paramEntity, final Class<T> rspClass, NetworkCallback<T> callback, boolean isNeedCache) {
        return req(paramEntity, rspClass, callback, null, true, isNeedCache);
    }

    /**
     * 带进度条的post请求，用于上传和下载
     *
     * @param paramEntity               请求体
     * @param rspClass                  返回实体类
     * @param callback                  请求回调
     * @param uiProgressRequestListener 进度
     * @param isNeedCache               是否缓存
     * @param <T>                       泛型
     * @return observer的返回体
     */
    public <T extends AbstractResponser> Flowable<T> post(ParamEntity paramEntity, final Class<T> rspClass, NetworkCallback<T> callback, final UIProgressListener uiProgressRequestListener, boolean isNeedCache) {
        return req(paramEntity, rspClass, callback, uiProgressRequestListener, true, isNeedCache);
    }

    public <T extends AbstractResponser> Flowable<T> req(ParamEntity paramEntity, final Class<T> rspClass, final NetworkCallback<T> callback, final UIProgressListener uiProgressRequestListener, final boolean isPost, final boolean isNeedCache) {
        return Flowable.just(paramEntity)
                .subscribeOn(Schedulers.computation())
                //网络请求前获取 参数
                .map(new Function<ParamEntity, URLBuilder>() {
                    @Override
                    public URLBuilder apply(@NonNull ParamEntity paramEntity) {
                        URLBuilder builder = URLBuilderFactory.build(paramEntity);
                        return builder;
                    }
                })
                //请求网络
                .flatMap(new Function<URLBuilder, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(URLBuilder urlBuilder) {
                        return reqOKhttp(urlBuilder, rspClass, callback, uiProgressRequestListener, isPost, isNeedCache);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                //处理返回体
                .doOnNext(new Consumer<T>() {
                              @Override
                              public void accept(@NonNull T t) throws Exception {
                                  if (null != callback) {
                                      if (t.isSuccess) {
                                          callback.onSucessed(t);
                                      } else if (!t.isCache) {
                                          callback.onFailed(t.errorCode, t.errorMessage);
                                      }
                                  }
                              }
                          }
                );
    }

    /**
     * 请求网络数据返回rsp对象
     *
     * @param builder
     * @param rspClass
     * @param callback
     * @param uiProgressRequestListener
     * @param isPost
     * @param isNeedCache
     * @param <T>
     * @return
     */
    private <T extends AbstractResponser> Flowable<T> reqOKhttp(URLBuilder builder, final Class<T> rspClass,
                                                                final NetworkCallback<T> callback,
                                                                final UIProgressListener uiProgressRequestListener, boolean isPost, boolean isNeedCache) {
        final Flowable<NetWorkRsultEntity> source;
        if (isNeedCache) {
            source = Flowable.merge(reqCache(builder), reqNetWork(callback, builder, rspClass, uiProgressRequestListener, isPost, isNeedCache));
        } else {
            source = reqNetWork(callback, builder, rspClass, uiProgressRequestListener, isPost, isNeedCache);
        }
        final Flowable<T> observable = source
                .map(new Function<NetWorkRsultEntity, T>() {
                    @Override
                    public T apply(NetWorkRsultEntity s) {
                        T rsp = null;
                        try {
                            rsp = rspClass.newInstance();
                            rsp.parser(s.resultJsonStr);
                            rsp.isCache = s.isCache;
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }

                        return rsp;
                    }
                });
        return observable;
    }


    /**
     * 查询网络数据
     *
     * @param builder
     */
    private <T extends AbstractResponser> Flowable<NetWorkRsultEntity> reqNetWork(final Object tag, final URLBuilder builder, final Class<T> rspClass, final UIProgressListener uiProgressRequestListener, final boolean isPost, final boolean isCache) {
        Flowable<NetWorkRsultEntity> observable = Flowable.create(new FlowableOnSubscribe<NetWorkRsultEntity>() {
            @Override
            public void subscribe(final FlowableEmitter<NetWorkRsultEntity> subscriber) {
                String resultJsonStr = "";
                if (isPost) {
                    RequestBody body = null;
                    //stringReq
                    if (URLBuilder.REQ_TYPE_JSON == builder.getReqType()) {
                        //JSON格式请求
                        body = MapParamsConverter.map2ForJSON(builder.getParams());
                    } else if (URLBuilder.REQ_TYPE_KV == builder.getReqType()) {
                        //KV格式请求
                        body = MapParamsConverter.map2ForBody(builder.getParams());
                    } else if (URLBuilder.REQ_TYPE_FILE == builder.getReqType()) {
                        //KV格式请求
                        body = MapParamsConverter.map2ForMultBody(builder.getParams());
                        if (null != uiProgressRequestListener) {
                            //判断是否有上传进度listener
                            body = ProgressHelper.addProgressRequestListener(body, uiProgressRequestListener);
                        }
                    }
//                    StringRequest stringRequest = new StringRequest(builder.getUrl(), new Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            NetWorkRsultEntity cacheEntity = new NetWorkRsultEntity();
//                            if (!TextUtils.isEmpty(response.toString())){
//                                cacheEntity.resultJsonStr = response.toString();
//                            }
//                            if (!subscriber.isCancelled())
//                                subscriber.onNext(cacheEntity);
//                        }
//                    }, new com.android.volley.Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            if (!subscriber.isCancelled())
//                                subscriber.onNext(null);
//                        }
//                    });
//                    VolleyHttpWork.getInstance(context).stringReq(stringRequest);
                    final NetWorkRsultEntity cacheEntity = new NetWorkRsultEntity();
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,"http://m.weather.com.cn/data/101010100.html", null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    if (!TextUtils.isEmpty(response.toString())) {
                                        cacheEntity.resultJsonStr = response.toString();
                                    }
                                    if (!subscriber.isCancelled())
                                        subscriber.onNext(cacheEntity);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    if (!subscriber.isCancelled())
                                        subscriber.onNext(cacheEntity);
                                }
                            });

                    VolleyHttpWork.getInstance(context).jsonReq(jsonObjectRequest);


                    //同步
//                    resultJsonStr = OkHttpWork.stringReq(tag, builder.getUrl(), body);
                    //异步
//                    OkHttpWork.stringReq(tag, builder.getUrl(), body, new HttpCallBack() {
//                        @Override
//                        public void onFailure(Call call, IOException e) {
//                            if (!subscriber.isCancelled())
//                            subscriber.onNext(null);
//                        }
//
//                        @Override
//                        public void onResponse(Call call, Response response) throws IOException {
//                            NetWorkRsultEntity cacheEntity = new NetWorkRsultEntity();
//                            if (!TextUtils.isEmpty(response.toString())){
//                                cacheEntity.resultJsonStr = response.toString();
//                            }
//                            if (!subscriber.isCancelled())
//                            subscriber.onNext(cacheEntity);
//                        }
//                    });
                } else {
                    //get
                    String urlKey = URLBuilderHelper.getUrlStr(builder.getUrl(), builder.getParams());
                    resultJsonStr = OkHttpWork.get(tag, urlKey);
                }
//                NetWorkRsultEntity cacheEntity = new NetWorkRsultEntity();
//                if (!TextUtils.isEmpty(resultJsonStr)){
//                    cacheEntity.resultJsonStr = resultJsonStr;
//                }
//                cacheEntity.isCache = false;
//
//                subscriber.onNext(cacheEntity);
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.computation())
                .doOnNext(new Consumer<NetWorkRsultEntity>() {
                    @Override
                    public void accept(NetWorkRsultEntity entity) {
                        if (isCache) {
                            saveCache(builder, entity.resultJsonStr, rspClass);
                        }
                    }
                });
        return observable;
    }

    private <T extends AbstractResponser> void saveCache(URLBuilder builder, String s, Class<T> rspClass) {
        if (!TextUtils.isEmpty(s)) {
            try {
                T rsp = rspClass.newInstance();
                rsp.parseHeader(s);
                if (rsp.isSuccess) {
                    NetWorkRsultEntity entity = createCacheEntity(builder, s);
                    cacheEntityDao.saveItem(entity);
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * 查询本地DB缓存
     *
     * @param builder
     */
    private Flowable<NetWorkRsultEntity> reqCache(final URLBuilder builder) {
        Flowable<NetWorkRsultEntity> observable = Flowable.create(new FlowableOnSubscribe<NetWorkRsultEntity>() {
            @Override
            public void subscribe(FlowableEmitter<NetWorkRsultEntity> subscriber) {
                String urlKey = URLBuilderHelper.getUrlStr(builder.getUrl(), builder.getCacheKeyParams());
                NetWorkRsultEntity cacheEntity = cacheEntityDao.queryForID(urlKey);
                if (null == cacheEntity) {
                    cacheEntity = new NetWorkRsultEntity();
                }
                cacheEntity.isCache = true;
                MMLogger.logv(TAG, "reqCache: " + cacheEntity.resultJsonStr);
                subscriber.onNext(cacheEntity);
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.computation());
        return observable;
    }

    private NetWorkRsultEntity createCacheEntity(URLBuilder builder, String result) {
        NetWorkRsultEntity cacheEntity = new NetWorkRsultEntity();
        String urlKey = URLBuilderHelper.getUrlStr(builder.getUrl(), builder.getCacheKeyParams());
        cacheEntity.url = urlKey;
        cacheEntity.resultJsonStr = result;
        return cacheEntity;
    }

    /**
     * 清除缓存
     */
    public void clearCache() {
        DatabaseHelper.getInstance(context).clearDb();
    }

    /**
     * 下载文件
     *
     * @param url
     * @param filePath
     * @param progressListener
     * @return
     */
    public Call downLoad(String url, String filePath, ProgressListener progressListener) {
        return OkHttpWork.downLoad(url, filePath, progressListener);
    }

    public static void cancel(Object... tags) {
        for (Object tag : tags) {
            OkHttpWork.cancel(tag);
        }
    }
}
