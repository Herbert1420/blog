package com.layne.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * 标签类
 * 属性 : id,名字
 */
@TableName("t_tag")
public class Tag {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @NotBlank(message = "标签名不能为空")
    private String name;
    @TableField(exist = false)
    private List<Blog> blogs = new ArrayList<>();
    @TableField(exist = false)
    private Integer blogsLength;

    public Tag() {
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBlogsLength() {
        return blogsLength;
    }

    public void setBlogsLength(Integer blogsLength) {
        this.blogsLength = blogsLength;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", blogs=" + blogs +
                ", blogsLength=" + blogsLength +
                '}';
    }
}
