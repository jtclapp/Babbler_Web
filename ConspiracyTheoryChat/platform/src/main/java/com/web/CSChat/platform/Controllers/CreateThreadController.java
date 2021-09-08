package com.web.CSChat.platform.Controllers;

import com.web.CSChat.platform.services.StorageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CreateThreadController
{
    List<String> fileList = new ArrayList<>();
    StorageService storageService;
    String uploadPath = "ConspiracyTheoryChat/platform/src/main/resources/static";
    String prefix;
    public CreateThreadController()
    {

    }
    @GetMapping("/create")
    public String loadCreateThreadPage() throws IOException {
        fileList = new ArrayList<>();
        System.out.println("Switching over to the create thread page.");
        return "createThread";
    }
    @PostMapping("/create/save")
    public String saveCreatedThread(@ModelAttribute("files") MultipartFile[] files, Model model) throws Exception {
        storageService = new StorageService();
        for (MultipartFile file : files) {
            storageService.uploadFile(file);
            fileList.add(storageService.getUploadURL(file.getOriginalFilename()));
        }
        model.addAttribute("Photos", fileList);
        return "createThread";
    }
}
