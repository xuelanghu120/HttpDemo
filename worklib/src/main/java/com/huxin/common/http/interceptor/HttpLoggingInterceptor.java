package com.huxin.common.http.interceptor;


import com.huxin.common.http.utils.UrlEncodeUtils;
import com.huxin.common.utils.Logger;
import com.huxin.common.utils.MMLogger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;


/**
 * OkHttp拦截器，主要用于打印日志
 */
public class HttpLoggingInterceptor implements Interceptor {
    private static final String TAG = "httpwork";

    private static final Charset UTF8 = Charset.forName("UTF-8");


    public HttpLoggingInterceptor() {
    }


    public void log(String message) {
        Logger.v(TAG, message);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!MMLogger.isLog()) {
            return chain.proceed(request);
        }

        //请求日志拦截
        logForRequest(request, chain.connection());

        //执行请求，计算请求时间
        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            log("<-- HTTP FAILED: " + e);
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        //响应日志拦截
        return logForResponse(response, tookMs);
    }

    private void logForRequest(Request request, Connection connection) throws IOException {
        boolean logBody = true;
        boolean logHeaders = true;
        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;

        try {
            String requestStartMessage = "--> " + request.method() +"\t" + " 地址：" + request.url() + ' ' + protocol;
            log(requestStartMessage);

            if (logHeaders) {
                Headers headers = request.headers();
                StringBuilder builder = new StringBuilder();
                builder.append("headers:");
                for (int i = 0, count = headers.size(); i < count; i++) {
                    builder.append("\t"  + headers.name(i) + ": " + headers.value(i));
                }
                log(builder.toString());

                if (logBody && hasRequestBody) {
                    if (isPlaintext(requestBody.contentType())) {
                        bodyToString(request);
                    } else {
                        log("\tbody: maybe [file part] , too large too print , ignored!");
                    }
                }
            }
        } catch (Exception e) {
            com.orhanobut.logger.Logger.e(e);
        } finally {
            log("--> END " + request.method());
        }
    }

    private Response logForResponse(Response response, long tookMs) {
        Response.Builder builder = response.newBuilder();
        Response clone = builder.build();
        ResponseBody responseBody = clone.body();
        boolean logBody = true;
        boolean logHeaders = true;

        try {
            log("<-- " + clone.code() + ' ' + clone.message() + ' ' + clone.request().url() + " (" + tookMs + "ms）");
            if (logHeaders) {
                Headers headers = clone.headers();
                StringBuilder builderResponse = new StringBuilder();
                builderResponse.append("headers:");
                for (int i = 0, count = headers.size(); i < count; i++) {
                    builderResponse.append("\t"  + headers.name(i) + ": " + headers.value(i));
                }
                log(builderResponse.toString());
                if (logBody && HttpHeaders.hasBody(clone)) {
                    if (isPlaintext(responseBody.contentType())) {
                        String body = responseBody.string();
                        log("\tbody:" + body);
                        responseBody = ResponseBody.create(responseBody.contentType(), body);
                        return response.newBuilder().body(responseBody).build();
                    } else {
                        log("\tbody: maybe [file part] , too large too print , ignored!");
                    }
                }
            }
        } catch (Exception e) {
            com.orhanobut.logger.Logger.e(e);
        } finally {
            log("<-- END HTTP");
        }
        return response;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    private static boolean isPlaintext(MediaType mediaType) {
        if (mediaType == null) return false;
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        String subtype = mediaType.subtype();
        if (subtype != null) {
            subtype = subtype.toLowerCase();
            if (subtype.contains("x-www-form-urlencoded") ||
                    subtype.contains("json") ||
                    subtype.contains("xml") ||
                    subtype.contains("html")) //
                return true;
        }
        return false;
    }

    private void bodyToString(Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            Charset charset = UTF8;
            MediaType contentType = copy.body().contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            log("\tbody:" + UrlEncodeUtils.Utf8URLdecode(buffer.readString(charset)) );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}