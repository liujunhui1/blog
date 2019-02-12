package cn.junhui.blog_test.service.impl;

import cn.junhui.blog_test.domain.*;
import cn.junhui.blog_test.repository.BlogRepository;
import cn.junhui.blog_test.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.swing.text.BadLocationException;
import javax.transaction.Transactional;
import java.util.Optional;

/**
 * 军辉
 * 2019-02-10 12:00
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    @Transactional
    public Blog saveBlog(Blog blog) {
        Blog returnBlog = blogRepository.save(blog);
        return returnBlog;
    }

    @Override
    public void removeBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Optional<Blog> getBlogById(Long id) {
        return blogRepository.findById(id);
    }

    @Override
    public Page<Blog> listBlogsByTitleVote(User user, String title, Pageable pageable) {
        //模糊查询
        title = "%" + title + "%";
        String tags = title;
        Page<Blog> blogs = blogRepository.findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(title, user, tags, user, pageable);
        return blogs;
    }

    @Override
    public Page<Blog> listBlogsBtTitleVoteAndSort(User user, String title, Pageable pageable) {
        //模糊查询
        title = "%" + title + "%";
        Page<Blog> blogs = blogRepository.findByUserAndTitleLike(user, title, pageable);
        return blogs;
    }

    @Override
    public void readingIncrease(Long id) {
        Optional<Blog> blog = blogRepository.findById(id);
        Blog blogNew = null;

        if (blog.isPresent()) {//返回 true如果存在值，否则为 false 。
            blogNew = blog.get();
            blogNew.setReadSize(blogNew.getReadSize() + 1);
            this.saveBlog(blogNew);
        }
    }


    @Override
    public Blog createComment(Long blogId, String commentContent) {
        Optional<Blog> optionalBlog = blogRepository.findById(blogId);
        Blog originalBlog = null;
        if (optionalBlog.isPresent()) {
            originalBlog = optionalBlog.get();
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Comment comment = new Comment(user, commentContent);
            originalBlog.addComment(comment);
        }
        return this.saveBlog(originalBlog);
    }

    @Override
    public void removeComment(Long blogId, Long commentId) {
        Optional<Blog> optionalBlog = blogRepository.findById(blogId);
        if (optionalBlog.isPresent()) {
            Blog originalBlog = optionalBlog.get();
            originalBlog.removeComment(commentId);
            this.saveBlog(originalBlog);
        }
    }

    @Override
    public Blog createVote(Long blogId) {
        Optional<Blog> optionalBlog = blogRepository.findById(blogId);
        Blog originalBlog = null;

        if (optionalBlog.isPresent()) {
            originalBlog = optionalBlog.get();
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Vote vote = new Vote(user);
            boolean isExist = originalBlog.addVote(vote);
            if (isExist) {
                throw new IllegalArgumentException("该用户已经点过赞了");
            }
        }
        return this.saveBlog(originalBlog);
    }

    @Override
    public void removeVote(Long blogId, long voteId) {
        Optional<Blog> optionalBlog = blogRepository.findById(blogId);
        Blog originalBlog = null;

        if (optionalBlog.isPresent()) {
            originalBlog = optionalBlog.get();
            originalBlog.removeVote(voteId);
            this.saveBlog(originalBlog);
        }
    }

    @Override
    public Page<Blog> listBlogsByCatalog(Catalog catalog, Pageable pageable) {
        Page<Blog> blogs = blogRepository.findByCatalog(catalog, pageable);
        return blogs;
    }
}
