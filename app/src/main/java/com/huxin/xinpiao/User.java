package com.huxin.xinpiao;

import com.huxin.common.entity.IEntity;

/**
 * Created by 56417 on 2017/8/30.
 */

public class User implements IEntity{
    //账号体系返回
    public String uid;

    //账户信息返回
    public int identityAuthStatus;
    public int basicStatus;
    public String mobile;
    public String icon;

    public boolean  isRealNameAuth;//是否实名认证
    public boolean  isBankBinding;//是否绑定银行卡
}
