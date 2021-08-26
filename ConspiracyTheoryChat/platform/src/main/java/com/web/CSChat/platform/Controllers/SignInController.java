package com.web.CSChat.platform.Controllers;

import com.google.api.client.auth.oauth2.Credential;
import com.web.CSChat.platform.models.Credentials;
import com.web.CSChat.platform.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.io.IOException;

@Controller
public class SignInController
{
    @GetMapping("/SignIn")
    public String loadLoginPage(Model model)
    {
        System.out.println("Switching over to Sign In page.");
        model.addAttribute("credentials", new Credentials());

        return "signIn";
    }
    @PostMapping("/SignIn")
    public String loginAttempt(@ModelAttribute("credentials") Credentials credentials) throws IOException
    {
        System.out.println("User Credentials: " + credentials.getUsername() + " : " + credentials.getPassword());
        // Use Firebase Authentication
        return "index";
    }
}
