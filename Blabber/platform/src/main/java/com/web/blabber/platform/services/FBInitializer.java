package com.web.blabber.platform.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FBInitializer {

    @PostConstruct
    public void initialize() throws IOException {
        InputStream serviceAccount = getClass().getResourceAsStream("/key.json");

        assert serviceAccount != null;
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://conspiracy-theory-chat-default-rtdb.firebaseio.com")
                .setStorageBucket("conspiracy-theory-chat.appspot.com")
                .build();

        FirebaseApp.initializeApp(options);
    }
}
