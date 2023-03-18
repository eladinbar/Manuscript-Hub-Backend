package com.manuscript.infrastructure.firebase.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.manuscript.infrastructure.firebase.security.Roles.RoleConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.management.relation.RoleNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthenticationService implements IAuthenticationService {
    FirebaseAuth auth;
    FirebaseApp firebaseApp;

    @Value("classpath:firebase/serviceAccount.json")
    Resource accountPath;


    @PostConstruct
    private void postConstruct() throws IOException {
        System.out.println(accountPath);
        this.firebaseApp = getFirebaseApp();
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public FirebaseApp getFirebaseApp() throws IOException {
//        FileInputStream refreshToken = new FileInputStream(ClassLoader.getSystemResource("firebase/serviceAccount.json").getFile());
        InputStream refreshToken = accountPath.getInputStream();

        FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(refreshToken)).build();

        FirebaseApp.initializeApp(options);
        return FirebaseApp.getInstance();
    }

    @Override
    public FirebaseToken verifyIdToken(String idToken) throws FirebaseAuthException {
        return auth.verifyIdToken(idToken);
    }

    @Override
    public void addRole(String uid, String role) throws FirebaseAuthException, RoleNotFoundException {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        UserRecord user = firebaseAuth.getUser(uid);
        if (RoleConstants.isRole(role)) {
            Map<String, Object> claims = new HashMap<>();
            user.getCustomClaims().forEach((k, v) ->
                    claims.put(k, v));
            if (!claims.containsKey(role.toLowerCase())) {
                claims.put(role.toLowerCase(), true);
            }

            firebaseAuth.setCustomUserClaims(uid, claims);
        } else {
            throw new RoleNotFoundException(role + " Is not a role");
        }
    }

    @Override
    public void removeRole(String uid, String role) throws FirebaseAuthException, RoleNotFoundException {
        if (RoleConstants.isRole(role)) {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            UserRecord user = firebaseAuth.getUser(uid);
            Map<String, Object> claims = new HashMap<>();
            user.getCustomClaims().forEach((k, v) -> claims.put(k, v));
            if (claims.containsKey(role.toLowerCase())) {
                claims.remove(role);
            }
            firebaseAuth.setCustomUserClaims(uid, claims);
        } else {
            throw new RoleNotFoundException(role + "Is not a role");
        }
    }
}
