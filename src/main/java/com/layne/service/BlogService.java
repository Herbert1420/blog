package com.layne.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.layne.pojo.Blog;
import com.layne.vo.BlogQuery;

import java.util.List;
import java.util.Map;


public interface BlogService extends IService<Blog> {
    Blog getBlog(Long id);
    Blog getAndConvertBlog(Long id);
    IPage<Blog> listBlog(IPage<Blog> page , BlogQuery blogQuery);
    IPage<Blog> listBlog(Integer pageNum, Integer size);
    Integer saveBlog(Blog blog);
    Integer updateBlog(Long id, Blog blog);
    Integer deleteBlog(Long id);
    Integer getCountByTitle(String title);
    List<Blog> listRecommendBlog(Integer size);


    IPage<Blog> listSearchBlog(String query, Integer pageNum);

    Blog getBlogDetails(Long id);

    IPage<Blog> listBlogByTypeId(Integer pageNum, Long typeId);

    IPage<Blog> listBlogByTagId(Integer pageNum, Long tagid);

    Map<String,List<Blog>> archiveBlog();

    List<Blog> listBlogToFooter(Integer size);
}
