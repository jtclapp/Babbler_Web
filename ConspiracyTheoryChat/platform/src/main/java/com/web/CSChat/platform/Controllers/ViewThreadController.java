package com.web.CSChat.platform.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewThreadController
{
    @GetMapping("/view")
    public String loadViewThreadsPage(Model model)
    {
        System.out.println("Switching over to the view threads page.");
        return "viewThread";
    }
}
