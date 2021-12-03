package com.web.babbler.platform.Controllers;

import com.web.babbler.platform.services.ThreadService;
import com.web.babbler.platform.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.concurrent.ExecutionException;

@Controller
public class UserprofileController
{
    UserService userService;
    ThreadService threadService;

    @GetMapping("/userprofile")
    public String loadUserprofilePage(Model model) throws ExecutionException, InterruptedException {
        Authentication user_authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = user_authentication.getName();
        if (!currentUserName.equals("") && !currentUserName.equals("anonymousUser")) {
            model.addAttribute("currentUser", currentUserName);
        } else {
            return "error";
        }
        userService = new UserService();
        threadService = new ThreadService();
        model.addAttribute("currentUSER",userService.getUser(userService.getUserID(currentUserName)));
        model.addAttribute("USERThreads",threadService.getAllUserThreads(userService.getUserID(currentUserName)));
        return "userprofile";
    }
}