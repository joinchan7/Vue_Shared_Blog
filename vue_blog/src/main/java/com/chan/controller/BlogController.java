package com.chan.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chan.common.lang.Result;
import com.chan.entity.Blog;
import com.chan.service.IBlogService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chan
 * @since 2020-06-06
 */
@RestController
public class BlogController {

    @Autowired
    private IBlogService blogService;

    // 列表
    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage) {
        IPage<Blog> pageData = blogService.getBlogsWithPage(currentPage);
        return Result.success(pageData);
    }

    // 详情
    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable Long id) {
        Blog blog = blogService.getBlogById(id);
        return Result.success(blog);
    }

    // 进入编辑页面
    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog) {
        blogService.saveBlog(blog);
        return Result.success("编辑成功");
    }

    // 删除
    @RequiresAuthentication
    @DeleteMapping("/blog/{id}")
    public Result delete(@PathVariable Long id) {
        blogService.removeBlogById(id);
        return Result.success("删除成功");
    }

}
