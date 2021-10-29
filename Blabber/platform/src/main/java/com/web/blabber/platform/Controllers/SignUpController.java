package com.web.blabber.platform.Controllers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.web.blabber.platform.models.Credentials;
import com.web.blabber.platform.models.User;
import com.web.blabber.platform.services.FBInitializer;
import com.web.blabber.platform.services.UserService;
import com.web.blabber.platform.services.EmailService;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Controller
public class SignUpController
{
    FirebaseAuth auth;
    FBInitializer fb;
    User user;
    UserService userService;
    boolean result;
    @GetMapping("/signUp")
    public String loadSignUpPage(Model model)
    {
        Authentication user_authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = user_authentication.getName();
        if (!currentUserName.equals("") && !currentUserName.equals("anonymousUser")) {
            model.addAttribute("currentUser", currentUserName);
        }
        model.addAttribute("signUpCredentials", new Credentials());
        return "signUp";
    }
    @PostMapping("/signedUp")
    public String loginAttempt(@ModelAttribute("signUpCredentials") Credentials credentials) throws IOException, FirebaseAuthException, ExecutionException, InterruptedException, MessagingException {
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
            user = new User(userRecord.getUid(),userRecord.getDisplayName(),userRecord.getEmail(),credentials.getPassword(),"default" , "",false);
            userService.createUser(user);
            result = userService.authenticateUser(credentials.getEmail(),credentials.getPassword());
        } catch (FirebaseAuthException e) {
            return "redirect:signUp?error";
        }
        if(result) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if ((authentication instanceof UsernamePasswordAuthenticationToken)) {
                String currentUserName = authentication.getName();
                System.out.println("Current User: " + currentUserName);

                String randomCode = RandomString.make(64);
                user.setVerification_code(randomCode);
                user.setVerified(false);
                String siteURL = "localhost" ;
                EmailService.sendVerificationEmail(user, siteURL);
            } else {
                System.out.println("Error Occurred...");
            }
            return "redirect:/";
        } else {
            return "redirect:signUp?error";
        }
    }
}
