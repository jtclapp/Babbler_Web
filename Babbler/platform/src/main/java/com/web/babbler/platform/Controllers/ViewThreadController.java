package com.web.babbler.platform.Controllers;

import com.web.babbler.platform.models.Comments;
import com.web.babbler.platform.models.Threads;
import com.web.babbler.platform.services.ThreadService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        System.out.println("Selected thread sender: " + threads.getId());
        //threadService.addCommentToThread(new Comments("","jtclapp","This article is alright ig","11/8/2021"),threads);
        model.addAttribute("selectedThread",threadService.getThread(threads.getId()));
        model.addAttribute("selectedThreadComments",threadService.getAllThreadComments(threads.getId()));

        return "selectedThread";
    }
    @PostMapping("/view/{id}/upvote")
    public String upvoteSelectedThread(@ModelAttribute("selectedThread") Threads thread,Model model) throws ExecutionException, InterruptedException {
        int temp = thread.getScore()+1;


        thread.incrementScore(1);


        threadService.updateThreadScore(thread,"score");
        System.out.println("Selected thread sender: " + thread.getId());
        model.addAttribute("selectedThread",threadService.getThread(thread.getId()));
        model.addAttribute("selectedThreadComments",threadService.getAllThreadComments(thread.getId()));
        System.out.print(thread.getScore());
        return "selectedThread";
    }
    @PostMapping("/view/{id}/downvote")
    public String downvoteSelectedThread(@ModelAttribute("selectedThread") Threads thread,Model model) throws ExecutionException, InterruptedException {
    int temp =thread.getScore()-1;


        thread.decrementScore(1);


        threadService.updateThreadScore(thread,"score");
        System.out.println("Selected thread sender: " + thread.getId());
        model.addAttribute("selectedThread",threadService.getThread(thread.getId()));
        model.addAttribute("selectedThreadComments",threadService.getAllThreadComments(thread.getId()));
        System.out.print(thread.getScore());
        return"selectedThread";
    }

}
