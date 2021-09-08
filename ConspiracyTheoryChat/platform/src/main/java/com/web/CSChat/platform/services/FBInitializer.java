package com.web.CSChat.platform.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FBInitializer {

    @PostConstruct
    public void initialize() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream("ConspiracyTheoryChat/platform/src/main/resources/key.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://conspiracy-theory-chat-default-rtdb.firebaseio.com")
                .setStorageBucket("conspiracy-theory-chat.appspot.com")
                .build();

        FirebaseApp.initializeApp(options);
    }
}
