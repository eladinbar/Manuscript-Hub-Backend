package com.manuscript.infrastructure.persistence.sql.mapping;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.core.domain.common.enums.Role;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.infrastructure.persistence.sql.entities.AlgorithmEntity;
import com.manuscript.infrastructure.persistence.sql.entities.AnnotationEntity;
import com.manuscript.infrastructure.persistence.sql.entities.UserEntity;
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
public class UserEntityMapperTests {
    private final UUID NIL = UUID.fromString("00000000-0000-0000-0000-000000000000");
    private final UserEntityMapperImpl userEntityMapper = new UserEntityMapperImpl();
    private final UUID userId = UUID.randomUUID();
    private final UUID invalidUserId = UUID.fromString("00000000-98d6-42c7-aca8-763bf4cbfd23");
    private final String email = "email@gmail.com";
    private final String invalidEmail = "emailgmail.com";
    private String name = "asd";
    private String phoneNumber = "234234";
    private Status status = Status.active;
    private String uid = "5555888";
    private Role role = Role.User;
    private Date createdTime;
    private Date updatedTime;
    private UserResponse userResponse;
    private UserRequest userRequest;
    private UserModel userModel;

    private UserEntity userEntity;
    private AlgorithmEntity algorithm;


    @BeforeAll
    public void setUp() {
        // set up time
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        this.createdTime = cal.getTime();
        cal.set(Calendar.DAY_OF_MONTH, 2);
        this.updatedTime = cal.getTime();
    }

    @BeforeEach
    public void beforeEach() {
        // set up user entity
        this.userEntity = UserEntity.builder()
                .id(userId).uid(uid)
                .email(email).name(name).phoneNumber(phoneNumber)
                .status(status).role(role)
                .createdTime(createdTime).updatedTime(updatedTime)
                .build();


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

    ////--------------------------------------- 'modelToEntity' tests

    @Test
    public void modelToEntity_Success() {
        //act
        UserEntity userEntity1 = userEntityMapper.modelToEntity(userModel);

        //assert
        assertNotNull(userEntity1);
        assertEquals(userId, userEntity1.getId());
        assertEquals(name, userEntity1.getName());
        assertEquals(email, userEntity1.getEmail());
        assertEquals(uid, userEntity1.getUid());
        assertEquals(status, userEntity1.getStatus());
        assertEquals(role, userEntity1.getRole());
        assertEquals(phoneNumber, userEntity1.getPhoneNumber());
        assertEquals(updatedTime, userEntity1.getUpdatedTime());
        assertEquals(createdTime, userEntity1.getCreatedTime());
        assertTrue(userModel.getCreatedTime().before(new Date())
                || userModel.getCreatedTime().equals(new Date()));
        assertTrue(userModel.getUpdatedTime().after(createdTime)
                || userModel.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void modelToEntityNullId_Success() {
        //set up
        userModel.setId(null);

        //act
        UserEntity userEntity1 = userEntityMapper.modelToEntity(userModel);
        //assert
        assertNotNull(userEntity1);
        assertNotEquals(userId, userEntity1.getId());

        assertEquals(name, userEntity1.getName());
        assertEquals(email, userEntity1.getEmail());
        assertEquals(uid, userEntity1.getUid());
        assertEquals(status, userEntity1.getStatus());
        assertEquals(role, userEntity1.getRole());
        assertEquals(phoneNumber, userEntity1.getPhoneNumber());
        assertEquals(updatedTime, userEntity1.getUpdatedTime());
        assertEquals(createdTime, userEntity1.getCreatedTime());
        assertTrue(userModel.getCreatedTime().before(new Date())
                || userModel.getCreatedTime().equals(new Date()));
        assertTrue(userModel.getUpdatedTime().after(createdTime)
                || userModel.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void modelToEntityEmptyUid_Success() {
        //set up
        userModel.setUid("");
        //act
        UserEntity userEntity1 = userEntityMapper.modelToEntity(userModel);
        //assert
        assertNotNull(userEntity1);
        assertNotEquals(uid, userEntity1.getUid());

        assertEquals(name, userEntity1.getName());
        assertEquals(email, userEntity1.getEmail());
        assertEquals(userId, userEntity1.getId());
        assertEquals(status, userEntity1.getStatus());
        assertEquals(role, userEntity1.getRole());
        assertEquals(phoneNumber, userEntity1.getPhoneNumber());
        assertEquals(updatedTime, userEntity1.getUpdatedTime());
        assertEquals(createdTime, userEntity1.getCreatedTime());
        assertTrue(userModel.getCreatedTime().before(new Date())
                || userModel.getCreatedTime().equals(new Date()));
        assertTrue(userModel.getUpdatedTime().after(createdTime)
                || userModel.getUpdatedTime().equals(createdTime));
    }

    ////--------------------------------------- 'entityToModel' tests

    @Test
    public void entityToModel_Success() {
        //act
        UserModel userModel1 = userEntityMapper.entityToModel(userEntity);

        //assert
        assertNotNull(userModel1);
        assertEquals(userId, userModel1.getId());
        assertEquals(name, userModel1.getName());
        assertEquals(email, userModel1.getEmail());
        assertEquals(uid, userModel1.getUid());
        assertEquals(status, userModel1.getStatus());
        assertEquals(role, userModel1.getRole());
        assertEquals(phoneNumber, userModel1.getPhoneNumber());
        assertEquals(updatedTime, userModel1.getUpdatedTime());
        assertEquals(createdTime, userModel1.getCreatedTime());
        assertTrue(userModel.getCreatedTime().before(new Date())
                || userModel.getCreatedTime().equals(new Date()));
        assertTrue(userModel.getUpdatedTime().after(createdTime)
                || userModel.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void entityToModelNullId_Success() {
        //set up
        userEntity.setId(null);

        //act
        UserModel userModel1 = userEntityMapper.entityToModel(userEntity);

        //assert
        assertNotNull(userModel1);
        assertNotEquals(userId, userModel1.getId());

        assertEquals(name, userModel1.getName());
        assertEquals(email, userModel1.getEmail());
        assertEquals(uid, userModel1.getUid());
        assertEquals(status, userModel1.getStatus());
        assertEquals(role, userModel1.getRole());
        assertEquals(phoneNumber, userModel1.getPhoneNumber());
        assertEquals(updatedTime, userModel1.getUpdatedTime());
        assertEquals(createdTime, userModel1.getCreatedTime());
        assertTrue(userModel.getCreatedTime().before(new Date())
                || userModel.getCreatedTime().equals(new Date()));
        assertTrue(userModel.getUpdatedTime().after(createdTime)
                || userModel.getUpdatedTime().equals(createdTime));

    }
}

