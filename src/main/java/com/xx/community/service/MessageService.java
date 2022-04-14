package com.xx.community.service;

import com.xx.community.dao.MessageMapper;
import com.xx.community.entity.Message;
import com.xx.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;


    public List<Message> findConversations(int userId, int offset, int limit) {
        return messageMapper.selectConversations(userId, offset, limit);
    }

    public int findConversationCount(int userId) {
        return messageMapper.selectConversationCount(userId);
    }

    public List<Message> findLetters(String conversationId, int offset, int limit) {
        return messageMapper.selectLetters(conversationId, offset, limit);
    }

    public int findLetterCount(String conversationId) {
        return messageMapper.selectLetterCount(conversationId);
    }

    public int findLetterUnreadCount(int userId, String conversationId) {
        return messageMapper.selectLetterUnreadCount(userId, conversationId);
    }

    //新增消息
    public int addMessage(Message message) {
        message.setContent(sensitiveFilter.filter(message.getContent()));
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        return messageMapper.insertMessage(message);
    }

    //读取消息
    public int readMessage(List<Integer> ids) {
        //状态标记为已读
        return messageMapper.updateStatus(ids, 1);
    }

    // 查询某个主题下的最新消息
    public Message findLatestNotice(int userId, String topic) {
        return messageMapper.selectLatestNotice(userId, topic);
    }

    // 查询某个主题的通知数量
    public int findNoticeCount(int userId, String topic) {
        return messageMapper.selectNoticeCount(userId, topic);
    }

    // 查询未读消息数量可以是某个主题，也可以是全部主题
    public int findNoticeUnreadCount(int userId, String topic) {
        return messageMapper.selectNoticeUnreadCount(userId, topic);
    }

    public List<Message> findNotices(int userId, String topic, int offset, int limit) {
        return messageMapper.selectNotices(userId, topic, offset, limit);
    }
}
