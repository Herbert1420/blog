package com.layne.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.layne.pojo.Tag;

import java.util.List;

/**
 * 分类Service层
 */
public interface TagService extends IService<Tag> {
    Integer saveTag(Tag tag);
    Tag getTag(Long id);
    Integer getCountByName(String name);
    IPage<Tag> listTag(IPage<Tag> page, QueryWrapper<Tag> tagWrapper);
    Integer updateTag(Long id, Tag tag);
    Integer deleteTag(Long id);
    List<Tag> listTagByCount(Integer size);
}
