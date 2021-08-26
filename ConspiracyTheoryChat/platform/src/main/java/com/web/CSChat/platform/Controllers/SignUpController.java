package com.web.CSChat.platform.Controllers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.web.CSChat.platform.models.Credentials;
import com.web.CSChat.platform.services.FBInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.io.IOException;

@Controller
public class SignUpController
{
    FirebaseAuth auth;
    FBInitializer fb;
    @GetMapping("/signUp")
    public String loadSignUpPage(Model model)
    {
        System.out.println("Switching over to Sign Up page.");
        model.addAttribute("signUpCredentials", new Credentials());
        return "signUp";
    }
    @PostMapping("/SignUp")
    public String loginAttempt(@ModelAttribute("signUpCredentials") Credentials credentials) throws IOException, FirebaseAuthException {
        fb = new FBInitializer();
        auth = FirebaseAuth.getInstance();

        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setDisplayName(credentials.getUsername())
                .setEmail(credentials.getEmail())
                .setPassword(credentials.getPassword())
                .setEmailVerified(false)
                .setDisabled(false);

        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
        System.out.println("Successfully created new user: " + userRecord.getUid());
        return "index";
    }
}
