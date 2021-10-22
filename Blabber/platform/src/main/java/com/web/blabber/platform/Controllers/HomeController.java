package com.web.blabber.platform.Controllers;

import com.web.blabber.platform.services.ThreadService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
public class HomeController
{
    List<GrantedAuthority> authorities;
    ThreadService threadService;
    Authentication authentication;
    @GetMapping("/")
    public String loadHomePage(Model model) throws ExecutionException, InterruptedException {
        Authentication user_authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = user_authentication.getName();
        if (currentUserName.equals("") || currentUserName.equals("anonymousUser")) {
            authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("ANONYMOUS_USER"));
            authentication = new UsernamePasswordAuthenticationToken(null, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        if (!currentUserName.equals("") && !currentUserName.equals("anonymousUser")) {
            model.addAttribute("currentUser", currentUserName);
        }
        threadService = new ThreadService();
        model.addAttribute("allRThreads",threadService.getAllRecommendedThreads());
        return "index";
    }
    @GetMapping("/logout")
    public String logoutAttempt()
    {
        authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ANONYMOUS_USER"));
        authentication = new UsernamePasswordAuthenticationToken(null, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "index";
    }
}
