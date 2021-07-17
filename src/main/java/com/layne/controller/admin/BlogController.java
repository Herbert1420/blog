package com.layne.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.layne.pojo.*;
import com.layne.service.BlogService;
import com.layne.service.BlogToTagService;
import com.layne.service.TagService;
import com.layne.service.TypeService;
import com.layne.util.PageUtils;
import com.layne.util.StringUtils;
import com.layne.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 博客控制器
 */

@Controller
@RequestMapping("/admin")
public class BlogController {
    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";


    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogToTagService blogToTagService;

    /**
     * 查询博客列表
     * @param blogQuery
     * @param pageNum
     * @param model
     * @return
     */
    @GetMapping("/blogs")
    public String blogs(BlogQuery blogQuery, @RequestParam(defaultValue = "1") Integer pageNum, Model model){
        //初始化,用以查询条件
        model.addAttribute("types",typeService.list(null));
        IPage<Blog> page = new Page<>(pageNum,5);
        page = blogService.listBlog(page,blogQuery);
        //返回分页对象,最大页码
        model.addAttribute("page",page);
        model.addAttribute("maxPage",PageUtils.getMaxPage(page));
        return LIST;
    }

    /**
     * 查询博客列表,按输入条件拼接条件
     * @param blogQuery 包含属性:title,typeId,recommend
     * @param pageNum
     * @param model
     * @return
     */
    @PostMapping("/blogs/search")
    public String search(BlogQuery blogQuery , @RequestParam(defaultValue = "1") Integer pageNum, Model model){
        IPage<Blog> page = new Page<>(pageNum,5);
        page = blogService.listBlog(page,blogQuery);
        model.addAttribute("page",page);
        model.addAttribute("maxPage",PageUtils.getMaxPage(page));
        return "admin/blogs :: blogList";
    }

    /**
     * 发布博客,初始化
     * @param model
     * @return
     */
    @GetMapping("/blogs/input")
    public String input(Model model){
        setTypeAndTag(model);
        model.addAttribute("blog",new Blog());
        return INPUT;
    }

    //初始化
    private void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.list(null));
        model.addAttribute("tags",tagService.list(null));
    }

    /**
     * 编辑,发布
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/blogs/{id}/input")
    public String input(Model model, @PathVariable Long id){
        setTypeAndTag(model);
        //查blog , 及关联关系,以及标签,分类
        Blog blog = blogService.getBlog(id);
        List<BlogToTag> blogAndTags =  blogToTagService.list(new QueryWrapper<BlogToTag>().eq("blogs_id",id));
        List<Tag> tags = new ArrayList<>();
        for (BlogToTag bat :blogAndTags ) {
            tags.add(tagService.getTag(bat.getTagsId()));
        }
        blog.setTags(tags);
        blog.init();
        blog.setType(typeService.getType(blog.getTypeId()));
        model.addAttribute("blog",blog);
        return INPUT;
    }


    /**
     * 保存,发布
     * @param blog 博客内容
     * @param tagIds 标签id,以字符串形式接收
     * @param redirectAttributes 保存message
     * @param session 取出user
     * @return
     */
    @PostMapping("/blogs")
    public String post(Blog blog,String tagIds, RedirectAttributes redirectAttributes, HttpSession session){
        List<Long> tagIdList = StringUtils.stringToLongs(tagIds);
        //如果blog:id != null,表示修改博客,直接调用update方法
        if (blog.getId() != null){
            if(blogService.updateBlog(blog.getId(),blog) != null){
                //先删除关联表所有的blogId
                blogToTagService.remove(new QueryWrapper<BlogToTag>().eq("blogs_id",blog.getId()));
                blogToTagService.updateBlogToTag(blog,tagIdList);
            }
            redirectAttributes.addFlashAttribute("message","《"+blog.getTitle()+"》"+"修改成功,已重新发布!");
            return REDIRECT_LIST;
        }


        blog.setUser((User) session.getAttribute("user"));
        //通过标题名查询博客
        Integer i = blogService.getCountByTitle(blog.getTitle());
        if (i > 0){
            redirectAttributes.addFlashAttribute("message","该博客已存在, 您可以选择重新编辑博客");
            return REDIRECT_LIST;
        }
        //保存博客的同时向:博客-标签表中插入关系,自动回填id
        Integer i1 = blogService.saveBlog(blog);
        if (i1 != 0){
            Integer i2 =  blogToTagService.saveBlogToTag(blog,tagIdList);
            if (i2 != 0){
                redirectAttributes.addFlashAttribute("message","《"+blog.getTitle()+"》"+ "已保存成功");
            }else{
                redirectAttributes.addFlashAttribute("message","系统异常,请重试或联系管理员");
                //移除关系
            }
        }
        return REDIRECT_LIST;

    }


    /**
     * 删除博客
     * @param id
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes){
        //删除博客内容,删除博客与标签的关联
        Integer i = blogService.deleteBlog(id);
        if (i != null){
            redirectAttributes.addFlashAttribute("message" ,"删除成功") ;
        }else{
            redirectAttributes.addFlashAttribute("message" ,"操作失败,请重试") ;
        }
        return REDIRECT_LIST;
    }

}
