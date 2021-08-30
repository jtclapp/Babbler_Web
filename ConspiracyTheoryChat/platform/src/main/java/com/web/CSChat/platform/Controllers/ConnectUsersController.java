package com.web.CSChat.platform.Controllers;

import com.web.CSChat.platform.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.concurrent.ExecutionException;

@Controller
public class ConnectUsersController
{
    UserService userService;

    @GetMapping("/connect")
    public String loadConnectUsersPage(Model model) throws ExecutionException, InterruptedException {
        System.out.println("Switching over to the connect users page.");
        userService = new UserService();
        model.addAttribute("allUsers",userService.getAllUsers());
        return "connectUsers";
    }
}
