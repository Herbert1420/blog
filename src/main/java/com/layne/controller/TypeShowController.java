package com.layne.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.layne.pojo.Blog;
import com.layne.pojo.Type;
import com.layne.service.BlogService;
import com.layne.service.TypeService;
import com.layne.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    /**
     * 分类列表
     * @param id
     * @param pageNum
     * @param model
     * @return
     */
    @GetMapping("/types/{id}")
    public String types(@PathVariable Long id, @RequestParam(defaultValue = "1") Integer pageNum, Model model ){
        List<Type> types = typeService.listTypeByCount(100);
        if (id == -1){
            id = types.get(0).getId();
        }
        IPage<Blog> page = blogService.listBlogByTypeId(pageNum,id);
        model.addAttribute("page",page);
        model.addAttribute("types",types);
        model.addAttribute("activeId",id);
        model.addAttribute("maxPage", PageUtils.getMaxPage(page));
        return "types";
    }

}
