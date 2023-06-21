package com.manuscript.core.usecase.custom.invitation;

import com.manuscript.core.domain.invitation_request.models.InvitationRequestModel;
import com.manuscript.core.domain.invitation_request.repository.IInvitationRequestRepositoryService;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.core.domain.user.repository.IUserRepositoryService;

import java.util.Optional;

public class GetByEmailInvitationRequestImpl implements IGetByEmailInvitationRequest {
    private final IInvitationRequestRepositoryService invitationRequestRepositoryService;

    public GetByEmailInvitationRequestImpl(IInvitationRequestRepositoryService invitationRequestRepositoryService) {
        this.invitationRequestRepositoryService = invitationRequestRepositoryService;
    }


    @Override
    public Optional<InvitationRequestModel> getByEmail(String email) {
        return invitationRequestRepositoryService.getByEmail(email);
    }
}
