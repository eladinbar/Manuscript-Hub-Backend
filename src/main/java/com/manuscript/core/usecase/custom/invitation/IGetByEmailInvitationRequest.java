package com.manuscript.core.usecase.custom.invitation;

import com.manuscript.core.domain.invitation_request.models.InvitationRequestModel;
import com.manuscript.core.domain.user.models.UserModel;

import java.util.Optional;

public interface IGetByEmailInvitationRequest {

    Optional<InvitationRequestModel> getByEmail(String email);
}
