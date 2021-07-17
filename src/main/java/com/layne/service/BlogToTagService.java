package com.layne.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.layne.pojo.Blog;
import com.layne.pojo.BlogToTag;

import java.util.List;

public interface BlogToTagService extends IService<BlogToTag> {
    Integer saveBlogToTag(Blog blog, List<Long> tagIds) ;
    Integer updateBlogToTag(Blog blog, List<Long> tagIds) ;
}
