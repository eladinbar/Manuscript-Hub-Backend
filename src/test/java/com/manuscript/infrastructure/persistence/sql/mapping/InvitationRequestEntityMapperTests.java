package com.manuscript.infrastructure.persistence.sql.mapping;

import com.manuscript.core.domain.common.enums.InvitationEnum;
import com.manuscript.core.domain.common.enums.Role;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.core.domain.invitation_request.models.InvitationRequestModel;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.infrastructure.persistence.sql.entities.InvitationRequestEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InvitationRequestEntityMapperTests {
    private final InvitationRequestEntityMapperImpl invitationRequestEntityMapper = new InvitationRequestEntityMapperImpl();
    private final UUID invitationRequestId = UUID.randomUUID();
    private final String email = "email@gmail.com";
    private String name = "name";
    private String phoneNumber = "234234";
    private String uid = "5555888";
    private Role role = Role.User;
    private Status status = Status.Enabled;
    private Date createdTime;
    private Date updatedTime;
    private InvitationEnum pendingInvitation = InvitationEnum.Pending;
    private UserModel userModel;
    private InvitationRequestModel invitationRequestModel;
    private InvitationRequestEntity invitationRequestEntity;

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
        // set up user model
        this.userModel = UserModel.builder()
                .id(null)
                .uid(uid).email(email).name(name).phoneNumber(phoneNumber)
                .role(role).status(status)
                .createdTime(this.createdTime).updatedTime(this.updatedTime).build();

        // set up invitation model
        this.invitationRequestModel = InvitationRequestModel.builder()
                .id(invitationRequestId)
                .user(userModel)
                .invitationEnum(pendingInvitation)
                .createdTime(this.createdTime).updatedTime(this.updatedTime).build();

        // set up invitation entity
        this.invitationRequestEntity = InvitationRequestEntity.builder()
                .id(invitationRequestId).uid(uid)
                .email(email).name(name).phoneNumber(phoneNumber)
                .status(status).role(role).invitationEnum(pendingInvitation)
                .createdTime(createdTime).updatedTime(updatedTime)
                .build();
    }

    ////--------------------------------------- 'modelToEntity' tests

    @Test
    public void modelToEntity_Success() {
        //act
        InvitationRequestEntity invitationRequestEntity = invitationRequestEntityMapper.modelToEntity(invitationRequestModel);

        //assert
        assertEquals(this.invitationRequestEntity.getId(), invitationRequestEntity.getId());
        assertEquals(this.invitationRequestEntity.getUid(), invitationRequestEntity.getUid());
        assertEquals(this.invitationRequestEntity.getEmail(), invitationRequestEntity.getEmail());
        assertEquals(this.invitationRequestEntity.getName(), invitationRequestEntity.getName());
        assertEquals(this.invitationRequestEntity.getPhoneNumber(), invitationRequestEntity.getPhoneNumber());
        assertEquals(this.invitationRequestEntity.getRole(), invitationRequestEntity.getRole());
        assertEquals(this.invitationRequestEntity.getStatus(), invitationRequestEntity.getStatus());
    }

    @Test
    public void modelToEntityNullPhoneNumber_Success() {
        //set up
        invitationRequestModel.getUser().setPhoneNumber(null);
        invitationRequestEntity.setPhoneNumber(null);

        //act
        InvitationRequestEntity invitationRequestEntity = invitationRequestEntityMapper.modelToEntity(invitationRequestModel);

        //assert
        assertEquals(this.invitationRequestEntity.getId(), invitationRequestEntity.getId());
        assertEquals(this.invitationRequestEntity.getUid(), invitationRequestEntity.getUid());
        assertEquals(this.invitationRequestEntity.getEmail(), invitationRequestEntity.getEmail());
        assertEquals(this.invitationRequestEntity.getName(), invitationRequestEntity.getName());
        assertEquals(this.invitationRequestEntity.getPhoneNumber(), invitationRequestEntity.getPhoneNumber());
        assertEquals(this.invitationRequestEntity.getRole(), invitationRequestEntity.getRole());
        assertEquals(this.invitationRequestEntity.getStatus(), invitationRequestEntity.getStatus());
    }

    ////--------------------------------------- 'entityToModel' tests

    @Test
    public void entityToModel_Success() {
        //act
        InvitationRequestModel invitationRequestModel = invitationRequestEntityMapper.entityToModel(this.invitationRequestEntity);

        //assert
        assertEquals(this.invitationRequestModel.getId(), invitationRequestModel.getId());
        assertEquals(this.invitationRequestModel.getUser().getUid(), invitationRequestModel.getUser().getUid());
        assertEquals(this.invitationRequestModel.getUser().getEmail(), invitationRequestModel.getUser().getEmail());
        assertEquals(this.invitationRequestModel.getUser().getName(), invitationRequestModel.getUser().getName());
        assertEquals(this.invitationRequestModel.getUser().getPhoneNumber(), invitationRequestModel.getUser().getPhoneNumber());
        assertEquals(this.invitationRequestModel.getUser().getRole(), invitationRequestModel.getUser().getRole());
        assertEquals(this.invitationRequestModel.getUser().getStatus(), invitationRequestModel.getUser().getStatus());
        assertEquals(this.invitationRequestModel.getInvitationEnum(), invitationRequestModel.getInvitationEnum());
    }

    @Test
    public void entityToModelNullPhoneNumber_Success() {
        //set up
        invitationRequestModel.getUser().setPhoneNumber(null);
        invitationRequestEntity.setPhoneNumber(null);

        //act
        InvitationRequestModel invitationRequestModel = invitationRequestEntityMapper.entityToModel(this.invitationRequestEntity);

        //assert
        assertEquals(this.invitationRequestModel.getId(), invitationRequestModel.getId());
        assertEquals(this.invitationRequestModel.getUser().getUid(), invitationRequestModel.getUser().getUid());
        assertEquals(this.invitationRequestModel.getUser().getEmail(), invitationRequestModel.getUser().getEmail());
        assertEquals(this.invitationRequestModel.getUser().getName(), invitationRequestModel.getUser().getName());
        assertEquals(this.invitationRequestModel.getUser().getPhoneNumber(), invitationRequestModel.getUser().getPhoneNumber());
        assertEquals(this.invitationRequestModel.getUser().getRole(), invitationRequestModel.getUser().getRole());
        assertEquals(this.invitationRequestModel.getUser().getStatus(), invitationRequestModel.getUser().getStatus());
        assertEquals(this.invitationRequestModel.getInvitationEnum(), invitationRequestModel.getInvitationEnum());
    }
}
