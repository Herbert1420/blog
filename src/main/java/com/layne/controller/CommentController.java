package com.layne.controller;

import com.layne.pojo.Comment;
import com.layne.pojo.User;
import com.layne.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 评论Comment控制器
 */

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Value("${comment.avatar}")
    private String avatar;

    /**
     * 访问博客详情时,刷新评论片段
     * @param blogId 博客id
     * @param model
     * @return 评论片段
     */
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        //根据blogId获取该博客的comments信息
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        model.addAttribute("comments",comments);
        return "blog :: commentList";
    }

    /**
     * 保存评论信息,标记管理员
     * @param comment comment对象
     * @return 重定向到get
     */
    @PostMapping("/comments")
    public String post(Comment comment, RedirectAttributes redirectAttributes, HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }else{
            comment.setAvatar(avatar);
        }
        Integer i = commentService.saveComment(comment);
        if (i == 0){
            redirectAttributes.addFlashAttribute("message","操作失败,请重试");
        }
        return "redirect:/comments/"+comment.getBlogId();
    }
}
