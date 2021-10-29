package com.web.blabber.platform.Controllers;

import com.web.blabber.platform.models.User;
import com.web.blabber.platform.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String loadSelectedUser(@ModelAttribute User user, Model model) {
        System.out.println("Selected User: " + user.getId());
        model.addAttribute("SelectedUserId",user.getId());
        return "selectedUser";
    }
}
