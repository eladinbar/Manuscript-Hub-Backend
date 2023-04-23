package com.manuscript.rest.service.mapping;

import com.manuscript.core.domain.common.enums.Role;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.rest.mapping.UserRequestMapperImpl;
import com.manuscript.rest.mapping.UserResponseMapperImpl;
import com.manuscript.rest.request.UserRequest;
import com.manuscript.rest.response.UserResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserResponseMapperTests {
    private final UserResponseMapperImpl userResponseMapper = new UserResponseMapperImpl();
    private final UUID userId = UUID.randomUUID();
    private final UUID invalidUserId = UUID.fromString("00000000-98d6-42c7-aca8-763bf4cbfd23");
    private final String email = "email@gmail.com";
    private final String invalidEmail = "emailgmail.com";
    private String name = "asd";
    private String phoneNumber = "234234";
    private String status = "active";
    private String uid = "5555888";
    private Role role = Role.User;
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
        // set up user response
        this.userResponse = UserResponse.builder()
                .userId(userId)
                .role(Role.User)
                .createdTime(this.createdTime)
                .updatedTime(this.updatedTime)
                .email(email)
                .uid(uid)
                .name(name)
                .phoneNumber(phoneNumber)
                .status(status).build();

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
        UserResponse uResponse = userResponseMapper.modelToRest(userModel);

        //assert
        assertNotNull(uResponse);
        assertEquals(userId, uResponse.getUserId());
        assertEquals(name, uResponse.getName());
        assertEquals(email, uResponse.getEmail());
        assertEquals(uid, uResponse.getUid());
        assertEquals(status, uResponse.getStatus());
        assertEquals(role, uResponse.getRole());
        assertEquals(phoneNumber, uResponse.getPhoneNumber());
        assertEquals(updatedTime, uResponse.getUpdatedTime());
        assertEquals(createdTime, uResponse.getCreatedTime());
        assertTrue(userModel.getCreatedTime().before(new Date())
                || userModel.getCreatedTime().equals(new Date()));
        assertTrue(userModel.getUpdatedTime().after(createdTime)
                || userModel.getUpdatedTime().equals(createdTime));

    }

    @Test
    public void modelToRestNullId_Success() {
        //set up
        userModel.setId(null);

        //act
        UserResponse userResponse1 = userResponseMapper.modelToRest(userModel);

        //assert
        assertNotNull(userResponse1);
        assertNotEquals(userId, userResponse1.getUserId());
        assertEquals(name, userResponse1.getName());
        assertEquals(email, userResponse1.getEmail());
        assertEquals(uid, userResponse1.getUid());
        assertEquals(status, userResponse1.getStatus());
        assertEquals(role, userResponse1.getRole());
        assertEquals(phoneNumber, userResponse1.getPhoneNumber());
        assertEquals(updatedTime, userResponse1.getUpdatedTime());
        assertEquals(createdTime, userResponse1.getCreatedTime());
        assertTrue(userModel.getCreatedTime().before(new Date())
                || userModel.getCreatedTime().equals(new Date()));
        assertTrue(userModel.getUpdatedTime().after(createdTime)
                || userModel.getUpdatedTime().equals(createdTime));
    }


    ////--------------------------------------- 'restToModel' tests

    @Test
    public void restToModel_Success() {
        //act
        UserModel uModel = userResponseMapper.restToModel(userResponse);

        //assert
        assertNotNull(uModel);
        assertEquals(userId, uModel.getId());
        assertEquals(name, uModel.getName());
        assertEquals(email, uModel.getEmail());
        assertEquals(uid, uModel.getUid());
        assertEquals(status, uModel.getStatus());
        assertEquals(role, uModel.getRole());
        assertEquals(phoneNumber, uModel.getPhoneNumber());
        assertEquals(updatedTime, uModel.getUpdatedTime());
        assertEquals(createdTime, uModel.getCreatedTime());
        assertTrue(uModel.getCreatedTime().before(new Date())
                || uModel.getCreatedTime().equals(new Date()));
        assertTrue(uModel.getUpdatedTime().after(createdTime)
                || uModel.getUpdatedTime().equals(createdTime));
    }
    @Test
    public void restToModelNullId_Success() {
        //set up
        userModel.setId(null);

        //act
        UserResponse userResponse1 = userResponseMapper.modelToRest(userModel);

        //assert
        assertNotNull(userResponse1);
        assertNotEquals(userId, userResponse1.getUserId());
        assertEquals(name, userResponse1.getName());
        assertEquals(email, userResponse1.getEmail());
        assertEquals(uid, userResponse1.getUid());
        assertEquals(status, userResponse1.getStatus());
        assertEquals(role, userResponse1.getRole());
        assertEquals(phoneNumber, userResponse1.getPhoneNumber());
        assertEquals(updatedTime, userResponse1.getUpdatedTime());
        assertEquals(createdTime, userResponse1.getCreatedTime());
        assertTrue(userModel.getCreatedTime().before(new Date())
                || userModel.getCreatedTime().equals(new Date()));
        assertTrue(userModel.getUpdatedTime().after(createdTime)
                || userModel.getUpdatedTime().equals(createdTime));
    }
}
