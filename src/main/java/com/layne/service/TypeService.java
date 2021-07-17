package com.layne.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.layne.pojo.Type;

import java.util.List;

/**
 * 分类Service层
 */
public interface TypeService extends IService<Type> {
    Integer saveType(Type type);

    Type getType(Long id);
    Integer getCountByName(String name);


    IPage<Type> listType(IPage<Type> page , QueryWrapper<Type> typeWrapper);
    List<Type> listTypeByCount(Integer size);

    Integer updateType(Long id,Type type);

    Integer deleteType(Long id);
}
