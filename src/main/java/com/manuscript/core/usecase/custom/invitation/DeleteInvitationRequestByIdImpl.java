package com.manuscript.core.usecase.custom.invitation;


import com.manuscript.core.domain.invitation_request.models.InvitationRequestModel;
import com.manuscript.core.domain.invitation_request.repository.IInvitationRequestRepositoryService;
import com.manuscript.core.usecase.common.DeleteByIdUseCaseImpl;

public class DeleteInvitationRequestByIdImpl extends DeleteByIdUseCaseImpl<InvitationRequestModel> implements IDeleteInvitationRequestUserById {

    public DeleteInvitationRequestByIdImpl(IInvitationRequestRepositoryService invitationRequestRepositoryService) {
        super(invitationRequestRepositoryService);
    }
}
