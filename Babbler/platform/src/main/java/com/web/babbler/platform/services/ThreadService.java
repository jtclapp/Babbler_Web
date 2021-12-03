package com.web.babbler.platform.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.web.babbler.platform.models.Comments;
import com.web.babbler.platform.models.Threads;
import com.web.babbler.platform.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ThreadService
{
    private Firestore firestore;

    private CollectionReference getThreadCollection() {
        firestore = FirestoreClient.getFirestore();
        return firestore.collection("Threads");
    }
    public void createThread(Threads thread) throws ExecutionException, InterruptedException {
                ApiFuture<WriteResult> apiFuture =
                getThreadCollection().document().set(thread);
                apiFuture.get();
                addIdToThread(thread);
    }
    public void addIdToThread(Threads threads) throws ExecutionException, InterruptedException {
        CollectionReference threadCollection = getThreadCollection();
        ApiFuture<QuerySnapshot> querySnapshot = threadCollection.get();
        for(DocumentSnapshot doc:querySnapshot.get().getDocuments()) {
            Threads thread = doc.toObject(Threads.class);
            assert thread != null;
            if(thread.getSender().equals(threads.getSender()) && thread.getTitle().equals(threads.getTitle()))
            {
                threads.setId(doc.getId());
            }

        }
        ApiFuture<WriteResult> apiFuture = getThreadCollection().document(getThreadId(threads.getTitle(),threads.getSender())).update("id",threads.getId());
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
    public void addCommentToThread(Comments comment,Threads thread) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> apiFuture =
                firestore.collection("Threads").
                        document(thread.getId())
                        .collection("Comments").document().set(comment);
        apiFuture.get();
    }
    public void addCommentToRecommendThread(Comments comment,Threads thread) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> apiFuture =
                FirestoreClient.getFirestore().collection("RecommendThreads").document(thread.getId()).collection("Comments").document().set(comment);
        apiFuture.get();
    }
    public void updateThreadScore(String id,int score)
    {
        getThreadCollection().document(id).update("score",score);
    }
    public void updateRecommendThread(String id, int score)
    {
        getRecommendedThreadCollection().document(id).update("score",score);
    }
    public List<Comments> getAllThreadComments(String id) throws ExecutionException, InterruptedException {
        List<Comments> commentsList = new ArrayList<>();
        firestore = FirestoreClient.getFirestore();
        CollectionReference comments = firestore.collection("Threads").document(id).collection("Comments");
        ApiFuture<QuerySnapshot> querySnapshot = comments.orderBy("timeStamp",Query.Direction.ASCENDING).get();
        for(DocumentSnapshot doc:querySnapshot.get().getDocuments()) {
            Comments comments1 = doc.toObject(Comments.class);
            commentsList.add(comments1);
        }
        return commentsList;
    }
    public List<Comments> getAllRecommendedThreadComments(String id) throws ExecutionException, InterruptedException {
        List<Comments> commentsList = new ArrayList<>();
        firestore = FirestoreClient.getFirestore();
        CollectionReference comments = firestore.collection("RecommendThreads").document(id).collection("Comments");
        ApiFuture<QuerySnapshot> querySnapshot = comments.orderBy("timeStamp",Query.Direction.ASCENDING).get();
        for(DocumentSnapshot doc:querySnapshot.get().getDocuments()) {
            Comments comments1 = doc.toObject(Comments.class);
            commentsList.add(comments1);
        }
        return commentsList;
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
    public List<Threads> orderByScore(boolean trueCrime) throws ExecutionException, InterruptedException {
        List<Threads> threadList = new ArrayList<>();
        firestore = FirestoreClient.getFirestore();
        CollectionReference threads = firestore.collection("Threads");
        ApiFuture<QuerySnapshot> querySnapshot = threads.orderBy("score",Query.Direction.DESCENDING).get();
        for(DocumentSnapshot doc:querySnapshot.get().getDocuments()) {
            Threads threads1 = doc.toObject(Threads.class);
            assert threads1 != null;
            if (threads1.isTrueCrime() == trueCrime) {
                if(threadList.size() < 3)
                {
                    threadList.add(threads1);
                }
            }
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
    public Threads getThread(String id) throws ExecutionException, InterruptedException {
        List<Threads> threadList = getAllThreads();
        for(int i = 0; i <= threadList.size()-1; i++)
        {
                if (threadList.get(i).getId().equals(id)) {
                    return threadList.get(i);
                }
        }
        return new Threads();
    }
    public String getThreadId(String title,String sender) throws ExecutionException, InterruptedException {
        // You can save doc.getId() to the thread, so you can get the exact thread everytime
        CollectionReference threads = getThreadCollection();
        ApiFuture<QuerySnapshot> querySnapshot = threads.get();
        for(DocumentSnapshot doc:querySnapshot.get().getDocuments()) {
            Threads thread = doc.toObject(Threads.class);
            assert thread != null;
            if(thread.getTitle().equals(title) && thread.getSender().equals(sender))
            {
               return doc.getId();
            }
        }
        return "";
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
    // Add update and delete later
}
