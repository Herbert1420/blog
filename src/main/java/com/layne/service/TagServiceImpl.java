package com.layne.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.layne.exception.NotFoundException;
import com.layne.mapper.TagMapper;
import com.layne.pojo.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper,Tag> implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Transactional
    @Override
    public Integer saveTag(Tag tag) {
        return tagMapper.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagMapper.getOneById(id);
    }

    @Transactional
    @Override
    public Integer getCountByName(String name) {
        return tagMapper.getCountByName(name);
    }

    @Transactional
    @Override
    public IPage<Tag> listTag(IPage<Tag> page, QueryWrapper<Tag> tagWrapper) {
        return tagMapper.selectPage(page,tagWrapper);
    }

    @Transactional
    @Override
    public Integer updateTag(Long id, Tag tag) {
        Tag resultTag = tagMapper.getOneById(id);
        if (resultTag == null){
            throw new NotFoundException("该标签不存在");
        }
        //BeanUtils.copyProperties(tag,resultTag);
        return tagMapper.updateTag(tag);

    }

    @Transactional
    @Override
    public Integer deleteTag(Long id) {
        return tagMapper.deleteTag(id);
    }

    /**
     * 首页展示标签 , 按标签中博客数进行倒序
     * @param size
     * @return
     */
    @Transactional
    @Override
    public List<Tag> listTagByCount(Integer size) {
        return tagMapper.getTagByCount(size);
    }
}
