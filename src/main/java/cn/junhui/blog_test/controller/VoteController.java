package cn.junhui.blog_test.controller;

import cn.junhui.blog_test.domain.User;
import cn.junhui.blog_test.domain.Vote;
import cn.junhui.blog_test.service.BlogService;
import cn.junhui.blog_test.service.VoteService;
import cn.junhui.blog_test.util.ConstraintViolationExceptionHandler;
import cn.junhui.blog_test.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

/**
 * 军辉
 * 2019-02-12 15:37
 */
@RestController
@RequestMapping("/votes")
public class VoteController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private VoteService voteService;


    /*
    发表点赞
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Response> createVote(Long blogId) {
        try {
            blogService.createVote(blogId);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true, "点赞成功", null
        ));
    }

    /*
    删除点赞
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    private ResponseEntity<Response> delete(@PathVariable("id") Long voteId, Long blogId) {

        boolean isOwner = false;
        Optional<Vote> optionalVote = voteService.getVoteById(voteId);
        User user = null;
        if (optionalVote.isPresent()) {
            user = optionalVote.get().getUser();
        } else {
            return ResponseEntity.ok().body(new Response(false, "不存在该点赞"));
        }

        //判断操作用户是否为点赞的所有者
        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null && user.getUsername().equals(principal.getUsername())) {
                isOwner = true;
            }
        }
        if (!isOwner) {
            return ResponseEntity.ok().body(new Response(false, "没有操作权限"));
        }

        try {
            blogService.removeVote(blogId, voteId);
            voteService.removeVote(voteId);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true, "取消点赞成功"));
    }
}
