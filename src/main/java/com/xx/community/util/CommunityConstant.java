package com.xx.community.util;

public interface CommunityConstant {
    /**
     * 激活成功
     */
    int ACTIVATION_SUCCESS = 0;
    int ACTIVATION_REPEAT = 1;
    int ACTIVATION_FAILURE = 2;

    /**
     * 默认超时等待时间
     */
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;
    //一个月
    //勾选记住我
    int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 30;

}
