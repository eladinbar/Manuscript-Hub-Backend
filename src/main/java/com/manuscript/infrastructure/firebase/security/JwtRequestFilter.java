package com.manuscript.infrastructure.firebase.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.Fir+ebaseToken;
import com.google.firebase.auth.UserRecord;
import com.sirajtechnologies.iot.dhv.Services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    AuthenticationService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        logger.info("Request received - path: " + request.getRequestURI() + ", host: " + request.getLocalAddr() + ", " + request.getRemoteAddr());
        String uri = request.getRequestURI();
        if (uri.startsWith("/api")) {
            final String requestTokenHeader = request.getHeader("Authorization");
            if (requestTokenHeader != null) {
                String Bearer = requestTokenHeader.substring(0, 7);
                if (Bearer.equals("Bearer ")) {
                    String[] authToken = requestTokenHeader.split(" ");
                    String idToken = authToken[1];
                    try {
                        FirebaseToken firebaseToken = authService.verifyIdToken(idToken);
                        String uid = firebaseToken.getUid();
                        if (uid != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                            UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
                            ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
                            userRecord.getCustomClaims().forEach((k, v) -> authorities.add(new SimpleGrantedAuthority(k)));

                            SecurityContextHolder.getContext().setAuthentication(new UserAuthModel(uid, authorities));
                            chain.doFilter(request, response);

                        }
                    } catch (FirebaseAuthException e) {
                        logger.warn("FirebaseException: " + e + "id token: " + idToken);
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        Map<String, String> error = new HashMap<>();
                        error.put("error", e.getMessage());
                        error.put("errorCode", e.getErrorCode().toString());
                        response.setContentType(APPLICATION_JSON_VALUE);
                        new ObjectMapper().writeValue(response.getOutputStream(), error);
                    }


                } else {
                    logger.warn("Auth token doesn't start with bearer");
                }
            } else {
                logger.warn("Auth header cannot be empty");
            }
        }
    }


}