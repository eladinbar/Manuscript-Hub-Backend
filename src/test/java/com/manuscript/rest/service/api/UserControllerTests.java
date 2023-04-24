package com.manuscript.rest.service.api;

import com.manuscript.core.domain.common.enums.Role;
import com.manuscript.rest.api.UserController;
import com.manuscript.rest.request.UserRequest;
import com.manuscript.rest.response.UserResponse;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTests {
    private IUserService userService;
    private UserController userController;
    private final UUID userId = UUID.randomUUID();
    private final UUID invalidUserId = UUID.fromString("00000000-98d6-42c7-aca8-763bf4cbfd23");

    private final String email = "email@gmail.com";
    private final String invalidEmail = "emailgmail.com";
    private String name = "asd";
    private String phoneNumber = "234234";
    private String status;
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
        userController = new UserController(userService);
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
                .phoneNumber(phoneNumber)
                .newUser(false).build();    }

    @Test
    public void getUserByIdTest_Success() {
        //set up
        when(userService.getById(any(UUID.class))).thenReturn(userResponse);

        //act
        ResponseEntity<UserResponse> response = userController.getUserById(userId);

        //assert
        assertTrue(response.hasBody());
        UserResponse returnedResponse = response.getBody();
        assertEquals(userResponse.getId(), returnedResponse.getId());
        assertEquals(userResponse.getRole(), returnedResponse.getRole());
        assertEquals(userResponse.getName(), returnedResponse.getName());
        assertEquals(userResponse.getUid(), returnedResponse.getUid());
        assertEquals(userResponse.getPhoneNumber(), returnedResponse.getPhoneNumber());
        assertEquals(userResponse.getStatus(), returnedResponse.getStatus());
        assertEquals(userResponse.getCreatedTime(), returnedResponse.getCreatedTime());
        assertEquals(userResponse.getUpdatedTime(), returnedResponse.getUpdatedTime());
        assertEquals(userResponse.getEmail(), returnedResponse.getEmail());
    }
    @Test
    public void getUserByIdTest_NonExistent() {
        //set up
        when(userService.getById(any(UUID.class))).thenReturn(null);

        //act
        ResponseEntity<UserResponse> response = userController.getUserById(userId);

        //assert
        assertFalse(response.hasBody());
    }
    @Test
    public void updateTest_Success() {
        //set up
        when(userService.updateUser(any(UserRequest.class))).thenReturn(userResponse);

        //act
        ResponseEntity<UserResponse> response = userController.update(userRequest ,userId);

        //assert
        assertTrue(response.hasBody());
        UserResponse returnedResponse = response.getBody();
        assertEquals(userResponse.getId(), returnedResponse.getId());
        assertEquals(userResponse.getRole(), returnedResponse.getRole());
        assertEquals(userResponse.getName(), returnedResponse.getName());
        assertEquals(userResponse.getUid(), returnedResponse.getUid());
        assertEquals(userResponse.getPhoneNumber(), returnedResponse.getPhoneNumber());
        assertEquals(userResponse.getStatus(), returnedResponse.getStatus());
        assertEquals(userResponse.getCreatedTime(), returnedResponse.getCreatedTime());
        assertEquals(userResponse.getUpdatedTime(), returnedResponse.getUpdatedTime());
        assertEquals(userResponse.getEmail(), returnedResponse.getEmail());

    }

    @Test
    public void updateTest_NonExistent() {
        //set up
        when(userService.updateUser(any(UserRequest.class))).thenReturn(null);

        //act
        ResponseEntity<UserResponse> response = userController.update(userRequest ,userId);

        //assert
        assertFalse(response.hasBody());
    }

    @Test
    public void deleteTest_NullId() {
        //set up
        doNothing().when(userService).deleteUser(any(UUID.class));

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> userController.deleteUser(null));
    }


}
