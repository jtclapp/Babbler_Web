package com.web.CSChat.platform.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class FBInitializer {
    FirebaseApp firebaseApp;

    public FBInitializer() throws IOException {
        InputStream file = getClass().getResourceAsStream("/key.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(file))
                .setDatabaseUrl("https://conspiracy-theory-chat-default-rtdb.firebaseio.com")
                .build();

        firebaseApp = null;
        List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
        if(firebaseApps != null && !firebaseApps.isEmpty())
        {

        }
        else {
            firebaseApp = FirebaseApp.initializeApp(options);
        }
    }
}
