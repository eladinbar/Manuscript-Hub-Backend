package com.manuscript.rest.service;


import com.manuscript.rest.forms.request.InvitationRequestRequest;
import com.manuscript.rest.forms.response.InvitationRequestResponse;

import java.util.List;

public interface IInvitationRequestService {
    List<InvitationRequestResponse> getAll();
    List<InvitationRequestResponse> save(InvitationRequestRequest invitationRequestRequest);
    List<InvitationRequestResponse> approve(String email);

    List<InvitationRequestResponse> deny(String email);
}


