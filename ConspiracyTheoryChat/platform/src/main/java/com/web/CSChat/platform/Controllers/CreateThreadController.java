package com.web.CSChat.platform.Controllers;

import com.web.CSChat.platform.models.Credentials;
import com.web.CSChat.platform.models.Thread;
import com.web.CSChat.platform.services.StorageService;
import com.web.CSChat.platform.services.ThreadService;
import com.web.CSChat.platform.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

@Controller
public class CreateThreadController
{
    List<String> fileList = new ArrayList<>();
    StorageService storageService;
    ThreadService threadService;
    com.web.CSChat.platform.models.Thread thread;
    String currentUserName;
    UserService userService;
    public CreateThreadController()
    {

    }
    @GetMapping("/create")
    public String loadCreateThreadPage(Model model) throws IOException {
        fileList = new ArrayList<>();
        model.addAttribute("createdThread",new com.web.CSChat.platform.models.Thread());
        System.out.println("Switching over to the create thread page.");
        return "createThread";
    }
    @PostMapping("/create/add")
    public String addToThread(@ModelAttribute("files") MultipartFile[] files, Model model) throws Exception {
        storageService = new StorageService();
        for (MultipartFile file : files) {
            if(fileList.size() > 4)
            {
                model.addAttribute("Photos", fileList);
                model.addAttribute("createdThread", new com.web.CSChat.platform.models.Thread());
                model.addAttribute("capacityMessage","You can only upload five photos per post.");
                return "createThread";
            }
            storageService.uploadFile(file);
            fileList.add(storageService.getUploadURL(file.getOriginalFilename()));
        }
        model.addAttribute("createdThread",new com.web.CSChat.platform.models.Thread());
        model.addAttribute("Photos", fileList);
        return "createThread";
    }
    @PostMapping("/create/save")
    public String saveCreatedThread(@ModelAttribute("createdThread")com.web.CSChat.platform.models.Thread capturedThread) throws ExecutionException, InterruptedException, IOException {
        storageService = new StorageService();
        threadService = new ThreadService();
        thread = new com.web.CSChat.platform.models.Thread();
        thread.setTitle(capturedThread.getTitle());
        thread.setDate(getCurrentDate());
        thread.setSender(getUserIDForThread());
        thread.setCaption(capturedThread.getCaption());
        for(int i = 0; i < fileList.size(); i++)
        {
            if(i == 0) {
                thread.setImage1(fileList.get(i));
            } else if (i == 1) {
                thread.setImage2(fileList.get(i));
            } else if (i == 2) {
                thread.setImage3(fileList.get(i));
            } else if (i == 3) {
                thread.setImage4(fileList.get(i));
            } else if (i == 4) {
                thread.setImage5(fileList.get(i));
            }
        }
        threadService.createThread(thread);
        return "redirect:/";
    }
    public String getUserIDForThread() throws ExecutionException, InterruptedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        currentUserName = authentication.getName();
        userService = new UserService();
        return userService.getUserID(currentUserName);
    }
    public String getCurrentDate() {
        return new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date());
    }
}
