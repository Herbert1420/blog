package com.layne.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.layne.pojo.Type;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TypeMapper extends BaseMapper<Type> {
    Integer save(Type type);
    Type getOneById(Long id);
    Integer getCountByName(String name);
    Integer updateType(Type type);
    Integer deleteType(Long id);
    List<Type> getTypeByCount(Integer size);
}
