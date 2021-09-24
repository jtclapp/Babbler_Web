package com.web.blabber.platform.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController
{
    @GetMapping("/about")
    public String loadAboutPage(Model model)
    {
        System.out.println("Switching over to the about page.");
        return "about";
    }
}
