package com.web.blabber.platform.Controllers;

import com.web.blabber.platform.models.Credentials;
import com.web.blabber.platform.models.Threads;
import com.web.blabber.platform.services.ThreadService;
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

    @GetMapping("/view")
    public String loadViewThreadsPage(Model model) throws ExecutionException, InterruptedException {
        Authentication user_authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = user_authentication.getName();
        if (!currentUserName.equals("") && !currentUserName.equals("anonymousUser")) {
            model.addAttribute("currentUser", currentUserName);
        }
        threadService = new ThreadService();
        model.addAttribute("selectedThread",new Threads());
        model.addAttribute("allThreads",threadService.getAllThreads());
        return "viewThread";
    }
    @GetMapping("/view/{sender}/{title}")
    public String loadSelectedThreadPage(@ModelAttribute Threads threads,Model model) {

        System.out.println("Selected thread title: " + threads.getTitle());
        System.out.println("Selected thread sender: " + threads.getSender());
        model.addAttribute("SelectedTitle",threads.getTitle());
        model.addAttribute("SelectedSender",threads.getSender());
        return "selectedThread";
    }
}
