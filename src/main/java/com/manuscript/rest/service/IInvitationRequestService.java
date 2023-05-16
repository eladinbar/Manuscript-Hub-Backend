package com.manuscript.rest.service;


import com.manuscript.rest.request.InvitationRequestRequest;
import com.manuscript.rest.response.InvitationRequestResponse;

import java.util.List;

public interface IInvitationRequestService {
    List<InvitationRequestResponse> getAllInvitations();
    List<InvitationRequestResponse> save(InvitationRequestRequest invitationRequestRequest);
    List<InvitationRequestResponse> acceptRequest(String email);

    List<InvitationRequestResponse> denyRequest(String email);
}


