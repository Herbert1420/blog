package com.layne.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.layne.pojo.Blog;
import com.layne.pojo.Tag;
import com.layne.service.BlogService;
import com.layne.service.TagService;
import com.layne.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    /**
     * 标签列表
     * @param id
     * @param pageNum
     * @param model
     * @return
     */
    @GetMapping("/tags/{id}")
    public String types(@PathVariable Long id, @RequestParam(defaultValue = "1") Integer pageNum, Model model ){
        List<Tag> tags = tagService.listTagByCount(10000);
        if (id == -1){
            id = tags.get(0).getId();
        }
        IPage<Blog> page = blogService.listBlogByTagId(pageNum,id);
        model.addAttribute("page",page);
        model.addAttribute("tags",tags);
        model.addAttribute("activeId",id);
        model.addAttribute("maxPage", PageUtils.getMaxPage(page));
        return "tags";
    }

}
