package com.web.blabber.platform.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.web.blabber.platform.models.Threads;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ThreadService
{
    private Firestore firestore;
    private String currentUserName;
    private UserService userService;

    private CollectionReference getThreadCollection() {
        firestore = FirestoreClient.getFirestore();
        return firestore.collection("Threads");
    }
    public void createThread(Threads thread) throws ExecutionException, InterruptedException {
                ApiFuture<WriteResult> apiFuture =
                getThreadCollection().document().set(thread);
                apiFuture.get();
    }
    public List<Threads> getAllThreads() throws ExecutionException, InterruptedException {
        // You can save doc.getId() to the thread, so you can get the exact thread everytime
        List<Threads> threadList = new ArrayList<>();
        CollectionReference threads = getThreadCollection();
        ApiFuture<QuerySnapshot> querySnapshot = threads.get();
        for(DocumentSnapshot doc:querySnapshot.get().getDocuments()) {
            Threads thread = doc.toObject(Threads.class);
            threadList.add(thread);
        }
        return threadList;
    }
//    public Thread getThread(String id) throws ExecutionException, InterruptedException {
//        List<Thread> threadList = getAllThreads();
//        for(int i = 0; i <= threadList.size()-1; i++)
//        {
//            if(threadList.get(i).getId().equals(id))
//            {
//                return threadList.get(i);
//            }
//        }
//        return new Thread();
//    }
    // Add update and delete later
}
