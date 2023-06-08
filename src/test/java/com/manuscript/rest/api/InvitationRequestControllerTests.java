package com.manuscript.rest.api;

import com.manuscript.core.domain.common.enums.InvitationEnum;
import com.manuscript.core.domain.common.enums.Role;
import com.manuscript.rest.forms.request.InvitationRequestRequest;
import com.manuscript.rest.forms.response.InvitationRequestResponse;
import com.manuscript.rest.service.IInvitationRequestService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InvitationRequestControllerTests {
    private IInvitationRequestService invitationRequestService;
    private InvitationRequestController invitationRequestController;
    private final UUID invitationRequestId = UUID.randomUUID();
    private final String email = "email@gmail.com";
    private final String emptyEmail = "";
    private String name = "asd";
    private String phoneNumber = "234234";
    private InvitationEnum pendingInvitation = InvitationEnum.Pending;
    private InvitationEnum approvedInvitation = InvitationEnum.Approved;
    private InvitationEnum deniedInvitation = InvitationEnum.Denied;
    private String uid = "";
    private Role role = Role.User;
    private InvitationRequestRequest invitationRequestRequest;
    private InvitationRequestResponse invitationRequestResponse;

    @BeforeAll
    public void setUp() {
        // set up controller and mocked service
        invitationRequestService = Mockito.mock(IInvitationRequestService.class);
        invitationRequestController = new InvitationRequestController(invitationRequestService);
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
    }

    ////--------------------------------------- 'createInvitation' tests

    @Test
    public void createInvitation_success() {
        //set up
        List<InvitationRequestResponse> invitationRequestResponses = new ArrayList<>();
        invitationRequestResponses.add(invitationRequestResponse);
        when(invitationRequestService.save(any(InvitationRequestRequest.class))).thenReturn(invitationRequestResponses);

        //act
        ResponseEntity<List<InvitationRequestResponse>> response = invitationRequestController.createInvitation(invitationRequestRequest);

        //assert
        assertTrue(response.hasBody());
        List<InvitationRequestResponse> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertFalse(responseBody.isEmpty());
        assertEquals(invitationRequestRequest.getEmail(), responseBody.get(0).getEmail());
        assertEquals(invitationRequestRequest.getName(), responseBody.get(0).getName());
        assertEquals(invitationRequestRequest.getRole(), responseBody.get(0).getRole());
        assertEquals(InvitationEnum.Pending, responseBody.get(0).getInvitationEnum());
    }

    ////--------------------------------------- 'approveInvitation' tests

    @Test
    public void approveInvitation_success() {
        //set up
        invitationRequestResponse.setInvitationEnum(InvitationEnum.Approved);
        List<InvitationRequestResponse> invitationRequestResponses = new ArrayList<>();
        invitationRequestResponses.add(invitationRequestResponse);
        when(invitationRequestService.approveRequest(any(String.class))).thenReturn(invitationRequestResponses);

        //act
        ResponseEntity<List<InvitationRequestResponse>> response = invitationRequestController.approveInvitationRequest(email);

        //assert
        assertTrue(response.hasBody());
        List<InvitationRequestResponse> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertFalse(responseBody.isEmpty());
        assertEquals(invitationRequestRequest.getEmail(), responseBody.get(0).getEmail());
        assertEquals(invitationRequestRequest.getName(), responseBody.get(0).getName());
        assertEquals(invitationRequestRequest.getRole(), responseBody.get(0).getRole());
        assertEquals(InvitationEnum.Approved, responseBody.get(0).getInvitationEnum());
    }

    @Test
    public void approveInvitation_emptyEmail_success() {
        //set up
        List<InvitationRequestResponse> invitationRequestResponses = new ArrayList<>();
        when(invitationRequestService.getAllInvitations()).thenReturn(invitationRequestResponses);

        //act
        ResponseEntity<List<InvitationRequestResponse>> response = invitationRequestController.approveInvitationRequest(emptyEmail);

        //assert
        assertTrue(response.hasBody());
        List<InvitationRequestResponse> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.isEmpty());
    }

    ////--------------------------------------- 'denyInvitation' tests

    @Test
    public void denyInvitation_success() {
        //set up
        invitationRequestResponse.setInvitationEnum(InvitationEnum.Denied);
        List<InvitationRequestResponse> invitationRequestResponses = new ArrayList<>();
        invitationRequestResponses.add(invitationRequestResponse);
        when(invitationRequestService.denyRequest(any(String.class))).thenReturn(invitationRequestResponses);

        //act
        ResponseEntity<List<InvitationRequestResponse>> response = invitationRequestController.denyInvitationRequest(email);

        //assert
        assertTrue(response.hasBody());
        List<InvitationRequestResponse> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertFalse(responseBody.isEmpty());
        assertEquals(invitationRequestRequest.getEmail(), responseBody.get(0).getEmail());
        assertEquals(invitationRequestRequest.getName(), responseBody.get(0).getName());
        assertEquals(invitationRequestRequest.getRole(), responseBody.get(0).getRole());
        assertEquals(InvitationEnum.Denied, responseBody.get(0).getInvitationEnum());
    }

    @Test
    public void denyInvitation_emptyEmail_success() {
        //set up
        List<InvitationRequestResponse> invitationRequestResponses = new ArrayList<>();
        when(invitationRequestService.getAllInvitations()).thenReturn(invitationRequestResponses);

        //act
        ResponseEntity<List<InvitationRequestResponse>> response = invitationRequestController.denyInvitationRequest(emptyEmail);

        //assert
        assertTrue(response.hasBody());
        List<InvitationRequestResponse> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.isEmpty());
    }

    ////--------------------------------------- 'getAllInvitations' tests

    @Test
    public void getAllInvitations_success() {
        //set up
        List<InvitationRequestResponse> invitationRequestResponses = new ArrayList<>();
        invitationRequestResponses.add(invitationRequestResponse);
        when(invitationRequestService.getAllInvitations()).thenReturn(invitationRequestResponses);

        //act
        ResponseEntity<List<InvitationRequestResponse>> response = invitationRequestController.getAllInvitations();

        //assert
        assertTrue(response.hasBody());
        List<InvitationRequestResponse> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertFalse(responseBody.isEmpty());
        assertEquals(invitationRequestRequest.getEmail(), responseBody.get(0).getEmail());
        assertEquals(invitationRequestRequest.getName(), responseBody.get(0).getName());
        assertEquals(invitationRequestRequest.getRole(), responseBody.get(0).getRole());
        assertEquals(InvitationEnum.Pending, responseBody.get(0).getInvitationEnum());
    }
}
