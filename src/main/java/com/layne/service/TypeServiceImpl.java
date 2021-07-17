package com.layne.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.layne.exception.NotFoundException;
import com.layne.mapper.TypeMapper;
import com.layne.pojo.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {
    @Autowired
    private TypeMapper typeMapper;

    @Transactional
    @Override
    public Integer saveType(Type type) {
        return typeMapper.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeMapper.getOneById(id);
    }

    @Transactional
    @Override
    public Integer getCountByName(String name) {
        return typeMapper.getCountByName(name);
    }

    @Transactional
    @Override
    public IPage<Type> listType(IPage<Type> page, QueryWrapper<Type> typeWrapper) {
        return typeMapper.selectPage(page,typeWrapper);
    }

    /**
     * 首页展示分类 , 按分类博客数量进行倒序
     * @param size 展示条数
     * @return 分类分页对象
     */
    @Transactional
    @Override
    public List<Type> listTypeByCount(Integer size) {
        return typeMapper.getTypeByCount(size);
    }

    @Transactional
    @Override
    public Integer updateType(Long id, Type type) {
        Type resultType = typeMapper.getOneById(id);
        if (resultType == null){
            throw new NotFoundException("该分类不存在");
        }
        //BeanUtils.copyProperties(type,resultType);
        return typeMapper.updateType(type);

    }

    @Transactional
    @Override
    public Integer deleteType(Long id) {
        return typeMapper.deleteType(id);
    }
}
