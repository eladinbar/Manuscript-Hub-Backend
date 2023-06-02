package com.manuscript.rest.service;

import com.manuscript.core.domain.common.enums.InvitationEnum;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.core.domain.invitation_request.models.InvitationRequestModel;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.core.exceptions.UserAlreadyExistException;
import com.manuscript.core.usecase.custom.invitation.*;
import com.manuscript.core.usecase.custom.user.ICreateUser;
import com.manuscript.core.usecase.custom.user.IDeleteUserById;
import com.manuscript.core.usecase.custom.user.IGetByEmailUser;
import com.manuscript.core.usecase.custom.user.IUpdateUser;
import com.manuscript.rest.mapping.IRestMapper;
import com.manuscript.rest.request.InvitationRequestRequest;
import com.manuscript.rest.response.InvitationRequestResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class InvitationRequestServiceImpl implements IInvitationRequestService {
    private final IRestMapper<InvitationRequestModel, InvitationRequestRequest> invitationRequestMapper;
    private final IRestMapper<InvitationRequestModel, InvitationRequestResponse> invitationResponseMapper;
    private final ICreateInvitationRequest createInvitationRequestUseCase;
    private final IUpdateInvitationRequest updateInvitationRequestUseCase;
    private final IGetByUidInvitationRequest getByUidInvitationRequestUseCase;
    private final IGetByEmailInvitationRequest getByEmailInvitationRequestUseCase;
    private final IGetAllInvitationRequests getAllInvitationRequestUseCase;
    private final ICreateUser createUserUseCase;
    private final IUpdateUser updateUserUseCase;
    private final IGetByEmailUser getByEmailUserUseCase;
    private final IDeleteUserById deleteUserByIdUseCase;

    @Override
    public List<InvitationRequestResponse> getAllInvitations() {
        return getAllInvitationRequestUseCase.getAll().stream().map(invitationResponseMapper::modelToRest).collect(Collectors.toList());
    }

    @Override
    public List<InvitationRequestResponse> save(InvitationRequestRequest invitationRequestRequest) {
        Optional<InvitationRequestModel> userModelByUid = getByUidInvitationRequestUseCase.getByUid(invitationRequestRequest.getUid());
        if (userModelByUid.isPresent()) {
            throw new UserAlreadyExistException();
        }
        InvitationRequestModel invitationRequestModel =
                invitationRequestMapper.restToModel(invitationRequestRequest);
        invitationResponseMapper.modelToRest(createInvitationRequestUseCase.create(invitationRequestModel));
        return getAllInvitations();
    }

    @Override
    public List<InvitationRequestResponse> approveRequest(String email) {
        return handleRequest(email, InvitationEnum.Approved);
    }

    @Override
    public List<InvitationRequestResponse> denyRequest(String email) {
        return handleRequest(email, InvitationEnum.Denied);
    }

    private List<InvitationRequestResponse> handleRequest(String email, InvitationEnum invitationEnum) {
        Optional<InvitationRequestModel> optionalInvitation = getByEmailInvitationRequestUseCase.getByEmail(email);
        if (optionalInvitation.isPresent()) {
            Optional<UserModel> optionalUser = getByEmailUserUseCase.getByEmail(email);
            UserModel userModel = null;
            InvitationRequestModel invitationRequestModel = optionalInvitation.get();
            if (invitationEnum.equals(InvitationEnum.Approved)) {
                // First time approval
                if(!optionalUser.isPresent()) {
                    userModel = invitationRequestModel.getUser();
                    createUserUseCase.create(userModel);
                } else {
                    userModel = optionalUser.get();
                    userModel.setStatus(Status.Enabled);
                    updateUserUseCase.update(userModel);
                }
            } else {
                // If user is being banned
                if (optionalUser.isPresent()) {
                    userModel = optionalUser.get();
                    userModel.setStatus(Status.Disabled);
                    updateUserUseCase.update(userModel);
                }
            }
            invitationRequestModel.setInvitationEnum(invitationEnum);
            updateInvitationRequestUseCase.update(invitationRequestModel);
        }
        return getAllInvitations();
        //TODO: need to handle it
    }
}
