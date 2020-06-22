package com.chan.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chan.entity.Blog;
import com.chan.mapper.BlogMapper;
import com.chan.service.IBlogService;
import com.chan.util.ShiroUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chan
 * @since 2020-06-06
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {

    @Override
    public IPage<Blog> getBlogsWithPage(long currentPage) {
        Page<Blog> page = new Page<>(currentPage, 5);
        QueryWrapper<Blog> blogs = new QueryWrapper<Blog>().orderByDesc("created");
        return super.page(page, blogs);
    }

    @Override
    public Blog getBlogById(Long id) {
        Blog blog = super.getById(id);
        Assert.notNull(blog, "该博客不存在");
        return blog;
    }

    @Override
    public void saveBlog(Blog blog) {
        Blog temp;
        if (null != blog.getId()) {
            // 编辑数据库中的 blog
            temp = super.getById(blog.getId());
            // 判断编辑权限
            Assert.isTrue(temp.getUserId().equals(ShiroUtils.getProfile().getId()), "权限不足，无法编辑");

        } else {
            // 新建 blog
            temp = new Blog();
            temp.setUserId(ShiroUtils.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        // 把页面中的 blog 复制到 temp 中
        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        super.saveOrUpdate(temp);
    }

    @Override
    public void removeBlogById(Long id) {
        super.removeById(id);
    }
}
