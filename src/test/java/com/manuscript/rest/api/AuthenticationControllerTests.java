package com.manuscript.rest.api;

import com.manuscript.core.domain.common.enums.Role;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.core.exceptions.NoUserFoundException;
import com.manuscript.core.exceptions.UserAlreadyExistException;
import com.manuscript.infrastructure.firebase.service.IAuthenticationService;
import com.manuscript.rest.forms.request.UserRequest;
import com.manuscript.rest.forms.response.UserResponse;
import com.manuscript.rest.service.IUserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthenticationControllerTests {
    private IAuthenticationService authService;
    private IUserService userService;
    private AuthenticationController authController;
    private final UUID userId = UUID.randomUUID();
    private final UUID invalidUserId = UUID.fromString("00000000-98d6-42c7-aca8-763bf4cbfd23");
    private final String email = "email@gmail.com";
    private final String invalidEmail = "emailgmail.com";
    private String name = "asd";
    private String phoneNumber = "234234";
    private Status status;
    private String uid = "";
    private Role role;
    private Date createdTime;
    private Date updatedTime;
    private UserRequest userRequest;
    private UserResponse userResponse;

    @BeforeAll
    public void setUp() {
        // set up time
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.APRIL);
        cal.set(Calendar.DAY_OF_MONTH, 16);
        this.createdTime = cal.getTime();
        this.updatedTime = cal.getTime();

        // set up controller and mocked service
        userService = Mockito.mock(IUserService.class);
        authService = Mockito.mock(IAuthenticationService.class);
        authController = new AuthenticationController(authService, userService);
    }

    @BeforeEach
    public void beforeEach() {
        // create user response
        this.userResponse = UserResponse.builder()
                .id(userId)
                .role(Role.User)
                .createdTime(this.createdTime)
                .updatedTime(this.updatedTime)
                .email(email)
                .uid(uid)
                .name(name)
                .phoneNumber(phoneNumber)
                .status(status).build();

        // create user request
        this.userRequest = UserRequest.builder()
                .role(Role.User)
                .email(email)
                .uid(uid)
                .name(name)
                .id(userId)
                .phoneNumber(phoneNumber)
                .newUser(false).build();
    }

    ////--------------------------------------- 'login' tests

    @Test
    public void login_Success() {
        //set up
        when(userService.getByUid(any(String.class))).thenReturn(userResponse);

        //act
        ResponseEntity<UserResponse> response = authController.login(userRequest);

        //assert
        assertTrue(response.hasBody());
        UserResponse returnedResponse = response.getBody();
        assertEquals(userRequest.getId(), returnedResponse.getId());
        assertEquals(userRequest.getRole(), returnedResponse.getRole());
        assertEquals(userRequest.getName(), returnedResponse.getName());
        assertEquals(userRequest.getUid(), returnedResponse.getUid());
        assertEquals(userRequest.getPhoneNumber(), returnedResponse.getPhoneNumber());
        assertEquals(userRequest.getEmail(), returnedResponse.getEmail());
    }

    @Test
    public void login_UserNotFoundOnSQL() {
        //set up
        when(userService.getByUid(any(String.class))).thenThrow(NoUserFoundException.class);
        when(userService.save((any(UserRequest.class)))).thenReturn(userResponse);

        //act
        ResponseEntity<UserResponse> response = authController.login(userRequest);

        //assert
        assertTrue(response.hasBody());
        UserResponse returnedResponse = response.getBody();
        assertEquals(userRequest.getId(), returnedResponse.getId());
        assertEquals(userRequest.getRole(), returnedResponse.getRole());
        assertEquals(userRequest.getName(), returnedResponse.getName());
        assertEquals(userRequest.getUid(), returnedResponse.getUid());
        assertEquals(userRequest.getPhoneNumber(), returnedResponse.getPhoneNumber());
        assertEquals(userRequest.getEmail(), returnedResponse.getEmail());
    }

    ////--------------------------------------- 'register' tests

    @Test
    public void register_Success() {
        //set up
        when(userService.save((any(UserRequest.class)))).thenReturn(userResponse);

        //act
        ResponseEntity<UserResponse> response = authController.register(userRequest);

        //assert
        assertTrue(response.hasBody());
        UserResponse returnedResponse = response.getBody();
        assertEquals(userRequest.getId(), returnedResponse.getId());
        assertEquals(userRequest.getRole(), returnedResponse.getRole());
        assertEquals(userRequest.getName(), returnedResponse.getName());
        assertEquals(userRequest.getUid(), returnedResponse.getUid());
        assertEquals(userRequest.getPhoneNumber(), returnedResponse.getPhoneNumber());
        assertEquals(userRequest.getEmail(), returnedResponse.getEmail());
    }

    @Test
    public void register_Failure() {
        //set up
        when(userService.save((any(UserRequest.class)))).thenThrow(UserAlreadyExistException.class);

        //act
        ResponseEntity<UserResponse> response = authController.register(userRequest);

        //assert
        assertFalse(response.hasBody());
    }
}
