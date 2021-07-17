package com.layne.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.layne.pojo.Comment;

import java.util.List;

public interface CommentService extends IService<Comment> {
    List<Comment> listCommentByBlogId(Long blogId);
    Integer saveComment(Comment comment);
}

