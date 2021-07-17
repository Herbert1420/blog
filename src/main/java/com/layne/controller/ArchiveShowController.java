package com.layne.controller;


import com.layne.pojo.Blog;
import com.layne.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;

    /**
     * 归档列表
     * @param model
     * @return
     */
    @GetMapping("/archives")
    public String archives(Model model){
        Map<String, List<Blog>> archiveMap = blogService.archiveBlog();
        model.addAttribute("archiveMap",archiveMap);
        model.addAttribute("blogCount",blogService.count(null));
        return "archives";
    }
}
