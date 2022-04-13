package com.xx.community.dao;

import com.xx.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    //entityType表示回复的对象 可以是回复帖子也可以是回复评论
    List<Comment> selectCommentsByEntity(int entityType,int entityId,int offset,int limit);

    int selectCountByEntity(int entityType,int entityId);

    int insertComment(Comment comment);

    Comment selectCommentById(int id);
}
