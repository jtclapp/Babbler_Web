package com.web.CSChat.platform.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CreateThreadController
{
    @GetMapping("/create")
    public String loadCreateThreadPage(Model model)
    {
        System.out.println("Switching over to the create thread page.");
        return "createThread";
    }
}
