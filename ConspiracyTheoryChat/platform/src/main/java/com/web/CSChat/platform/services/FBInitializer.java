package com.web.CSChat.platform.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class FBInitializer {
     FirebaseApp firebaseApp;

    public FBInitializer() throws IOException {
        firebaseApp = null;
        InputStream file = getClass().getResourceAsStream("/key.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(file))
                .setDatabaseUrl("https://conspiracy-theory-chat-default-rtdb.firebaseio.com")
                .build();

        List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
        if(firebaseApps != null && !firebaseApps.isEmpty())
        {

        }
        else {
            firebaseApp = FirebaseApp.initializeApp(options);
        }
    }
}
