package com.manuscript.rest.service;

import com.manuscript.core.domain.common.enums.InvitationEnum;
import com.manuscript.core.domain.common.enums.Role;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.core.domain.invitation_request.models.InvitationRequestModel;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.core.exceptions.UserAlreadyExistException;
import com.manuscript.core.usecase.custom.invitation.*;
import com.manuscript.core.usecase.custom.user.ICreateUser;
import com.manuscript.core.usecase.custom.user.IDeleteUserById;
import com.manuscript.core.usecase.custom.user.IGetByEmailUser;
import com.manuscript.core.usecase.custom.user.IUpdateUser;
import com.manuscript.rest.forms.request.InvitationRequestRequest;
import com.manuscript.rest.forms.response.InvitationRequestResponse;
import com.manuscript.rest.mapping.IRestMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InvitationRequestServiceImplTests {
    private InvitationRequestServiceImpl invitationRequestService;
    private IRestMapper<InvitationRequestModel, InvitationRequestRequest> invitationRequestMapper;
    private IRestMapper<InvitationRequestModel, InvitationRequestResponse> invitationResponseMapper;
    private ICreateInvitationRequest createInvitationRequestUseCase;
    private IUpdateInvitationRequest updateInvitationRequestUseCase;
    private IGetByUidInvitationRequest getByUidInvitationRequestUseCase;
    private IGetByEmailInvitationRequest getByEmailInvitationRequestUseCase;
    private IGetAllInvitationRequests getAllInvitationRequestUseCase;
    private ICreateUser createUserUseCase;
    private IUpdateUser updateUserUseCase;
    private IGetByEmailUser getByEmailUserUseCase;
    private IDeleteUserById deleteUserByIdUseCase;
    private final UUID invitationRequestId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();
    private final String email = "email@gmail.com";
    private String name = "asd";
    private String phoneNumber = "234234";
    private InvitationEnum pendingInvitation = InvitationEnum.Pending;
    private InvitationEnum approvedInvitation = InvitationEnum.Approved;
    private InvitationEnum deniedInvitation = InvitationEnum.Denied;
    private String uid = "";
    private Role role = Role.User;
    private InvitationRequestRequest invitationRequestRequest;
    private InvitationRequestResponse invitationRequestResponse;
    private InvitationRequestModel invitationRequestModel;
    private UserModel userModel;

    @BeforeAll
    @SuppressWarnings("unchecked")
    public void setUp() {
        // set up mocked services
        invitationRequestMapper = (IRestMapper<InvitationRequestModel, InvitationRequestRequest>) Mockito.mock(IRestMapper.class);
        invitationResponseMapper = (IRestMapper<InvitationRequestModel, InvitationRequestResponse>) Mockito.mock(IRestMapper.class);
        createInvitationRequestUseCase = Mockito.mock(ICreateInvitationRequest.class);
        updateInvitationRequestUseCase = Mockito.mock(IUpdateInvitationRequest.class);
        getByUidInvitationRequestUseCase = Mockito.mock(IGetByUidInvitationRequest.class);
        getByEmailInvitationRequestUseCase = Mockito.mock(IGetByEmailInvitationRequest.class);
        getAllInvitationRequestUseCase = Mockito.mock(IGetAllInvitationRequests.class);
        createUserUseCase = Mockito.mock(ICreateUser.class);
        updateUserUseCase = Mockito.mock(IUpdateUser.class);
        getByEmailUserUseCase = Mockito.mock(IGetByEmailUser.class);
        deleteUserByIdUseCase = Mockito.mock(IDeleteUserById.class);

        // set up service
        invitationRequestService = new InvitationRequestServiceImpl(invitationRequestMapper, invitationResponseMapper,
                createInvitationRequestUseCase, updateInvitationRequestUseCase, getByUidInvitationRequestUseCase,
                getByEmailInvitationRequestUseCase, getAllInvitationRequestUseCase, createUserUseCase, updateUserUseCase,
                getByEmailUserUseCase, deleteUserByIdUseCase);
    }

    @BeforeEach
    public void beforeEach() {
        // create invitation request
        this.invitationRequestRequest = InvitationRequestRequest.builder()
                .id(invitationRequestId)
                .uid(uid)
                .email(email)
                .name(name)
                .role(role)
                .phoneNumber(phoneNumber).build();

        // create invitation response
        this.invitationRequestResponse = InvitationRequestResponse.builder()
                .email(email)
                .name(name)
                .role(role)
                .invitationEnum(pendingInvitation).build();

        // create user model
        this.userModel = UserModel.builder()
                .id(userId)
                .uid(uid)
                .email(email)
                .name(name)
                .role(role)
                .phoneNumber(phoneNumber)
                .status(Status.Enabled)
                .createdTime(new Date())
                .updatedTime(new Date())
                .build();

        // create invitation model
        this.invitationRequestModel = InvitationRequestModel.builder()
                .id(invitationRequestId)
                .user(userModel)
                .invitationEnum(pendingInvitation)
                .createdTime(new Date())
                .updatedTime(new Date())
                .build();
    }

    ////--------------------------------------- 'save' tests

    @Test
    public void save_success() {
        //set up
        List<InvitationRequestModel> invitationRequestModels = new ArrayList<>();
        invitationRequestModels.add(invitationRequestModel);

        ////mock mappers and services
        Mockito.when(getByUidInvitationRequestUseCase.getByUid(any(String.class))).thenReturn(Optional.empty());
        Mockito.when(invitationRequestMapper.restToModel(any(InvitationRequestRequest.class))).thenReturn(invitationRequestModel);
        Mockito.when(createInvitationRequestUseCase.create(any(InvitationRequestModel.class))).thenReturn(invitationRequestModel);
        Mockito.when(getAllInvitationRequestUseCase.getAll()).thenReturn(invitationRequestModels);
        Mockito.when(invitationResponseMapper.modelToRest(invitationRequestModels.get(0))).thenReturn(invitationRequestResponse);

        //act
        List<InvitationRequestResponse> response = invitationRequestService.save(invitationRequestRequest);

        //assert
        assertFalse(response.isEmpty());
        assertEquals(response.get(0).getEmail(), invitationRequestRequest.getEmail());
        assertEquals(response.get(0).getName(), invitationRequestRequest.getName());
        assertEquals(response.get(0).getRole(), invitationRequestRequest.getRole());
        assertEquals(response.get(0).getInvitationEnum(), InvitationEnum.Pending);
    }

    @Test
    public void save_sameUser_fail() {
        //set up
        ////mock mappers and services
        Mockito.when(getByUidInvitationRequestUseCase.getByUid(any(String.class))).thenReturn(Optional.of(invitationRequestModel));

        //act
        //assert
        assertThrows(UserAlreadyExistException.class, () -> invitationRequestService.save(invitationRequestRequest));
    }

    ////--------------------------------------- 'approveRequest' tests

    @Test
    public void approveRequest_firstTime_success() {
        //set up
        invitationRequestModel.setInvitationEnum(InvitationEnum.Approved);
        invitationRequestResponse.setInvitationEnum(InvitationEnum.Approved);
        List<InvitationRequestModel> invitationRequestModels = new ArrayList<>();
        invitationRequestModels.add(invitationRequestModel);

        ////mock mappers and services
        Mockito.when(getByEmailInvitationRequestUseCase.getByEmail(any(String.class))).thenReturn(Optional.of(invitationRequestModel));
        Mockito.when(getByEmailUserUseCase.getByEmail(any(String.class))).thenReturn(Optional.empty());
        Mockito.when(createUserUseCase.create(any(UserModel.class))).thenReturn(userModel);
        Mockito.when(updateInvitationRequestUseCase.update(invitationRequestModels.get(0))).thenReturn(invitationRequestModel);
        Mockito.when(getAllInvitationRequestUseCase.getAll()).thenReturn(invitationRequestModels);
        Mockito.when(invitationResponseMapper.modelToRest(invitationRequestModel)).thenReturn(invitationRequestResponse);

        //act
        List<InvitationRequestResponse> response = invitationRequestService.approveRequest(invitationRequestRequest.getEmail());

        //assert
        assertFalse(response.isEmpty());
        assertEquals(response.get(0).getEmail(), invitationRequestRequest.getEmail());
        assertEquals(response.get(0).getName(), invitationRequestRequest.getName());
        assertEquals(response.get(0).getRole(), invitationRequestRequest.getRole());
        assertEquals(response.get(0).getInvitationEnum(), InvitationEnum.Approved);
    }

    @Test
    public void approveRequest_existingUser_success() {
        //set up
        invitationRequestModel.setInvitationEnum(InvitationEnum.Approved);
        invitationRequestResponse.setInvitationEnum(InvitationEnum.Approved);
        List<InvitationRequestModel> invitationRequestModels = new ArrayList<>();
        invitationRequestModels.add(invitationRequestModel);

        ////mock mappers and services
        Mockito.when(getByEmailInvitationRequestUseCase.getByEmail(any(String.class))).thenReturn(Optional.of(invitationRequestModel));
        Mockito.when(getByEmailUserUseCase.getByEmail(any(String.class))).thenReturn(Optional.of(userModel));
        Mockito.when(updateUserUseCase.update(any(UserModel.class))).thenReturn(userModel);
        Mockito.when(updateInvitationRequestUseCase.update(invitationRequestModels.get(0))).thenReturn(invitationRequestModel);
        Mockito.when(getAllInvitationRequestUseCase.getAll()).thenReturn(invitationRequestModels);
        Mockito.when(invitationResponseMapper.modelToRest(invitationRequestModel)).thenReturn(invitationRequestResponse);

        //act
        List<InvitationRequestResponse> response = invitationRequestService.approveRequest(invitationRequestRequest.getEmail());

        //assert
        assertFalse(response.isEmpty());
        assertEquals(response.get(0).getEmail(), invitationRequestRequest.getEmail());
        assertEquals(response.get(0).getName(), invitationRequestRequest.getName());
        assertEquals(response.get(0).getRole(), invitationRequestRequest.getRole());
        assertEquals(response.get(0).getInvitationEnum(), InvitationEnum.Approved);
    }

    @Test
    public void approveRequest_nonExistent_success() {
        //set up
        List<InvitationRequestModel> invitationRequestModels = new ArrayList<>();

        ////mock mappers and services
        Mockito.when(getByEmailInvitationRequestUseCase.getByEmail(any(String.class))).thenReturn(Optional.empty());
        Mockito.when(getAllInvitationRequestUseCase.getAll()).thenReturn(invitationRequestModels);
        Mockito.when(invitationResponseMapper.modelToRest(invitationRequestModel)).thenReturn(invitationRequestResponse);

        //act
        List<InvitationRequestResponse> response = invitationRequestService.approveRequest(invitationRequestRequest.getEmail());

        //assert
        assertTrue(response.isEmpty());
    }

    ////--------------------------------------- 'denyRequest' tests

    @Test
    public void denyRequest_firstTime_success() {
        //set up
        invitationRequestModel.setInvitationEnum(InvitationEnum.Denied);
        invitationRequestResponse.setInvitationEnum(InvitationEnum.Denied);
        List<InvitationRequestModel> invitationRequestModels = new ArrayList<>();
        invitationRequestModels.add(invitationRequestModel);

        ////mock mappers and services
        Mockito.when(getByEmailInvitationRequestUseCase.getByEmail(any(String.class))).thenReturn(Optional.of(invitationRequestModel));
        Mockito.when(getByEmailUserUseCase.getByEmail(any(String.class))).thenReturn(Optional.empty());
        Mockito.when(updateInvitationRequestUseCase.update(invitationRequestModels.get(0))).thenReturn(invitationRequestModel);
        Mockito.when(getAllInvitationRequestUseCase.getAll()).thenReturn(invitationRequestModels);
        Mockito.when(invitationResponseMapper.modelToRest(invitationRequestModel)).thenReturn(invitationRequestResponse);

        //act
        List<InvitationRequestResponse> response = invitationRequestService.denyRequest(invitationRequestRequest.getEmail());

        //assert
        assertFalse(response.isEmpty());
        assertEquals(response.get(0).getEmail(), invitationRequestRequest.getEmail());
        assertEquals(response.get(0).getName(), invitationRequestRequest.getName());
        assertEquals(response.get(0).getRole(), invitationRequestRequest.getRole());
        assertEquals(response.get(0).getInvitationEnum(), InvitationEnum.Denied);
    }

    @Test
    public void denyRequest_existingUser_success() {
        //set up
        invitationRequestModel.setInvitationEnum(InvitationEnum.Denied);
        invitationRequestResponse.setInvitationEnum(InvitationEnum.Denied);
        List<InvitationRequestModel> invitationRequestModels = new ArrayList<>();
        invitationRequestModels.add(invitationRequestModel);

        ////mock mappers and services
        Mockito.when(getByEmailInvitationRequestUseCase.getByEmail(any(String.class))).thenReturn(Optional.of(invitationRequestModel));
        Mockito.when(getByEmailUserUseCase.getByEmail(any(String.class))).thenReturn(Optional.of(userModel));
        Mockito.when(updateUserUseCase.update(any(UserModel.class))).thenReturn(userModel);
        Mockito.when(updateInvitationRequestUseCase.update(invitationRequestModels.get(0))).thenReturn(invitationRequestModel);
        Mockito.when(getAllInvitationRequestUseCase.getAll()).thenReturn(invitationRequestModels);
        Mockito.when(invitationResponseMapper.modelToRest(invitationRequestModel)).thenReturn(invitationRequestResponse);

        //act
        List<InvitationRequestResponse> response = invitationRequestService.denyRequest(invitationRequestRequest.getEmail());

        //assert
        assertFalse(response.isEmpty());
        assertEquals(response.get(0).getEmail(), invitationRequestRequest.getEmail());
        assertEquals(response.get(0).getName(), invitationRequestRequest.getName());
        assertEquals(response.get(0).getRole(), invitationRequestRequest.getRole());
        assertEquals(response.get(0).getInvitationEnum(), InvitationEnum.Denied);
    }

    @Test
    public void denyRequest_nonExistent_success() {
        //set up
        List<InvitationRequestModel> invitationRequestModels = new ArrayList<>();

        ////mock mappers and services
        Mockito.when(getByEmailInvitationRequestUseCase.getByEmail(any(String.class))).thenReturn(Optional.empty());
        Mockito.when(getAllInvitationRequestUseCase.getAll()).thenReturn(invitationRequestModels);
        Mockito.when(invitationResponseMapper.modelToRest(invitationRequestModel)).thenReturn(invitationRequestResponse);

        //act
        List<InvitationRequestResponse> response = invitationRequestService.denyRequest(invitationRequestRequest.getEmail());

        //assert
        assertTrue(response.isEmpty());
    }

    ////--------------------------------------- 'getAllInvitations' tests

    @Test
    public void getAllInvitations_success() {
        //set up
        List<InvitationRequestModel> invitationRequestModels = new ArrayList<>();

        ////mock mappers and services
        Mockito.when(getAllInvitationRequestUseCase.getAll()).thenReturn(invitationRequestModels);
        Mockito.when(invitationResponseMapper.modelToRest(invitationRequestModel)).thenReturn(invitationRequestResponse);

        //act
        List<InvitationRequestResponse> response = invitationRequestService.getAllInvitations();

        //assert
        assertTrue(response.isEmpty());
    }
}
