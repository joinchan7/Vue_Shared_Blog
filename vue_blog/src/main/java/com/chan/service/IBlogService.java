package com.chan.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chan.entity.Blog;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chan
 * @since 2020-06-06
 */
public interface IBlogService extends IService<Blog> {

    IPage<Blog> getBlogsWithPage(long currentPage);

    Blog getBlogById(Long id);

    void saveBlog(Blog blog);

    void removeBlogById(Long id);
}
