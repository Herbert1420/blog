package com.layne.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.layne.mapper.CommentMapper;
import com.layne.pojo.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentIServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 根据blogId获取评论列表,默认按创建时间升序
     * 逻辑:一个父级,多个子级为同一级
     * @param blogId
     * @return Comment列表
     */
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        //所有根节点
        List<Comment> comments = commentMapper.getByBlogIdAndParentmentIdNull(blogId);
        if (comments == null){
            return null;
        }
        for (Comment comment: comments ) {
            //找出直接子级封装:本节点id作为子节点的parentCommentId
            comment.setReplyComments(commentMapper.getByParentCommentId(comment.getId()));
        }
        return eachComment(comments);
    }

    /**
     * 没有具体逻辑,保证数据库的安全性,拷贝一份新的数据进行操作
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentView.add(c);
        }
        //合并评论的子代到第一层子代集合
        combineChildren(commentView);
        return commentView;
    }


    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();
    /**
     * 将所有同一根节点下的所有子级存放在replyComments中中
     * @param comments 根节点列表
     */
    private void combineChildren(List<Comment> comments) {
        if (comments == null)return;
        for (Comment comment : comments ) {
            List<Comment> replys1 = comment.getReplyComments();
            for (Comment reply2 : replys1) {
                reply2.setParentComment(comment);
                tempReplys.add(reply2);
                recursively(reply2);
            }
            comment.setReplyComments(tempReplys);
            tempReplys = new ArrayList<>();
        }

    }

    /**
     * 剥洋葱,找出所有子级,存放在tempReplys
     * @param comment 节点
     */
    public void recursively(Comment comment){
        if (comment == null)return;
//        tempReplys.add(comment);//将该节点先放到tempReplys
        List<Comment> replys = commentMapper.getByParentCommentId(comment.getId());
        if (replys.size() > 0){
            comment.setReplyComments(replys);//封装子级
            for (Comment reply1 : replys) {
                reply1.setParentComment(comment);//封装父级
                tempReplys.add(reply1);
                if (commentMapper.getByParentCommentId(reply1.getId()).size() > 0){
                    recursively(reply1);
                }


            }
        }


    }
    

    /**
     * 保存评论信息
     * @param comment 平论对象
     * @return
     */
    @Transactional
    @Override
    public Integer saveComment(Comment comment) {
        comment.setCreateTime(new Date());
        if (comment.getParentCommentId() == -1){
            comment.setParentCommentId(null);
        }
        return commentMapper.insert(comment);
    }
}
