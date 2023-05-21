package com.manuscript.rest.mapping;

import com.manuscript.core.domain.common.enums.Role;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.rest.mapping.request.UserRequestMapperImpl;
import com.manuscript.rest.forms.request.UserRequest;
import com.manuscript.rest.forms.response.UserResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRequestMapperTests {
    private final UserRequestMapperImpl userRequestMapper = new UserRequestMapperImpl();
    private final UUID userId = UUID.randomUUID();
    private final UUID invalidUserId = UUID.fromString("00000000-98d6-42c7-aca8-763bf4cbfd23");
    private final String email = "email@gmail.com";
    private final String invalidEmail = "emailgmail.com";
    private String name = "asd";
    private String phoneNumber = "234234";
    private String uid = "5555888";
    private Role role = Role.User;
    private Status status = Status.active;
    private Date createdTime;
    private Date updatedTime;
    private UserResponse userResponse;
    private UserRequest userRequest;
    private UserModel userModel;

    @BeforeAll
    public void setUp() {
        // set up time
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.APRIL);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        this.createdTime = cal.getTime();
        cal.set(Calendar.DAY_OF_MONTH, 2);
        this.updatedTime = cal.getTime();
    }

    @BeforeEach
    public void beforeEach() {
        // set up user request
        this.userRequest = UserRequest.builder()
                .role(Role.User)
                .email(email)
                .uid(uid)
                .name(name)
                .phoneNumber(phoneNumber)
                .newUser(false).build();

        // set up user model
        this.userModel = UserModel.builder()
                .id(userId)
                .role(Role.User)
                .createdTime(this.createdTime)
                .updatedTime(this.updatedTime)
                .email(email)
                .uid(uid)
                .name(name)
                .phoneNumber(phoneNumber)
                .status(status).build();

    }

    ////--------------------------------------- 'modelToRest' tests

    @Test
    public void modelToRest_Success() {
        //act
        UserRequest userRequest = userRequestMapper.modelToRest(userModel);

        //assert
        assertNotNull(userRequest);
        assertEquals(uid, userRequest.getUid());
        assertEquals(role, userRequest.getRole());
        assertEquals(name, userRequest.getName());
        assertEquals(email, userRequest.getEmail());
        assertEquals(phoneNumber, userRequest.getPhoneNumber());
        assertEquals(false, userRequest.isNewUser());
    }

    @Test
    public void modelToRestNullId_Success() {
        //set up
        userModel.setId(null);

        //act
        UserRequest userRequest = userRequestMapper.modelToRest(userModel);

        //assert
        assertNotNull(userRequest);
        assertEquals(uid, userRequest.getUid());
        assertEquals(role, userRequest.getRole());
        assertEquals(name, userRequest.getName());
        assertEquals(email, userRequest.getEmail());
        assertEquals(phoneNumber, userRequest.getPhoneNumber());
        assertEquals(false, userRequest.isNewUser());
    }


    ////--------------------------------------- 'restToModel' tests

    @Test
    public void restToModel_Success() {
        //act
        UserModel uModel = userRequestMapper.restToModel(userRequest);

        //assert
        assertNotNull(uModel);
        assertEquals(uid, uModel.getUid());
        assertEquals(role, uModel.getRole());
        assertEquals(status, uModel.getStatus());
        assertEquals(name, uModel.getName());
        assertEquals(email, uModel.getEmail());
        assertEquals(phoneNumber, uModel.getPhoneNumber());
        assertTrue(uModel.getCreatedTime().before(new Date())
                || uModel.getCreatedTime().equals(new Date()));
        assertTrue(uModel.getUpdatedTime().after(createdTime)
                || uModel.getUpdatedTime().equals(createdTime));
    }
}
