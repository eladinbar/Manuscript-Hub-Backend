package com.manuscript.rest.mapping;

import com.manuscript.core.domain.common.enums.InvitationEnum;
import com.manuscript.core.domain.common.enums.Role;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.core.domain.invitation_request.models.InvitationRequestModel;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.rest.forms.request.InvitationRequestRequest;
import com.manuscript.rest.mapping.request.InvitationRequestRequestMapperImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InvitationRequestRequestMapperTests {
    private final InvitationRequestRequestMapperImpl invitationRequestRequestMapper = new InvitationRequestRequestMapperImpl();
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
    private InvitationRequestRequest invitationRequestRequest;
    private InvitationRequestModel invitationRequestModel;

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
        // set up invitation request
        this.invitationRequestRequest = InvitationRequestRequest.builder()
                .id(invitationRequestId)
                .uid(uid).email(email).name(name).role(role)
                .phoneNumber(phoneNumber).build();

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
    }

    ////--------------------------------------- 'modelToRest' tests

    @Test
    public void modelToRest_success() {
        //act
        InvitationRequestRequest invitationRequestRequest = invitationRequestRequestMapper.modelToRest(invitationRequestModel);

        //assert
        assertEquals(this.invitationRequestRequest, invitationRequestRequest);
    }

    ////--------------------------------------- 'restToModel' tests

    @Test
    public void restToModel_success() {
        //act
        InvitationRequestModel invitationRequestModel = invitationRequestRequestMapper.restToModel(invitationRequestRequest);

        //assert
        assertEquals(this.invitationRequestModel.getId(), invitationRequestModel.getId());
        assertEquals(this.invitationRequestModel.getUser().getUid(), invitationRequestModel.getUser().getUid());
        assertEquals(this.invitationRequestModel.getUser().getEmail(), invitationRequestModel.getUser().getEmail());
        assertEquals(this.invitationRequestModel.getUser().getName(), invitationRequestModel.getUser().getName());
        assertEquals(this.invitationRequestModel.getUser().getRole(), invitationRequestModel.getUser().getRole());
        assertEquals(this.invitationRequestModel.getUser().getStatus(), invitationRequestModel.getUser().getStatus());
        assertEquals(this.invitationRequestModel.getUser().getPhoneNumber(), invitationRequestModel.getUser().getPhoneNumber());
        assertEquals(this.invitationRequestModel.getInvitationEnum(), invitationRequestModel.getInvitationEnum());
    }
}
