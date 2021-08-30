package com.web.CSChat.platform.Controllers;

import com.google.firebase.auth.*;
import com.google.firebase.database.*;
import com.web.CSChat.platform.models.Credentials;
import com.web.CSChat.platform.models.User;
import com.web.CSChat.platform.services.FBInitializer;
import com.web.CSChat.platform.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
public class SignUpController
{
    FirebaseAuth auth;
    FBInitializer fb;
    User user;
    UserService userService;

    @GetMapping("/signUp")
    public String loadSignUpPage(Model model)
    {
        System.out.println("Switching over to Sign Up page.");
        model.addAttribute("signUpCredentials", new Credentials());
        return "signUp";
    }

    @PostMapping("/signedUp")
    public String loginAttempt(@ModelAttribute("signUpCredentials") Credentials credentials) throws IOException, FirebaseAuthException, ExecutionException, InterruptedException {
        fb = new FBInitializer();
        auth = FirebaseAuth.getInstance();
        userService = new UserService();

        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setDisplayName(credentials.getUsername())
                .setEmail(credentials.getEmail())
                .setPassword(credentials.getPassword())
                .setEmailVerified(false)
                .setDisabled(false);

        UserRecord userRecord;
        try {
            userRecord = FirebaseAuth.getInstance().createUser(request);
            user = new User(userRecord.getUid(),userRecord.getDisplayName(),"default");
            userService.createUser(user);
        } catch (FirebaseAuthException e) {
            return "redirect:signUp?error";
        }
//        auth.generateEmailVerificationLink(userRecord.getEmail());
        System.out.println("Successfully created new user: " + userRecord.getUid());
        return "index";
    }
}
