package com.xx.community.dao;

import com.xx.community.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {
    //查询用户的会话列表
    List<Message> selectConversations(int userId,int offset,int limit);

    //查询用户的会话数量
    int selectConversationCount(int userId);

    //查询某个会话的私信列表
    List<Message> selectLetters(String conversationId,int offset,int limit);

    //查询某个会话包含的私信数量
    int selectLetterCount(String conversationId);

    //查询未读私信的数量
    int selectLetterUnreadCount(int userId,String conversationId);

    // 新增消息
    int insertMessage(Message message);

    // 修改消息的状态 未读->已读
    int updateStatus(List<Integer> ids,int status);

    // 查询某个主题下的最新通知
    Message selectLatestNotice(int userId, String topic);
    // 查询某个主题包含的通知数量
    int selectNoticeCount(int userId, String topic);
    // 查询某个主题未读消息数量,如果topic为空串，则查询所有未读通知数量
    int selectNoticeUnreadCount(int userId,String topic);

    //查询某个主题包含的通知列表
    List<Message> selectNotices(int userId, String topic, int offset, int limit);
}
