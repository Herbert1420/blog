package com.layne.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.layne.pojo.Blog;
import com.layne.pojo.Tag;
import com.layne.pojo.Type;
import com.layne.service.BlogService;
import com.layne.service.TagService;
import com.layne.service.TypeService;
import com.layne.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    /**
     * 博客列表,初始化分类,标签,最新推荐
     * @param pageNum
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "1") Integer pageNum, Model model){
        //首页内容 : 博客(根据更新时间倒序) ,
        //分类列表(根据分类中博客数倒序) ,
        //标签(根据关联表中博客最多的倒序) ,
        //最新推荐(根据更新时间,倒序,且推荐)
        /*其他:封装user到blog.user
               封装type到blog.type
               封装分类长度到type.blogLength(自定义封装规则)
               封装标签长度到tag.blogLength
        * */

        IPage<Blog> page = blogService.listBlog(pageNum,6);
        List<Type> types = typeService.listTypeByCount(5);
        List<Tag> tags = tagService.listTagByCount(5);
        List<Blog> recommendBlogs = blogService.listRecommendBlog(6);


        model.addAttribute("page",page);
        model.addAttribute("types",types);
        model.addAttribute("tags",tags);
        model.addAttribute("recommendBlogs",recommendBlogs);
        model.addAttribute("maxPage", PageUtils.getMaxPage(page));
        return "index";
    }

    /**
     * 查询博客
     * @param query
     * @param pageNum
     * @param model
     * @return
     */
    @PostMapping("/search")
    public String search(@RequestParam String query,@RequestParam(defaultValue = "1") Integer pageNum,Model model){
        //优先按title搜索,再content包含,更新倒序
        IPage<Blog> page = blogService.listSearchBlog(query,pageNum);
        model.addAttribute("page",page);
        model.addAttribute("maxPage",PageUtils.getMaxPage(page));
        model.addAttribute("query",query);
        return "search";
    }


    /**
     * 博客详情
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        Blog blog = blogService.getAndConvertBlog(id);
        model.addAttribute("blog",blog);
        return "blog";
    }

    /**
     * footer刷新,获取三条最新推荐博客
     * @param model
     * @return
     */
    @GetMapping("/footer/newblog")
    public String newBlogs(Model model){
        //取三条博客
        model.addAttribute("newblogs",blogService.listBlogToFooter(3));
        return "_fragments :: newblogList";
    }
}
