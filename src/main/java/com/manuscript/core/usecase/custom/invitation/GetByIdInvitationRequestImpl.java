package com.manuscript.core.usecase.custom.invitation;

import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.invitation_request.models.InvitationRequestModel;
import com.manuscript.core.usecase.common.GetByIdUseCaseImpl;

public class GetByIdInvitationRequestImpl extends GetByIdUseCaseImpl<InvitationRequestModel> implements IGetByIdInvitationRequest {
    public GetByIdInvitationRequestImpl(IBaseRepositoryService<InvitationRequestModel> _serviceRepo) {
        super(_serviceRepo);
    }
}
