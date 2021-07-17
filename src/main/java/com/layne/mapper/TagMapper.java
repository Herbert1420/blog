package com.layne.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.layne.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    Integer save(Tag tag);
    Tag getOneById(Long id);
    Integer getCountByName(String name);
    Integer updateTag(Tag tag);
    Integer deleteTag(Long id);
    List<Tag> getTagByCount(Integer size);
}
