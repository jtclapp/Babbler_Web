package com.web.babbler.platform.Controllers;

import com.web.babbler.platform.models.Comments;
import com.web.babbler.platform.models.Credentials;
import com.web.babbler.platform.models.Threads;
import com.web.babbler.platform.services.ThreadService;
import com.web.babbler.platform.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

@Controller
public class ViewThreadController
{
    ThreadService threadService;
    SignInController signInController;

    @GetMapping("/view")
    public String loadViewThreadsPage(Model model) throws ExecutionException, InterruptedException {
        Authentication user_authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = user_authentication.getName();
        if (!currentUserName.equals("") && !currentUserName.equals("anonymousUser")) {
            model.addAttribute("currentUser", currentUserName);
        }
        threadService = new ThreadService();
        model.addAttribute("allThreads",threadService.getAllThreads());
        return "viewThread";
    }
    @GetMapping("/view/{id}")
    public String loadSelectedThreadPage(@ModelAttribute Threads threads,Model model) throws ExecutionException, InterruptedException {
        Authentication user_authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = user_authentication.getName();
        if (!currentUserName.equals("") && !currentUserName.equals("anonymousUser")) {
            model.addAttribute("currentUser", currentUserName);
        } else {
            return "error";
        }
        model.addAttribute("comment", new Comments());
        model.addAttribute("score",threadService.getThread(threads.getId()).getScore());
        model.addAttribute("selectedThread",threadService.getThread(threads.getId()));
        model.addAttribute("selectedThreadComments",threadService.getAllThreadComments(threads.getId()));
        return "selectedThread";
    }
    @PostMapping("/view/{id}/comment")
    public String commentOnSelectedThread(@ModelAttribute("selectedThread") Threads thread,@ModelAttribute("comment") Comments comments,Model model) throws ExecutionException, InterruptedException {
        comments.setTimeStamp(String.valueOf(new Timestamp(System.currentTimeMillis())));
        comments.setDate(new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date()));
        comments.setSender(getUserIDForComment());
        threadService.addCommentToThread(comments,thread);
        model.addAttribute("comment", new Comments());
        model.addAttribute("score",threadService.getThread(thread.getId()).getScore());
        model.addAttribute("selectedThread",threadService.getThread(thread.getId()));
        model.addAttribute("selectedThreadComments",threadService.getAllThreadComments(thread.getId()));
        return "selectedThread";
    }
    @PostMapping("/view/{id}/upvote")
    public String upvoteSelectedThread(@ModelAttribute("selectedThread") Threads thread,Model model) throws ExecutionException, InterruptedException {
        int score = threadService.getThread(thread.getId()).getScore() + 1;
        String id = threadService.getThread(thread.getId()).getId();
        threadService.updateThreadScore(id,score);
        model.addAttribute("comment", new Comments());
        model.addAttribute("score",score);
        model.addAttribute("selectedThread",threadService.getThread(thread.getId()));
        model.addAttribute("selectedThreadComments",threadService.getAllThreadComments(thread.getId()));
        return "selectedThread";
    }
    @PostMapping("/view/{id}/downvote")
    public String downvoteSelectedThread(@ModelAttribute("selectedThread") Threads thread,Model model) throws ExecutionException, InterruptedException {
        int score = threadService.getThread(thread.getId()).getScore() - 1;
        String id = threadService.getThread(thread.getId()).getId();
        threadService.updateThreadScore(id,score);
        model.addAttribute("comment", new Comments());
        model.addAttribute("score",score);
        model.addAttribute("selectedThread",threadService.getThread(thread.getId()));
        model.addAttribute("selectedThreadComments",threadService.getAllThreadComments(thread.getId()));
        return"selectedThread";
    }
    public String getUserIDForComment() throws ExecutionException, InterruptedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        UserService userService = new UserService();
        return userService.getUserID(currentUserName);
    }
}
