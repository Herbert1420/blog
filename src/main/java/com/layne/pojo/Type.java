package com.layne.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * 分类
 * 属性 : id, 名字
 */

@TableName(value = "t_type")
public class Type {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @NotBlank(message = "分类名不能为空")
    private String name;
    //关系
    @TableField(exist = false)
    private List<Blog> blogs =new ArrayList<>();
    @TableField(exist = false)
    private Integer blogsLength;

    public Type() {
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

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public Integer getBlogsLength() {
        return blogsLength;
    }

    public void setBlogsLength(Integer blogsLength) {
        this.blogsLength = blogsLength;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", blogs=" + blogs +
                ", blogsLength=" + blogsLength +
                '}';
    }
}
