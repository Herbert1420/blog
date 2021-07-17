package com.layne.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.layne.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    List<Comment> getByBlogIdAndParentmentIdNull(Long id);

    List<Comment> getByParentCommentId(Long parentCommentId);
}
