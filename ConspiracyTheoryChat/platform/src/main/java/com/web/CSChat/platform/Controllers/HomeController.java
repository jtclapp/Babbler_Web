package com.web.CSChat.platform.Controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController
{
    @GetMapping("/")
    public String loadHomePage(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        if (!currentUserName.equals("anonymousUser")) {
            model.addAttribute("currentUser", currentUserName);
        }
        return "index";
    }
}
