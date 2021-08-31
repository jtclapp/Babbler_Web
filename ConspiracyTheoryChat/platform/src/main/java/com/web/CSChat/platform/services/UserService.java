package com.web.CSChat.platform.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.web.CSChat.platform.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {

    private Firestore firestore;

    private CollectionReference getUserCollection() {
        firestore = FirestoreClient.getFirestore();
        return firestore.collection("Users");
    }
    public String createUser(User user) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> apiFuture =
                getUserCollection().document(user.getId()).set(user);
        return apiFuture.get().getUpdateTime().toString();
    }
    public List<User> getAllUsers() throws ExecutionException, InterruptedException {
        List<User> userList = new ArrayList<>();
        CollectionReference users = getUserCollection();
        ApiFuture<QuerySnapshot> querySnapshot = users.get();
        for(DocumentSnapshot doc:querySnapshot.get().getDocuments()) {
            User usr = doc.toObject(User.class);
            userList.add(usr);
        }
        return userList;
    }
}
