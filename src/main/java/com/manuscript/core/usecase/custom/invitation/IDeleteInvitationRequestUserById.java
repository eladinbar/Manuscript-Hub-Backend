package com.manuscript.core.usecase.custom.invitation;

import com.manuscript.core.domain.invitation_request.models.InvitationRequestModel;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.core.usecase.common.IDeleteByIdUseCase;

public interface IDeleteInvitationRequestUserById extends IDeleteByIdUseCase<InvitationRequestModel> {

}
