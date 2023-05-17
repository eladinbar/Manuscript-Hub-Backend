package com.manuscript.core.usecase.custom.invitation;

import com.manuscript.core.domain.invitation_request.models.InvitationRequestModel;
import com.manuscript.core.domain.invitation_request.repository.IInvitationRequestRepositoryService;

import java.util.Optional;

public class GetByUidInvitationRequestImpl implements IGetByUidInvitationRequest {
    private final IInvitationRequestRepositoryService invitationRequestRepositoryService;

    public GetByUidInvitationRequestImpl(IInvitationRequestRepositoryService invitationRequestRepositoryService) {
        this.invitationRequestRepositoryService = invitationRequestRepositoryService;
    }

    @Override
    public Optional<InvitationRequestModel> getByUid(String uid) {
        Optional<InvitationRequestModel> invitationRequestModelOptional = invitationRequestRepositoryService.getByUid(uid);
        return invitationRequestModelOptional;
    }
}
