package com.layne.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.layne.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogMapper extends BaseMapper<Blog> {
    Integer saveBlog(Blog blog);
    //Blog getOneById(Long id);
    Integer getCountByTitle(String title);
    //Integer updateBlog(Blog blog);
   // Integer deleteBlog(Long id);

    List<Blog> getRecommendBlog(Integer size);

    Integer updateViewById(Integer views,Long id);

    List<String> getGroupYear();

    List<Blog> selectListByYear(String year);
    //IPage<Blog> listBlog();



}
