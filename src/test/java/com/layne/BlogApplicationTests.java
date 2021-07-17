package com.layne;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.layne.mapper.BlogMapper;
import com.layne.mapper.TagMapper;
import com.layne.mapper.TypeMapper;
import com.layne.pojo.Blog;
import com.layne.pojo.Tag;
import com.layne.pojo.Type;
import com.layne.service.TypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private TypeService typeService;
    private Object Type;

    @Test
    void contextLoads() {
    }

    @Test
    void getBlog(){

    }

    @Test
    void tedxfdsg(){
        Page<Type> page = new Page<>(1,3);
        typeMapper.selectPage(page,null);
        page.getRecords().forEach(System.out::println);
    }

    @Test
    void testUpdate(){
        Type type = new Type();
        type.setId(1l);
        type.setName("ffc1");
        typeMapper.updateType(type);
    }

    @Test
    void test001(){

        Integer pageNum = 1;
        QueryWrapper<Type> typeWrapper = new QueryWrapper<>();
        typeWrapper
                .orderByAsc("id");
        IPage<Type> page = new Page<>(pageNum,2);
        page = typeService.listType(page,typeWrapper);
        System.out.println(page);
    }

    @Test
    void save(){
        Type type = new Type();
        type.setName("haha00");
        typeService.saveType(type);
    }


    @Test
    void like(){
        QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
        Blog blog = new Blog();
        blog.setTitle("一族的");
        if(!"".equals(blog.getTitle()) && blog.getTitle() != null){
            blogQueryWrapper.likeLeft("title",blog.getTitle());
        }
        blogMapper.selectOne(blogQueryWrapper);
    }

    @Test
    void list(){
        List<Tag> list = new ArrayList<>();
        list.add(new Tag());
        list.add(new Tag());
        list.add(new Tag());
        list.add(new Tag());
        list.add(new Tag());
        System.out.println(list);
    }

    @Test
    void listType(){
        System.out.println(tagMapper.getTagByCount(3));
    }

    @Test
    void test00(){
        byte a = (byte)(127+2);
        System.out.println(a);


    }

}
