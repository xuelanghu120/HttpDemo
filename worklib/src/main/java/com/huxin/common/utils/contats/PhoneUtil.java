package com.huxin.common.utils.contats;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.huxin.common.application.Global;
import com.huxin.common.utils.StringUtils;
import com.huxin.common.utils.contats.entity.UploadContactEntity;

import java.util.ArrayList;
import java.util.List;


/***
 * 电话相关工具类
 * 1.读取手机通讯录
 */
public class PhoneUtil {
    private static String TAG = "PhoneUtils";

    /**
     * 获取所有通讯录数据
     *
     * @return 查询结果
     */
    public static final List<PhoneUserEntity> getAllPhoneUser() {
        List<PhoneUserEntity> mList = new ArrayList<PhoneUserEntity>();
        Uri uri = Uri.parse("content://com.android.contacts/contacts"); //访问raw_contacts表
        ContentResolver resolver = Global.getContext().getContentResolver();
        // 获得_id属性
        Cursor cursor = resolver.query(uri, new String[]{Data._ID}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                PhoneUserEntity entity = new PhoneUserEntity();
                // 获得id并且在data中寻找数据
                int id = cursor.getInt(0);
                uri = Uri.parse("content://com.android.contacts/contacts/" + id + "/data");
                // data1存储各个记录的总数据，mimetype存放记录的类型，如电话、email等
                Cursor cursor2 = resolver.query(uri, new String[]{Data.DATA1, Data.MIMETYPE}, null, null, null);
                while (cursor2.moveToNext()) {
                    String data = cursor2.getString(cursor2.getColumnIndex("data1"));
                    if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/name")) { // 如果是名字
                        entity.setNames(data);
                    } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/phone_v2")) { // 如果是电话
                        entity.setMobiles(data);
                    } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/email_v2")) { // 如果是email
                        entity.setEmail(data);
                    } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/postal-address_v2")) { // 如果是地址
                        entity.setAddress(data);
                    } else if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/organization")) { // 如果是组织
                        entity.setOrganization(data);
                    }
                }
                //close掉cursor
                cursor2.close();
                //如果名字为空，则填手机号
                if (TextUtils.isEmpty(entity.getName())) {
                    entity.setNames(entity.getMobiles());
                }
                mList.add(entity);
            }
            cursor.close();
        }
        return mList;
    }

    /**
     * 自定义显示Contacts提供的联系人的方法
     *
     * @param context
     * @return
     */
    public static List<UploadContactEntity> getContacts(Context context) {
        List<UploadContactEntity> mContactList = new ArrayList<>();
        UploadContactEntity entity;
        //生成ContentResolver对象
        ContentResolver contentResolver = context.getContentResolver();
        // 获得所有的联系人
        /*Cursor cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
         */
        //这段代码和上面代码是等价的，使用两种方式获得联系人的Uri
        Cursor cursor = contentResolver.query(Uri.parse("content://com.android.contacts/contacts"), null, null, null, null);
        // 循环遍历
        if (cursor.moveToFirst()) {

            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            int displayNameColumn = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            do {
                entity = new UploadContactEntity();
                // 获得联系人的ID
                String contactId = cursor.getString(idColumn);
                // 获得联系人姓名
                String displayName = cursor.getString(displayNameColumn);
                //公司名称暂时不拼接
//                Uri uri = Uri.parse("content://com.android.contacts/contacts/" + contactId + "/data");
//                // data1存储各个记录的总数据，mimetype存放记录的类型，如电话、email等
//                Cursor cursor2 = contentResolver.query(uri, new String[]{Data.DATA1, Data.MIMETYPE}, null, null, null);
//                while (cursor2.moveToNext()) {
//                    String companyColumn = cursor2.getString(cursor2.getColumnIndex("data1"));
//                    if (cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/organization")) { // 如果是组织
//                        displayName += "_"+companyColumn;
//                    }
//                }
//                //close掉cursor
//                cursor2.close();

                //赋值
                entity.name = displayName;
                // 查看联系人有多少个号码，如果没有号码，返回0
                int phoneCount = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                if (phoneCount > 0) {
                    // 获得联系人的电话号码列表
                    Cursor phoneCursor = context.getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + "=" + contactId, null, null);
                    if (phoneCursor.moveToFirst()) {
                        entity.mobile = new ArrayList<>();
                        do {
                            //遍历所有的联系人下面所有的电话号码
                            String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            //赋值
                            entity.mobile.add(phoneNumber);
                        } while (phoneCursor.moveToNext());
                    }
                    phoneCursor.close();
                }
                mContactList.add(entity);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return mContactList;

    }


    /**
     * 5.0获取联系人权限
     * @param context
     * @return
     */
    public static boolean checkContactPermisson(Context context) {
        //生成ContentResolver对象
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(Uri.parse("content://com.android.contacts/contacts"), null, null, null, null);
            boolean b = cursor.moveToFirst();
            cursor.close();
            return b;
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
            return false;
        }

    }

    /**
     * 判断设备是否是手机
     *
     * @param context 上下文
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isPhone(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
    }

    /**
     * 获取手机的IMIE
     * <p>需与{@link #isPhone(Context)}一起使用</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
     *
     * @param context 上下文
     * @return IMIE码
     */
    public static String getPhoneIMEI(Context context) {
        String deviceId;
        if (isPhone(context)) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = tm.getDeviceId();
        } else {
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceId;
    }

    /**
     * 获取手机状态信息
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
     *
     * @param context 上下文
     * @return DeviceId(IMEI) = 99000311726612<br>
     * DeviceSoftwareVersion = 00<br>
     * Line1Number =<br>
     * NetworkCountryIso = cn<br>
     * NetworkOperator = 46003<br>
     * NetworkOperatorName = 中国电信<br>
     * NetworkType = 6<br>
     * honeType = 2<br>
     * SimCountryIso = cn<br>
     * SimOperator = 46003<br>
     * SimOperatorName = 中国电信<br>
     * SimSerialNumber = 89860315045710604022<br>
     * SimState = 5<br>
     * SubscriberId(IMSI) = 460030419724900<br>
     * VoiceMailNumber = *86<br>
     */
    public static String getPhoneStatus(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String str = "";
        str += "DeviceId(IMEI) = " + tm.getDeviceId() + "\n";
        str += "DeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion() + "\n";
        str += "Line1Number = " + tm.getLine1Number() + "\n";
        str += "NetworkCountryIso = " + tm.getNetworkCountryIso() + "\n";
        str += "NetworkOperator = " + tm.getNetworkOperator() + "\n";
        str += "NetworkOperatorName = " + tm.getNetworkOperatorName() + "\n";
        str += "NetworkType = " + tm.getNetworkType() + "\n";
        str += "honeType = " + tm.getPhoneType() + "\n";
        str += "SimCountryIso = " + tm.getSimCountryIso() + "\n";
        str += "SimOperator = " + tm.getSimOperator() + "\n";
        str += "SimOperatorName = " + tm.getSimOperatorName() + "\n";
        str += "SimSerialNumber = " + tm.getSimSerialNumber() + "\n";
        str += "SimState = " + tm.getSimState() + "\n";
        str += "SubscriberId(IMSI) = " + tm.getSubscriberId() + "\n";
        str += "VoiceMailNumber = " + tm.getVoiceMailNumber() + "\n";
        return str;
    }

    /**
     * 跳至填充好phoneNumber的拨号界面
     *
     * @param context     上下文
     * @param phoneNumber 电话号码
     */
    public static void dial(Context context, String phoneNumber) {
        context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber)));
    }

    /**
     * 拨打phoneNumber
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.CALL_PHONE"/>}</p>
     *
     * @param context     上下文
     * @param phoneNumber 电话号码
     */
    public static void call(Context context, String phoneNumber) {
        context.startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber)));
    }

    /**
     * 发送短信
     *
     * @param context     上下文
     * @param phoneNumber 电话号码
     * @param content     内容
     */
    public static void sendSms(Context context, String phoneNumber, String content) {
        Uri uri = Uri.parse("smsto:" + (StringUtils.isEmpty(phoneNumber) ? "" : phoneNumber));
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", StringUtils.isEmpty(content) ? "" : content);
        context.startActivity(intent);
    }


    /**
     * 打开手机联系人界面点击联系人后便获取该号码
     * <p>参照以下注释代码</p>
     */
    public static void getContantNum() {
        Log.i("tips", "U should copy the following code.");
        /*
        Intent intent = new Intent();
        intent.setAction("android.intent.action.PICK");
        intent.setType("vnd.android.cursor.dir/phone_v2");
        startActivityForResult(intent, 0);
        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (data != null) {
                Uri uri = data.getData();
                String num = null;
                // 创建内容解析者
                ContentResolver contentResolver = getContentResolver();
                Cursor cursor = contentResolver.query(uri,
                        null, null, null, null);
                while (cursor.moveToNext()) {
                    num = cursor.getString(cursor.getColumnIndex("data1"));
                }
                cursor.close();
                num = num.replaceAll("-", "");//替换的操作,555-6 -> 5556
            }
        }
        */
    }

    /**
     * 获取联系人电话,仅仅获取电话
     * Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
     * Intent intent = new Intent(Intent.ACTION_PICK, uri);
     * amergencyContactActivity.startActivityForResult(intent, requstCode);
     *
     * @param context
     * @param cursor
     * @return
     */
    public static String getContactPhone(Activity context, Cursor cursor) {
        cursor.moveToFirst();
        int phoneColumn = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
        int phoneNum = cursor.getInt(phoneColumn);
        String phoneResult = "";
        //System.out.print(phoneNum);
        if (phoneNum > 0) {
            // 获得联系人的ID号
            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            String contactId = cursor.getString(idColumn);
            // 获得联系人的电话号码的cursor;
            Cursor phones = context.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null, null);
            if (phones.moveToFirst()) {
                // 遍历所有的电话号码
                for (; !phones.isAfterLast(); phones.moveToNext()) {
                    int index = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    int typeindex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
                    int phone_type = phones.getInt(typeindex);
                    String phoneNumber = phones.getString(index);
                    switch (phone_type) {
                        case 2:
                            phoneResult = phoneNumber;
                            break;
                    }
                }
                if (!phones.isClosed()) {
                    phones.close();
                }
            }
        }
        return phoneResult;
    }

    /**
     * 获取手机短信
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_SMS"/>}</p>
     *
     * @param context 上下文
     */
    public static List<SmsEntity> getAllSMS(Context context) {
        // 1.获取短信
        // 1.1获取内容解析者
        List<SmsEntity> smsEntities = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();
        // 1.2获取内容提供者地址   sms,sms表的地址:null  不写
        // 1.3获取查询路径
        Uri uri = Uri.parse("content://sms");
        // 1.4.查询操作
        // projection : 查询的字段
        // selection : 查询的条件
        // selectionArgs : 查询条件的参数
        // sortOrder : 排序
        Cursor cursor = resolver.query(uri, new String[]{"address", "date", "type", "body"}, null, null, null);
        // 设置最大进度
        int count = cursor.getCount();//获取短信的个数
        try {
            while (cursor.moveToNext()) {
                String address = cursor.getString(0);
                String date = cursor.getString(1);
                String type = cursor.getString(2);
                String body = cursor.getString(3);
                smsEntities.add(new SmsEntity(address, body, date, type));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cursor.close();
        return smsEntities;
    }

    /**
     * 获得通话记录
     * 报错部分是因为没有进行权限检测，可能会抛异常，在引用的地方务必要检测是否授权
     */
    public static List<CallRecordEntity> getCallsRecord() {
        List<CallRecordEntity> records = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(Global.getContext(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            Cursor cursor = Global.getContext().getContentResolver().query(
                    CallLog.Calls.CONTENT_URI,
                    new String[]{CallLog.Calls.CACHED_NAME, CallLog.Calls.DURATION, CallLog.Calls.TYPE, CallLog.Calls.DATE,
                            CallLog.Calls.NUMBER}, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
            boolean hasRecord = cursor.moveToFirst();
            int count = 0;
            while (hasRecord) {
                int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
                long duration = cursor.getLong(cursor
                        .getColumnIndex(CallLog.Calls.DURATION));
                String strPhone = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                String date = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
                records.add(new CallRecordEntity(date, String.valueOf(duration), name, strPhone, String.valueOf(type)));
                count++;
                hasRecord = cursor.moveToNext();
            }
            cursor.close();
            return records;
        }
        return records;
    }

}
