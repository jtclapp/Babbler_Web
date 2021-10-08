package com.web.blabber.platform.Controllers;

import com.google.firebase.auth.FirebaseAuth;
import com.web.blabber.platform.models.Credentials;
import com.web.blabber.platform.services.FBInitializer;
import com.web.blabber.platform.services.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
public class SignInController
{
    FirebaseAuth auth;
    FBInitializer fb;
    List<GrantedAuthority> authorities;
    Authentication authentication;
    UserService userService;
    @GetMapping("/signIn")
    public String loadLoginPage(Model model) {
            Authentication user_authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserName = user_authentication.getName();
            if (!currentUserName.equals("") && !currentUserName.equals("anonymousUser")) {
                model.addAttribute("currentUser", currentUserName);
            }
            model.addAttribute("credentials", new Credentials());
            return "signIn";
    }
    @PostMapping("/SignIn")
    public String loginAttempt(@ModelAttribute("credentials") Credentials credentials) throws ExecutionException, InterruptedException
    {
        fb = new FBInitializer();
        auth = FirebaseAuth.getInstance();
        userService = new UserService();
        boolean result = userService.authenticateUser(credentials.getEmail(),credentials.getPassword());
        if(result) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if ((authentication instanceof UsernamePasswordAuthenticationToken)) {
                String currentUserName = authentication.getName();
                System.out.println("Current User: " + currentUserName);
            } else {
                System.out.println("Not Signed In!");
            }
            return "redirect:/";
        } else {
            return "redirect:signIn?error";
        }
    }
}
