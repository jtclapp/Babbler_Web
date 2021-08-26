package com.web.CSChat.platform.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConnectUsersController
{
    @GetMapping("/connect")
    public String loadConnectUsersPage(Model model)
    {
        System.out.println("Switching over to the connect users page.");
        return "connectUsers";
    }
}
