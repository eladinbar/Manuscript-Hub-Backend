package com.manuscript.core.usecase.custom.invitation;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.invitation_request.models.InvitationRequestModel;
import com.manuscript.core.usecase.common.UpdateUseCaseImpl;

public class UpdateInvitationRequestImpl extends UpdateUseCaseImpl<InvitationRequestModel> implements IUpdateInvitationRequest {

    public UpdateInvitationRequestImpl(IBaseRepositoryService<InvitationRequestModel> _serviceRepo) {
        super(_serviceRepo);
    }

}
