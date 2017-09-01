package com.huxin.xinpiao;


public class HXConfig {

    /***
     * 添加环境，需要修改 ENVIRONMENT_KEY，API_HOST_ARRAY，
     *                   H5_HOST_ARRAY，IMG_HOST_ARRAY
     *                   缺一不可
     */

    //总共9个8为正式环境
//    public static final int ENVIRONMENT_KEY = 8;//EnvironmentUtils.getEnvironment();
    public static final int ENVIRONMENT_KEY = EnvironmentUtils.getEnvironment();

    //开发环境
    public static final String API_HOST_TRUNK = "https://app.ibicid.com";
    public static final String H5_HOST_TRUNK = "https://xp.ibicid.com";

    //开发环境1
    public static final String API_HOST_TRUNK_1 = "https://app1.ibicid.com";
    public static final String H5_HOST_TRUNK_1 = "https://xp1.ibicid.com";

    //开发环境2
    public static final String API_HOST_TRUNK_2 = "https://app2.ibicid.com";
    public static final String H5_HOST_TRUNK_2 = "https://xp2.ibicid.com";

    //测试环境
    public static final String API_HOST_QA = "https://app.baorendai.cn";
    public static final String H5_HOST_QA = "https://xp.baorendai.cn";

    //测试环境
    public static final String API_HOST_QA_1 = "https://app1.baorendai.cn";
    public static final String H5_HOST_QA_1 = "https://xp1.baorendai.cn";


    //测试环境
    public static final String API_HOST_QA_2 = "https://app2.baorendai.cn";
    public static final String H5_HOST_QA_2 = "https://xp2.baorendai.cn";

    //测试环境
    public static final String API_HOST_QA_3 = "https://app3.baorendai.cn";
    public static final String H5_HOST_QA_3 = "https://xp3.baorendai.cn";


    //自动环境
    public static final String API_HOST_AUTO = "http://autoapp.baorendai.cn";
    public static final String H5_HOST_AUTO = "http://autoxp.baorendai.cn";



    //预发布环境
    public static final String API_HOST_PRE = "https://preapp.ibicid.com";
    public static final String H5_HOST_PRE = "https://prexp.ibicid.com";

    //正式环境
    public static final String API_HOST_RELEASE = "https://app.17huxin.com";
    public static final String H5_HOST_RELEASE = "https://xp.17huxin.com";

    public static final String[] API_HOST_ARRAY = {API_HOST_TRUNK, API_HOST_TRUNK_1, API_HOST_TRUNK_2, API_HOST_QA, API_HOST_QA_1, API_HOST_QA_2, API_HOST_QA_3, API_HOST_AUTO,API_HOST_PRE, API_HOST_RELEASE};
    public static final String[] H5_HOST_ARRAY = {H5_HOST_TRUNK, H5_HOST_TRUNK_1, H5_HOST_TRUNK_2, H5_HOST_QA, H5_HOST_QA_1, H5_HOST_QA_2, H5_HOST_QA_3,H5_HOST_AUTO, H5_HOST_PRE, H5_HOST_RELEASE};

    public static final String API_HOST = API_HOST_ARRAY[ENVIRONMENT_KEY];
    public static final String H5_HOST = H5_HOST_ARRAY[ENVIRONMENT_KEY];


    //图片线上环境
    public static final String IMG_HOST_RELEASE = "https://up.17huxin.com/";
    //图片非线上环境
    public static final String IMG_HOST_UNRELEASE = "https://up.baorendai.cn/";
    public static final String[] IMG_HOST_ARRAY = {IMG_HOST_UNRELEASE, IMG_HOST_UNRELEASE, IMG_HOST_UNRELEASE,IMG_HOST_UNRELEASE, IMG_HOST_UNRELEASE, IMG_HOST_UNRELEASE, IMG_HOST_UNRELEASE, IMG_HOST_UNRELEASE,IMG_HOST_UNRELEASE, IMG_HOST_RELEASE};
    public static final String IMG_HOST = IMG_HOST_ARRAY[ENVIRONMENT_KEY];


}
