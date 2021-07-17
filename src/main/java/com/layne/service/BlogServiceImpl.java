package com.layne.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.layne.exception.NotFoundException;
import com.layne.mapper.*;
import com.layne.pojo.*;
import com.layne.util.MarkdownUtils;
import com.layne.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {
    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private BlogToTagMapper blogToTagMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private TagMapper tagMapper;

    /**
     * 通过id查询博客
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Blog getBlog(Long id) {
        return blogMapper.selectById(id);
    }

    /**
     * 详情页,通过id查询博客,经过Markdown插件处理content
     * @param id
     * @return
     */
    @Override
    public Blog getAndConvertBlog(Long id) {
        Blog blog = blogMapper.selectById(id);
        if (blog == null){
            throw new NotFoundException("博客不存在,或已被删除");
        }

        initUserAndType(blog);
        String content = blog.getContent();
        //在hibernate中谨慎操作
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));

        return initViews(blog);
    }


    /**
     * 访问博客时views + 1,更新数据库
     * @param blog
     * @return
     */
    @Transactional
    public Blog initViews(Blog blog){
        int views = (blog.getViews()==null ? 0 : blog.getViews());
        Long id = blog.getId();
        blogMapper.updateViewById(++views,id);
        blog.setViews(views);
        System.out.println(views);
        return blog;
    }

    /**
     * 后台按条件查询博客列表
     * @param page 分页对象,包含起始页,每页条数...
     * @param blogQuery 实体类,方便封装
     * @return 分页对象
     */
    @Transactional
    @Override
    public IPage<Blog> listBlog(IPage<Blog> page, BlogQuery blogQuery) {
        QueryWrapper<Blog> blogWrapper = new QueryWrapper<>();
            if(!"".equals(blogQuery.getTitle()) && blogQuery.getTitle() != null){
                blogWrapper.like("title",blogQuery.getTitle());
            }
            if (blogQuery.getTypeId() != null){
                blogWrapper.eq("type_id",blogQuery.getTypeId());
            }
            if (blogQuery.getRecommend() != null){
                blogWrapper.eq("recommend",blogQuery.getRecommend());
            }
            blogWrapper.orderByDesc("update_time");

        page = blogMapper.selectPage(page,blogWrapper);
        List<Blog> records = page.getRecords();
        for (Blog blog : records ) {
            //初始化
            initUserAndType(blog);
        }
        return page;
    }

    /**
     * 首页,查询博客列表(默认按照更新时间,倒序) ,封装user信息
     * @param pageNum 页码
     * @param size 每页大小
     * @return Blog分页对象
     */
    @Transactional
    @Override
    public IPage<Blog> listBlog(Integer pageNum, Integer size) {
        IPage<Blog> page = new Page<>(pageNum,size);
        QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
        blogQueryWrapper.orderByDesc("update_time");
        page = blogMapper.selectPage(page,blogQueryWrapper);
        if (page.getSize() != 0){
            List<Blog> blogs = page.getRecords();
            for (Blog blog :blogs ) {
                initUserAndType(blog);
            }

        }


        return page;
    }

    /**
     * 初始化user和type到blog对象
     * @param blog
     */
    public void initUserAndType(Blog blog){
        if (blog == null) return;
        //封装User,type,tags
        User user = null;
        Type type = null;


        Long userId = blog.getUserId();
        Long typeId = blog.getTypeId();
        user = userMapper.selectById(userId);
        type = typeMapper.selectById(typeId);
        blog.setUser(user);
        blog.setType(type);



    }

    /**
     * 新增博客
     * @param blog
     * @return
     */
    @Transactional
    @Override
    public Integer saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        blog.setUserId(blog.getUser().getId());


        return blogMapper.insert(blog);
    }

    /**
     * 更新博客内容
     * @param id
     * @param blog
     * @return
     */
    @Transactional
    @Override
    public Integer updateBlog(Long id, Blog blog) {
        Blog resultBlog = blogMapper.selectById(id);
        if (resultBlog == null){
            throw new NotFoundException("该博客不存在,或系统异常");
        }
        //只需要设置更新时间
        blog.setUpdateTime(new Date());
        //BeanUtils.copyProperties(blog,resultBlog);
        //更新步骤:更新t_blog表,更新t_blog_tags表
        return blogMapper.updateById(blog);
    }

    /**
     * 根据id删除博客
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Integer deleteBlog(Long id) {
        Integer i = blogToTagMapper.delete(new QueryWrapper<BlogToTag>().eq("blogs_id",id));
        if (i == null){
            return i;
        }
        return blogMapper.deleteById(id);
    }

    /**
     * 通过标题查询博客数,新增,修改博客,标题不能重复
     * @param title
     * @return
     */
    @Transactional
    @Override
    public Integer getCountByTitle(String title) {
        return blogMapper.getCountByTitle(title);
    }

    /**
     * 首页展示推荐博客 , 取出博客title,id
     * @param size 展示条数
     * @return <id,title>
     */
    @Transactional
    @Override
    public List<Blog> listRecommendBlog(Integer size) {
        return blogMapper.getRecommendBlog(size);
    }




    /**
     * 首页搜索博客列表
     * @param query 查询内容
     * @param pageNum 页码
     * @return
     */
    @Override
    public IPage<Blog> listSearchBlog(String query, Integer pageNum) {
        IPage<Blog> page = new Page<>(pageNum,5);
        QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
        blogQueryWrapper.like("title",query).or().like("content",query).orderByDesc("update_time");
        page = blogMapper.selectPage(page,blogQueryWrapper);
        if (page.getSize() != 0){
            List<Blog> blogs = page.getRecords();
            for (Blog blog :blogs ) {
                initUserAndType(blog);
            }
        }
        return page;
    }

    /**
     * 博客详情页面
     * @param id 博客id
     * @return 博客
     */
    @Override
    public Blog getBlogDetails(Long id) {
        Blog blog = getBlog(id);
        initUserAndType(blog);
        return blog;
    }

    /**
     * 分类页,根据typeId获取blog
     * @param pageNum 页码
     * @param typeId
     * @return 博客分页对象
     */
    @Override
    public IPage<Blog> listBlogByTypeId(Integer pageNum, Long typeId) {
        QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
        blogQueryWrapper.eq("type_id",typeId).orderByDesc("update_time");
        IPage<Blog> page = new Page<>(pageNum,5);
        page = blogMapper.selectPage(page, blogQueryWrapper);
        if (page.getRecords().size() > 0) {
            List<Blog> blogs = page.getRecords();
            for (Blog blog : blogs ) {
                initUserAndType(blog);
                initTags(blog);
            }
        }
        return page;
    }

    /**
     * 初始化标签列表到对应blog
     * @param blog
     */
    private void initTags(Blog blog) {
        Long blogId = blog.getId();
        QueryWrapper<BlogToTag> blogToTagQueryWrapper = new QueryWrapper<>();
        List<BlogToTag> blogToTags = blogToTagMapper.selectList(blogToTagQueryWrapper.eq("blogs_id",blogId));
        List<Tag> tags = new ArrayList<>();
        for (BlogToTag blogToTag: blogToTags ) {
            tags.add(tagMapper.getOneById(blogToTag.getTagsId()));
        }
        blog.setTags(tags);
    }

    /**
     * 标签页,根据tagId获取blog
     * @param pageNum 页码
     * @param tagId
     * @return
     */
    @Override
    public IPage<Blog> listBlogByTagId(Integer pageNum, Long tagId) {
        //根据tagId查出blogId
        QueryWrapper<BlogToTag> blogToTagQueryWrapper = new QueryWrapper<>();
        QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
        blogToTagQueryWrapper.eq("tags_id",tagId);
        List<BlogToTag> blogToTags = blogToTagMapper.selectList(blogToTagQueryWrapper);

        IPage<Blog> page = new Page<>(pageNum,5);
        Long blogId = null;
        int i = 0;
        for (BlogToTag blogToTag : blogToTags ) {
            blogId = blogToTag.getBlogsId();
            blogQueryWrapper.eq("id",blogId);
            i++;
            if (i == blogToTags.size()){
                break;
            }
            blogQueryWrapper.or();
        }
        page = blogMapper.selectPage(page,blogQueryWrapper);
        List<Blog> blogs = page.getRecords();
        for (Blog blog : blogs ) {
            initUserAndType(blog);
            initTags(blog);
        }
        return page;
    }

    /**
     * 按年份归档博客
     *      1.查询出所有年份
     *      2.归档到map
     * @return
     */
    @Transactional
    @Override
    public Map<String, List<Blog>> archiveBlog() {
        Map<String,List<Blog>> listMap = new LinkedHashMap<>();
        List<String> years = blogMapper.getGroupYear();
        //遍历所有year,查出所有对应年份的博客列表
        List<Blog> blogs = null;
        for (String year : years) {
            blogs = blogMapper.selectListByYear(year);
            listMap.put(year,blogs);
        }
        return listMap;
    }



    /**
     * footer展示最新博客
     */
    @Override
    public List<Blog> listBlogToFooter(Integer size) {
        QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
        blogQueryWrapper.eq("recommend",true).orderByDesc("update_time");
        return blogMapper.getRecommendBlog(size);
    }


}
