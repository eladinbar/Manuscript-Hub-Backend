package com.manuscript.rest.service;


import com.manuscript.rest.forms.request.InvitationRequestRequest;
import com.manuscript.rest.forms.response.InvitationRequestResponse;

import java.util.List;

public interface IInvitationRequestService {
    List<InvitationRequestResponse> getAllInvitations();
    List<InvitationRequestResponse> save(InvitationRequestRequest invitationRequestRequest);
    List<InvitationRequestResponse> approveRequest(String email);

    List<InvitationRequestResponse> denyRequest(String email);
}


