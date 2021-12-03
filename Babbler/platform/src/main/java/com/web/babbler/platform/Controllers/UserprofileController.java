package com.web.babbler.platform.Controllers;

import com.google.firebase.cloud.FirestoreClient;
import com.web.babbler.platform.models.Threads;
import com.web.babbler.platform.models.User;
import com.web.babbler.platform.services.StorageService;
import com.web.babbler.platform.services.ThreadService;
import com.web.babbler.platform.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
public class UserprofileController
{
    List<String> fileList = new ArrayList<>();
    StorageService storageService;
    UserService userService;
    ThreadService threadService;
    String userId;

    @GetMapping("/userprofile")
    public String loadUserprofilePage(Model model) throws ExecutionException, InterruptedException {
        Authentication user_authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = user_authentication.getName();
        if (!currentUserName.equals("") && !currentUserName.equals("anonymousUser")) {
            model.addAttribute("currentUser", currentUserName);
        } else {
            return "error";
        }
        userService = new UserService();
        threadService = new ThreadService();
        model.addAttribute("currentUSER",userService.getUser(userService.getUserID(currentUserName)));
        model.addAttribute("USERThreads",threadService.getAllUserThreads(userService.getUserID(currentUserName)));
        return "userprofile";
    }
    @GetMapping("/editProfile")
    public String loadEditProfilePage(Model model) throws ExecutionException, InterruptedException {
        Authentication user_authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = user_authentication.getName();
        if (!currentUserName.equals("") && !currentUserName.equals("anonymousUser")) {
            model.addAttribute("currentUser", currentUserName);
        } else {
            return "error";
        }
        userService = new UserService();
        model.addAttribute("currentUSER",userService.getUser(userService.getUserID(currentUserName)));
        model.addAttribute("USERBio",userService.getUser(userService.getUserID(currentUserName)));
        return "editprofile";
    }
//    @PostMapping("/createProfile")
//    public String EditProfilePage(@ModelAttribute("files") MultipartFile[] files, @ModelAttribute("currentUSER") User user, Model model) throws IOException {
//        Authentication user_authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentUserName = user_authentication.getName();
//        if (!currentUserName.equals("") && !currentUserName.equals("anonymousUser")) {
//            model.addAttribute("currentUser", currentUserName);
//        }
//        storageService = new StorageService();
//        for (MultipartFile file : files) {
//            if(fileList.size() > 0)
//            {
//                break;
//            }
//            storageService.uploadFile(file);
//            user.setImageURL(storageService.getUploadURL(file.getOriginalFilename()));
//            userId = user.getId();
//        }
//        model.addAttribute("currentUSER",user);
//        model.addAttribute("USERBio",user);
//        return "editprofile";
//    }
//    @PostMapping("/saveProfile")
//    public String SaveProfile(@ModelAttribute("currentUSER") User user, @ModelAttribute("USERBio") User userBio, Model model) throws ExecutionException, InterruptedException {
//        Authentication user_authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentUserName = user_authentication.getName();
//        HashMap<String, Object> hash = new HashMap<>();
//        hash.put("imageURL",user.getImageURL());
//        hash.put("bio",userBio.getBio());
//        FirestoreClient.getFirestore().collection("Users").document(userService.getUserID(currentUserName)).update(hash);
//        return "redirect:/userprofile";
//    }
}