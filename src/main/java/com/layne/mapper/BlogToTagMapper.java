package com.layne.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.layne.pojo.BlogToTag;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlogToTagMapper extends BaseMapper<BlogToTag> {
    //t_blog  --  t_tag
    Integer saveBlogToTag(Long blogId,Long tagId);
    Integer updateBlogToTag(Long blogsId,Long tagsId);

}
