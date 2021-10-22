package com.web.blabber.platform.services;

import ch.qos.logback.classic.Logger;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.StorageOptions;
import com.web.blabber.platform.Controllers.CreateThreadController;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class StorageService
{
    private final Logger log;
    private final InputStream serviceAccount;
    private final StorageOptions storageOptions;
    private String currentUserName;

    public StorageService() throws IOException {
        this.log = (Logger) LoggerFactory.getLogger(CreateThreadController.class);
        this.serviceAccount = getClass().getResourceAsStream("/key.json");
        assert serviceAccount != null;
        this.storageOptions = StorageOptions.newBuilder().setProjectId("conspiracy-theory-chat").setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        this.currentUserName = authentication.getName();
        currentUserName = currentUserName.replaceAll("@",".");
    }
    public String[] uploadFile(MultipartFile multipartFile) throws IOException {
        Map<String, String> map = new HashMap<>();
        File file = convertMultiPartToFile(multipartFile);
        Path filePath = file.toPath();

        String objectName = "Uploads-" + currentUserName + "/" + multipartFile.getOriginalFilename();
        map.put("firebaseStorageDownloadTokens", objectName);

        BlobId blobId = BlobId.of("conspiracy-theory-chat.appspot.com", objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setMetadata(map).setContentType(multipartFile.getContentType()).build();
        Blob blob = storageOptions.getService().create(blobInfo, Files.readAllBytes(filePath));
        log.info("File " + filePath + " uploaded to bucket " + "conspiracy-theory-chat.appspot.com" + " as " + objectName);
        return new String[]{"fileUrl", objectName};
    }
    public String getUploadURL(String filename)
    {
        String URL = "https://firebasestorage.googleapis.com/v0/b/conspiracy-theory-chat.appspot.com/o/";
        filename = filename.replaceAll(" ","%20");
        URL += "Uploads-" + currentUserName + "%2F";
        URL += filename + "?alt=media";
        return URL;
    }
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = File.createTempFile("temp",null);
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }
}
