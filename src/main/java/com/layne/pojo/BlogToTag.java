package com.layne.pojo;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("t_blog_tags")
public class BlogToTag {
    private Long blogsId;
    private Long tagsId;

    public BlogToTag() {
    }

    public Long getBlogsId() {
        return blogsId;
    }

    public void setBlogsId(Long blogsId) {
        this.blogsId = blogsId;
    }

    public Long getTagsId() {
        return tagsId;
    }

    public void setTagsId(Long tagsId) {
        this.tagsId = tagsId;
    }

    @Override
    public String toString() {
        return "BlogAndTag{" +
                "blogsId=" + blogsId +
                ", tagsId=" + tagsId +
                '}';
    }
}
