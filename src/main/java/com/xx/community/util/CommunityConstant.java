package com.xx.community.util;

public interface CommunityConstant {
    /**
     * 激活成功
     */
    int ACTIVATION_SUCCESS = 0;
    /**
     * 重复激活
     */
    int ACTIVATION_REPEAT = 1;
    /**
     * 激活失败
     */
    int ACTIVATION_FAILURE = 2;

    /**
     * 默认超时等待时间
     */
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;
    //一个月
    /**
     * 记住状态的超时等待时间
     */
    int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 30;
    /**
     * 实体类型 ： 帖子
     */
    int ENTITY_TYPE_POST = 1;
    /**
     * 实体类型：评论
     */
    int ENTITY_TYPE_COMMENT = 2;

    /**
     * 实体类型 用户
     */
    int ENTITY_TYPE_USER = 3;

    /**
     * 评论
     */
    String TOPIC_COMMENT = "comment";
    /**
     * 关注
     */
    String TOPIC_FOLLOW = "follow";
    /**
     * 点赞
     */
    String TOPIC_LIKE = "like";
    /**
     * 系统id
     */
    int SYSTEM_USER_ID = 1;
}
