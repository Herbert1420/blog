package com.layne.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.layne.mapper.BlogToTagMapper;
import com.layne.pojo.Blog;
import com.layne.pojo.BlogToTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class BlogToTagServiceImpl extends ServiceImpl<BlogToTagMapper, BlogToTag> implements BlogToTagService {

    @Autowired
    private BlogToTagMapper blogToTagMapper;


    /**
     * 更新关系表,保存关系
     * @param blog
     * @param tagIds
     * @return
     */
    @Transactional
    @Override
    public Integer saveBlogToTag(Blog blog, List<Long> tagIds) {
        Long blogId = blog.getId();
        int i = 0;
        for (Long tagId : tagIds ) {
            i += blogToTagMapper.saveBlogToTag(blogId,tagId);
        }
        if (i == tagIds.size()){
            return 1;
        }else {
            return 0;
        }

    }

    /**
     * 更新关系
     * @param blog
     * @param tagIds
     * @return
     */
    @Transactional
    @Override
    public Integer updateBlogToTag(Blog blog, List<Long> tagIds) {
        Long blogId = blog.getId();
        if (tagIds == null){
            return 0;
        }
        int i = 0;
        for (Long tagId : tagIds ) {
            i += blogToTagMapper.saveBlogToTag(blogId,tagId);
        }
        if (i == tagIds.size()){
            return 1;
        }else {
            return 0;
        }

    }
}
