package com.web.CSChat.platform.Controllers;

import com.google.api.client.auth.oauth2.Credential;
import com.google.firebase.auth.FirebaseAuth;
import com.web.CSChat.platform.models.Credentials;
import com.web.CSChat.platform.models.User;
import com.web.CSChat.platform.services.FBInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.io.IOException;

@Controller
public class SignInController
{
    FirebaseAuth auth;
    FBInitializer fb;
    @GetMapping("/signIn")
    public String loadLoginPage(Model model)
    {
        System.out.println("Switching over to Sign In page.");
        model.addAttribute("credentials", new Credentials());
        return "signIn";
    }
    @PostMapping("/SignIn")
    public String loginAttempt(@ModelAttribute("credentials") Credentials credentials) throws IOException
    {
        fb = new FBInitializer();
        auth = FirebaseAuth.getInstance();
        return "index";
    }
}
