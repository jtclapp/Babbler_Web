package com.web.babbler.platform.Controllers;

import com.web.babbler.platform.models.User;
import com.web.babbler.platform.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.concurrent.ExecutionException;

@Controller
public class ConnectUsersController
{
    UserService userService;

    @GetMapping("/connect")
    public String loadConnectUsersPage(Model model) throws ExecutionException, InterruptedException {
        Authentication user_authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = user_authentication.getName();
        if (!currentUserName.equals("") && !currentUserName.equals("anonymousUser")) {
            model.addAttribute("currentUser", currentUserName);
        } else {
            return "error";
        }
        System.out.println("Switching over to the connect users page.");
        userService = new UserService();
        model.addAttribute("selectedUser",new User());
        model.addAttribute("allUsers",userService.getAllUsers());
        return "connectUsers";
    }
    @GetMapping("/connect/{id}")
    public String loadSelectedUser(@ModelAttribute User user, Model model) throws ExecutionException, InterruptedException {
        Authentication user_authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = user_authentication.getName();
        if (!currentUserName.equals("") && !currentUserName.equals("anonymousUser")) {
            model.addAttribute("currentUser", currentUserName);
        } else {
            return "error";
        }
        System.out.println("Selected User: " + user.getId());
        model.addAttribute("selectedUser",userService.getUser(user.getId()));
        return "selectedUser";
    }
}
