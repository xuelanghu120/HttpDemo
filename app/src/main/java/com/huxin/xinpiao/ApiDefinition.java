package com.huxin.xinpiao;

/**
 * 网络请求url
 * Created by 56417 on 2016/9/13.
 */
public class ApiDefinition {
    //同步服务器时间
    public static final String URL_UPDATETIME = "/FenQi/Time/v1";
    //注册
    public static final String URL_REGISTER = "/FenQi/Reg/v1";
    //登录
    public static final String URL_LOGIN = "/FenQi/Login/v1";
    //用户账户信息
    public static final String URL_USERACCOUNTINFO = "/FenQi/UserAccountInfo/v1";
    //忘记密码/重置密码
    public static final String URL_RESET = "/FenQi/Reset/v1";
    //发送验证码
    public static final String URL_SENDSMS = "/FenQi/RegVCode/v1";
    //设备id
    public static final String URL_DEVICEID = "/FenQi/Device/v1";

    //提交联系人
    public static final String URL_CONTACTER_SUBMIT = "/FenQi/AddContacters/v1";
    //获取联系人
    public static final String URL_GET_CONTACTER = "/FenQi/Contacters/v1";
    //联系人上传
    public static final String URL_PHONEBOOK = "/FenQi/Phonebook/v1";
    //退出
    public static final String URL_LOGOUT = "/FenQi/Logout/v1";
    //信息
    public static final String URL_MESSAGE = "/FenQi/Message/v1/type/listview";
    //主页面
    public static final String URL_MAIN = "/FenQi/Main/v2";
    //运营消息接口
    public static final String URL_MAIN_OPERATING = "/FenQi/Message/v1/type/operating";
    //还款===========================================================
    //还款列表
//    public static final String URL_REPAY = "/FenQi/RepayList/v1";
    public static final String URL_REPAY = "/FenQi/RepayList/v2";
    //获取支付方式
//    public static final String URL_REPAY_METHOD = "/FenQi/RepayMethod/v3";
    public static final String URL_REPAY_METHOD = "/FenQi/RepayMethod/v4";//134
    //还款短信验证码
    public static final String URL_REPAY_SENDMESS = "/FenQi/RepaySendMess/v1";
    //提交订单有验证码
//    public static final String URL_REPAY_SUBMITMESS = "/FenQi/RepaySubmitMess/v1";//提前结清132之前
    public static final String URL_REPAY_SUBMITMESS = "/FenQi/RepaySubmitMess/v2";//提前花开134之后
    //信托支付，不需要短信验证的支付
    public static final String PAY_NOT_NEED_SMS = "/FenQi/RepaySubmitMess/v2";//和上面是同一个地址

    //轮询支付结果,返回true或者false
    public static final String URL_REPAY_CHECKRESULT = "/FenQi/RepayCheckResult/v1";
    //轮询支付结果成功失败
    public static final String URL_REPAY_RESULT = "/FenQi/RepayResult/v4";
    //阿里支付
//    public static final String URL_ALIPAY_V2 = "/FenQi/RepayAlipay/v1";
    public static final String URL_ALIPAY_V2 = "/FenQi/RepayAlipay/v2";//提前花开134之后
    //提前还清发起申请还款
//    public static final String ADVANCE_REPAY = "/FenQi/RepayLaunch/type/settle/v1";
    public static final String ADVANCE_REPAY = "/FenQi/RepayLaunch/type/settle/v2";
    //正常还清发起申请还款
//    public static final String REPAY_LAUNCH_NORMAL = "/FenQi/RepayWithMess/v1";//提前结清132之前
    public static final String REPAY_LAUNCH_NORMAL = "/FenQi/RepayWithMess/v2";//提前花开134之后
    //阿里支付结果上传
    public static final String URL_ALIPAY_RESULT = "/Pay/AlipayResult/v1";
    //还款===========================================================



    //消息数量
    public static final String URL_MESSAGE_COUNT = "/FenQi/Message/v1/type/count";
    //根据push消息id得到action
    public static final String URL_MESSAGE_ACTION = "/FenQi/Message/v1/type/url";
    //我的订单
    public static final String URL_MY_ORDERS = "/FenQi/GetApplyRecords/v1";
    //账户明细
    public static final String URL_ACCOUNT_DETAILS = "/FenQi/AccountDetails/v1";
    //还款记录
    public static final String URL_REPAY_DETAILS = "/FenQi/RepayHistory/v1";
    //分期记录
    public static final String URL_BORROW_DETAILS = "/FenQi/LoanList/v1";
    //App更新push token
    public static final String URL_PUSH_TOKEN = "/FenQi/ClientInfo/v1/type/set_push_token";
    //启动日志
    public static final String URL_STARTUP_LOG = "/FenQi/ClientInfo/v1/type/set_startup_log";
    //上传头像
    public static final String URL_UPDATEACCOUNTINFO = "/FenQi/UpdateAccountInfo/v1";
    //获取七牛上传token
    public static final String URL_QINIU_TOKEN = "/FenQi/UploadToken/v1";
    //还款详情（还款订单的详细信息）
//    public static final String REPAY_HISTORY_DETAIL = "/FenQi/RepayHistoryDetail/v1";
    public static final String REPAY_HISTORY_DETAIL = "/FenQi/RepayHistoryDetail/v2";
    //还款明细
//    public static final String REPAY_DETAIL_LIST = "/FenQi/RepayDetail/v2";
    public static final String REPAY_DETAIL_LIST = "/FenQi/RepayDetail/v3";
    //订单明细
    public static final String URL_ORDERDETAILS = "/FenQi/WithdrawalsDetail/v2";
    //获取贷后协议
    public static final String AGREE_LIST = "/FenQi/Agreement/v1";

    //上传位置信息
    public static final String URL_LOCATION = "/FenQi/ClientInfo/v1/type/location";
    //升级
    public static final String URL_UPDATE = "/FenQi/Version/v1";


    //===============提前还款=======================================
    //提前还款验证
    public static final String URL_PREPAY_REQUEST = "/FenQi/PrepayRequest/v1";
    //发起提前还款详情页
    public static final String URL_PREPAY_REQUEST_DETAIL= "/FenQi/PrepayRequestDetail/v1";
    //确定发起申请接口（入库）
    public static final String URL_PREPAY_REQUEST_CONFIRM= "/FenQi/PrepayRequestConfirm/v1";
    //提前还款申请结果页
    public static final String URL_PREPAY_REQUEST_RESULT= "/FenQi/PrepayRequestResult/v1";
    //取消提前还款接口
    public static final String URL_PREPAY_CANCEL= "/FenQi/PrepayCancel/v1";
    //提前还款结果页
    public static final String URL_PREPAY_RESULT= "/FenQi/PrepayResult/v1";
    //用户确认提前还款条件判断接口 生成流水好
    public static final String URL_PREPAY_SUBMIT= "/FenQi/PrepaySubmit/v1";
    //支付宝支付的时候加锁
    public static final String URL_FUNDLOCK= "/FenQi/FundLock/v1";
    //===============提前还款=======================================
}
