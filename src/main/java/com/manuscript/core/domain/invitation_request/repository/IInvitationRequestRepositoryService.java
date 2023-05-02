package com.manuscript.core.domain.invitation_request.repository;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.invitation_request.models.InvitationRequestModel;
import com.manuscript.core.domain.user.models.UserModel;

import java.util.List;
import java.util.Optional;

public interface IInvitationRequestRepositoryService extends IBaseRepositoryService<InvitationRequestModel> {
    List<InvitationRequestModel> getAllInvitation();
    Optional<InvitationRequestModel> getByUid(String uid);

    Optional<InvitationRequestModel> getByEmail(String email);

}
