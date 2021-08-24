package com.web.CSChat.platform;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class FB_Initialize
{
    FirebaseApp firebaseApp;

    public FB_Initialize() throws IOException {
        InputStream file = getClass().getResourceAsStream("/key.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(file))
//                .setDatabaseUrl("https://customer-info-853fc-default-rtdb.firebaseio.com")
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
