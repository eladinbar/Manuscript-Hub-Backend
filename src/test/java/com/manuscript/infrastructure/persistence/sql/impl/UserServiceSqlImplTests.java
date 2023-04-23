package com.manuscript.infrastructure.persistence.sql.impl;

import com.manuscript.core.domain.common.enums.Role;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.core.domain.user.repository.IUserRepositoryService;
import com.manuscript.infrastructure.persistence.sql.common.mapping.IRepositoryEntityMapper;
import com.manuscript.infrastructure.persistence.sql.entities.UserEntity;
import com.manuscript.infrastructure.persistence.sql.repositories.IUserRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceSqlImplTests {
    private final UUID NIL = UUID.fromString("00000000-0000-0000-0000-000000000000");
    private UserServiceSqlImpl repoService;
    private IUserRepositoryService userRepoService;
    private IUserRepo repo;
    private IRepositoryEntityMapper<UserModel, UserEntity> mapper;
    private final UUID id = UUID.randomUUID();
    private final UUID invalidId = NIL;
    private final String uid = "2UYxH92SpBQfkRgEeN75EBdvM9r1";

    private Date createdTime;
    private Date updatedTime;
    private final UUID userId = UUID.randomUUID();
    private final String email = "email@gmail.com";
    private final String name = "name";
    private final String phoneNumber = "0541234567";
    private final String status = "active";
    private final Role role = Role.User;
    private UserEntity user;
    private final String url = "url";
    private UserModel userModel;

    @BeforeAll
    @SuppressWarnings("unchecked")
    public void setUp() {
        // set up time
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        this.createdTime = cal.getTime();
        cal.set(Calendar.DAY_OF_MONTH, 2);
        this.updatedTime = cal.getTime();

        // set up mocked services
        userRepoService = Mockito.mock(IUserRepositoryService.class);
        repo = Mockito.mock(IUserRepo.class);
        mapper = (IRepositoryEntityMapper<UserModel, UserEntity>) Mockito.mock(IRepositoryEntityMapper.class);

        // set up controller
        repoService = new UserServiceSqlImpl(repo, mapper);
    }

    @BeforeEach
    public void beforeEach() {
        // set up user model
        this.userModel = UserModel.builder()
                .id(userId).uid(uid)
                .email(email).name(name).phoneNumber(phoneNumber)
                .status(status).role(role)
                .createdTime(createdTime).updatedTime(updatedTime)
                .build();

        // set up user entity
        this.user = UserEntity.builder()
                .id(userId).uid(uid)
                .email(email).name(name).phoneNumber(phoneNumber)
                .status(status).role(role)
                .createdTime(createdTime).updatedTime(updatedTime)
                .build();
    }

    ////--------------------------------------- 'save' tests

    @Test
    public void saveSuccess() {
        //set up
        UserEntity newUser = UserEntity.builder()
                .id(userId).uid(uid).email(email).name(name)
                .phoneNumber(phoneNumber).role(role).status(status)
                .createdTime(createdTime).updatedTime(updatedTime).build();

        Optional<UserModel> optUserModel = Optional.of(userModel);

        ////mock mappers, assisting services and repository
        when(mapper.modelToEntity(any(UserModel.class))).thenReturn(newUser);
        when(repo.save(any(UserEntity.class))).thenReturn(newUser);
        when(mapper.entityToModel(any(UserEntity.class))).thenReturn(userModel);

        //act
        UserModel userModel1 = repoService.save(this.userModel);

        //assert
        assertNotNull(userModel1);
        assertEquals(uid, userModel1.getUid());
        assertEquals(userId, userModel1.getId());
        assertEquals(name, userModel1.getName());
        assertEquals(email, userModel1.getEmail());
        assertEquals(phoneNumber, userModel1.getPhoneNumber());
        assertEquals(role, userModel1.getRole());
        assertEquals(status, userModel1.getStatus());
        assertEquals(createdTime, userModel1.getCreatedTime());
        assertEquals(updatedTime, userModel1.getUpdatedTime());
        assertTrue(userModel1.getCreatedTime().before(new Date())
                || userModel1.getCreatedTime().equals(new Date()));
        assertTrue(userModel1.getUpdatedTime().after(createdTime)
                || userModel1.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void getByUid_Success() {
        Optional<UserEntity> opt = Optional.of(this.user);
        when(repo.findByUid(any(String.class))).thenReturn(opt);
        when(mapper.entityToModel(any(UserEntity.class))).thenReturn(this.userModel);

        //act
        Optional<UserModel> us = repoService.getByUid(opt.get().getUid());
        UserModel user = us.get();

        //assert
        assertEquals(phoneNumber, user.getPhoneNumber());
        assertEquals(name, user.getName());
        assertEquals(status, user.getStatus());
        assertEquals(email, user.getEmail());
        assertEquals(role, user.getRole());
        assertEquals(createdTime, user.getCreatedTime());
        assertEquals(updatedTime, user.getUpdatedTime());
        assertEquals(uid, user.getUid());
    }
    @Test
    public void getByUid_NonExistent() {
        Optional<UserEntity> opt = Optional.of(this.user);
        when(repo.findByUid(any(String.class))).thenReturn(null);
        when(mapper.entityToModel(any(UserEntity.class))).thenReturn(this.userModel);

        //act
        assertThrows(NullPointerException.class, () -> repoService.getByUid(opt.get().getUid()));
    }
    @Test
    public void getByEmail_Success() {
        Optional<UserEntity> opt = Optional.of(this.user);
        when(repo.findByEmail(any(String.class))).thenReturn(opt);
        when(mapper.entityToModel(any(UserEntity.class))).thenReturn(this.userModel);

        //act
        Optional<UserModel> us = repoService.getByEmail(opt.get().getUid());
        UserModel user = us.get();

        //assert
        assertEquals(phoneNumber, user.getPhoneNumber());
        assertEquals(name, user.getName());
        assertEquals(status, user.getStatus());
        assertEquals(email, user.getEmail());
        assertEquals(role, user.getRole());
        assertEquals(createdTime, user.getCreatedTime());
        assertEquals(updatedTime, user.getUpdatedTime());
        assertEquals(uid, user.getUid());
    }

    @Test
    public void getByEmail_NonExistent() {
        Optional<UserEntity> opt = Optional.of(this.user);
        when(repo.findByEmail(any(String.class))).thenReturn(null);
        when(mapper.entityToModel(any(UserEntity.class))).thenReturn(this.userModel);

        //act
        assertThrows(NullPointerException.class, () -> repoService.getByEmail(opt.get().getUid()));
    }
}


