package com.web.blabber.platform.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.web.blabber.platform.models.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {


    private Firestore firestore;
    List<GrantedAuthority> authorities;
    UserDetails user;
    InMemoryUserDetailsManager userDetailsManager;
    Authentication authentication;

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
    public boolean authenticateUser(String email, String password) throws ExecutionException, InterruptedException {
        List<User> userList = getAllUsers();
        authorities = new ArrayList<>();
        userDetailsManager = new InMemoryUserDetailsManager();
        authorities.add(new SimpleGrantedAuthority("USER"));
        for(int i = 0; i <= userList.size()-1; i++)
        {
            if(userList.get(i).getEmail().equals(email) && userList.get(i).getPassword().equals(password))
            {
                SecurityContextHolder.clearContext();
                user = new org.springframework.security.core.userdetails.User(userList.get(i).getEmail(),userList.get(i).getPassword(), authorities);
                userDetailsManager.createUser(user);
                authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return true;
            }
        }
        return false;
    }
    public User getUser(String email) throws ExecutionException, InterruptedException {
        CollectionReference users = getUserCollection();
        ApiFuture<QuerySnapshot> querySnapshot = users.get();
        for(DocumentSnapshot doc:querySnapshot.get().getDocuments()) {
            User usr = doc.toObject(User.class);
            if(usr.getEmail().equals(email))
            {
                return usr;
            }
        }
        return new User();
    }
    public String getUserID(String email) throws ExecutionException, InterruptedException {
        CollectionReference users = getUserCollection();
        ApiFuture<QuerySnapshot> querySnapshot = users.get();
        for(DocumentSnapshot doc:querySnapshot.get().getDocuments()) {
            User usr = doc.toObject(User.class);
            if(usr.getEmail().equals(email))
            {
                return usr.getId();
            }
        }
        return "Invalid";
    }
    // Add update and delete later
}
