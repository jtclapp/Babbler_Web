package com.web.babbler.platform.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.web.babbler.platform.models.Threads;
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
    public void addThreadToUser(Threads thread) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> apiFuture =
        firestore.collection("Users").
        document(thread.getSender()).collection("CreatedThreads").document(thread.getTitle()).set(thread);
        apiFuture.get();
    }
    public boolean uniqueThread(String title, String id) throws ExecutionException, InterruptedException {
        List<Threads> threadList = getAllUserThreads(id);
        for(int i = 0; i <= threadList.size()-1; i++)
        {
            if(threadList.get(i).getTitle().equals(title))
            {
                return false;
            }
        }
        return true;
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
    public List<Threads> getAllUserThreads(String id) throws ExecutionException, InterruptedException {
        List<Threads> threadList = new ArrayList<>();
        firestore = FirestoreClient.getFirestore();
        CollectionReference threads = firestore.collection("Users").document(id).collection("CreatedThreads");
        ApiFuture<QuerySnapshot> querySnapshot = threads.get();
        for(DocumentSnapshot doc:querySnapshot.get().getDocuments()) {
            Threads thread = doc.toObject(Threads.class);
            threadList.add(thread);
        }
        return threadList;
    }
    private CollectionReference getRecommendedThreadCollection() {
        firestore = FirestoreClient.getFirestore();
        return firestore.collection("RecommendThreads");
    }
    public List<Threads> getAllRecommendedThreads() throws ExecutionException, InterruptedException {
        List<Threads> threadList = new ArrayList<>();
        CollectionReference threads = getRecommendedThreadCollection();
        ApiFuture<QuerySnapshot> querySnapshot = threads.get();
        for(DocumentSnapshot doc:querySnapshot.get().getDocuments()) {
            Threads thread = doc.toObject(Threads.class);
            assert thread != null;
            thread.setCaption(blurbCreator(thread.getCaption()));
            threadList.add(thread);
        }
        return threadList;
    }
    public List<Threads> getAllRecommendedThreadsNoBlurb() throws ExecutionException, InterruptedException {
        List<Threads> threadList = new ArrayList<>();
        CollectionReference threads = getRecommendedThreadCollection();
        ApiFuture<QuerySnapshot> querySnapshot = threads.get();
        for(DocumentSnapshot doc:querySnapshot.get().getDocuments()) {
            Threads thread = doc.toObject(Threads.class);
            assert thread != null;
            thread.setCaption(thread.getCaption());
            threadList.add(thread);
        }
        return threadList;
    }
    public Threads getRecommendedThread(String title) throws ExecutionException, InterruptedException {
        List<Threads> threadList = getAllRecommendedThreadsNoBlurb();
        for(int i = 0; i <= threadList.size()-1; i++)
        {
            if(threadList.get(i).getTitle().equals(title))
            {
                return threadList.get(i);
            }
        }
        return new Threads();
    }
    public String blurbCreator(String text)
    {
        String[] brokenText = text.split(" ");
        text = "";
        for(int i = 0; i < brokenText.length; i++)
        {
            if(i >= 30)
            {
                break;
            }
            if(i != 0)
            {
                text += " ";
            }
            text += brokenText[i];
        }
        text += "...";
        return text;
    }
    public Threads getThread(String title, String sender) throws ExecutionException, InterruptedException {
        List<Threads> threadList = getAllThreads();
        for(int i = 0; i <= threadList.size()-1; i++)
        {
            if(threadList.get(i).getTitle().equals(title)
                    && threadList.get(i).getSender().equals(sender))
            {
                return threadList.get(i);
            }
        }
        return new Threads();
    }
    // Add update and delete later
}
