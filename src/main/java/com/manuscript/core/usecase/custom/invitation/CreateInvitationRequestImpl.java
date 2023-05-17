package com.manuscript.core.usecase.custom.invitation;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.invitation_request.models.InvitationRequestModel;
import com.manuscript.core.usecase.common.CreateUseCaseImpl;

public class CreateInvitationRequestImpl extends CreateUseCaseImpl<InvitationRequestModel> implements ICreateInvitationRequest {
    private final IBaseRepositoryService<InvitationRequestModel> _serviceRepo;

    public CreateInvitationRequestImpl(IBaseRepositoryService<InvitationRequestModel> _serviceRepo) {
        super(_serviceRepo);
        this._serviceRepo = _serviceRepo;
    }

    @Override
    public InvitationRequestModel create(InvitationRequestModel invitationRequestModel) throws IllegalArgumentException {
        return _serviceRepo.save(invitationRequestModel);
    }
}
