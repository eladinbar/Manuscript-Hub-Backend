package com.manuscript.infrastructure.firebase.service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import javax.management.relation.RoleNotFoundException;
import java.io.IOException;

public interface IAuthenticationService {
    FirebaseApp getFirebaseApp() throws IOException;
    FirebaseToken verifyIdToken(String idToken) throws FirebaseAuthException;
    void addRole(String uid, String role) throws FirebaseAuthException, RoleNotFoundException;
    void removeRole(String uid, String role) throws FirebaseAuthException, RoleNotFoundException;
}
