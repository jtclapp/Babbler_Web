package com.web.babbler.platform.Controllers;

import com.web.babbler.platform.models.Comments;
import com.web.babbler.platform.models.Threads;
import com.web.babbler.platform.services.ThreadService;
import com.web.babbler.platform.services.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

@Controller
public class HomeController
{
    List<GrantedAuthority> authorities;
    ThreadService threadService;
    Authentication authentication;
    @GetMapping("/")
    public String loadHomePage(Model model) throws ExecutionException, InterruptedException {
        Authentication user_authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = user_authentication.getName();
        if (currentUserName.equals("") || currentUserName.equals("anonymousUser")) {
            authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("ANONYMOUS_USER"));
            authentication = new UsernamePasswordAuthenticationToken(null, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        if (!currentUserName.equals("") && !currentUserName.equals("anonymousUser")) {
            model.addAttribute("currentUser", currentUserName);
        }
        threadService = new ThreadService();
        model.addAttribute("allRThreads",threadService.getAllRecommendedThreads());
        return "index";
    }
    @GetMapping("/logout")
    public String logoutAttempt(Model model) throws ExecutionException, InterruptedException {
        authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ANONYMOUS_USER"));
        authentication = new UsernamePasswordAuthenticationToken(null, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        loadHomePage(model);
        return "index";
    }
    @GetMapping("/view/recommended/{title}")
    public String loadSelectedRecommendedThread(@ModelAttribute Threads threads, Model model) throws ExecutionException, InterruptedException {
        Authentication user_authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = user_authentication.getName();
        if (!currentUserName.equals("") && !currentUserName.equals("anonymousUser")) {
            model.addAttribute("currentUser", currentUserName);
        } else {
            return "error";
        }
        System.out.println("Selected thread title: " + threads.getTitle());
        model.addAttribute("selectedThread",threadService.getRecommendedThread(threads.getTitle()));
        model.addAttribute("comment", new Comments());
        model.addAttribute("isRecommended",true);
        model.addAttribute("selectedThreadComments",threadService.getAllRecommendedThreadComments(threadService.getRecommendedThread(threads.getTitle()).getId()));
        return "selectedThread";
    }
    @PostMapping("/view/recommended/{title}/comment")
    public String commentOnSelectedRecommendedThread(@ModelAttribute("selectedThread") Threads thread,@ModelAttribute("comment") Comments comments,Model model) throws ExecutionException, InterruptedException {
        threadService = new ThreadService();
        comments.setTimeStamp(String.valueOf(new Timestamp(System.currentTimeMillis())));
        comments.setDate(new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date()));
        comments.setSender(getUserIDForComment());
        threadService.addCommentToRecommendThread(comments,threadService.getRecommendedThread(thread.getTitle()));
        model.addAttribute("selectedThread",threadService.getRecommendedThread(thread.getTitle()));
        model.addAttribute("selectedThreadComments",threadService.getAllRecommendedThreadComments(threadService.getRecommendedThread(thread.getTitle()).getId()));
        return "selectedThread";
    }
    public String getUserIDForComment() throws ExecutionException, InterruptedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        UserService userService = new UserService();
        return userService.getUserID(currentUserName);
    }
}
