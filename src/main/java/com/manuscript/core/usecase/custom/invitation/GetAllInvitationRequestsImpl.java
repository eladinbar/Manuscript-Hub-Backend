package com.manuscript.core.usecase.custom.invitation;

import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.invitation_request.models.InvitationRequestModel;
import com.manuscript.core.usecase.common.GetAllUseCaseImpl;

public class GetAllInvitationRequestsImpl extends GetAllUseCaseImpl<InvitationRequestModel> implements IGetAllInvitationRequest {

    public GetAllInvitationRequestsImpl(IBaseRepositoryService<InvitationRequestModel> _serviceRepo) {
        super(_serviceRepo);
    }
}
