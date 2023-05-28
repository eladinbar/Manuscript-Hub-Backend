package com.manuscript.rest.service;

import com.manuscript.core.domain.common.enums.InvitationEnum;
import com.manuscript.core.domain.invitation_request.models.InvitationRequestModel;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.core.exceptions.UserAlreadyExistException;
import com.manuscript.core.usecase.custom.invitation.*;
import com.manuscript.core.usecase.custom.user.ICreateUser;
import com.manuscript.core.usecase.custom.user.IDeleteUserById;
import com.manuscript.core.usecase.custom.user.IGetByEmailUser;
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
    private final IRestMapper<InvitationRequestModel, InvitationRequestResponse> invitationResponseMapper;
    private final IRestMapper<InvitationRequestModel, InvitationRequestRequest> invitationRequestMapper;
    private final IGetByUidInvitationRequest iGetByUidInvitationRequest;
    private final IGetAllInvitationRequests getAllInvitationRequest;
    private final IUpdateInvitationRequest updateInvitationRequest;
    private final IGetByEmailInvitationRequest getByEmailInvitationRequest;
    private final IGetByEmailUser getByEmailUser;
    private final ICreateInvitationRequest createInvitationRequest;
    private final ICreateUser createUser;
    private final IDeleteUserById deleteUserById;

    @Override
    public List<InvitationRequestResponse> getAllInvitations() {
        return getAllInvitationRequest.getAll().stream().map(invitationResponseMapper::modelToRest).collect(Collectors.toList());
    }

    @Override
    public List<InvitationRequestResponse> save(InvitationRequestRequest invitationRequestRequest) {
        Optional<InvitationRequestModel> userModelByUid = iGetByUidInvitationRequest.getByUid(invitationRequestRequest.getUid());
        if (userModelByUid.isPresent()) {
            throw new UserAlreadyExistException();
        }
        InvitationRequestModel invitationRequestModel =
                invitationRequestMapper.restToModel(invitationRequestRequest);
        invitationResponseMapper.modelToRest(createInvitationRequest.create(invitationRequestModel));
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
        Optional<InvitationRequestModel> emailOptional = getByEmailInvitationRequest.getByEmail(email);
        if (emailOptional.isPresent()) {
            InvitationRequestModel invitationRequestModel = emailOptional.get();
            if (invitationEnum.equals(InvitationEnum.Approved)) {
                UserModel userModel = invitationRequestModel.getUser();
                createUser.create(userModel);
            }else{
                Optional<UserModel> optByEmail = getByEmailUser.getByEmail(email);
                optByEmail.ifPresent(userModel -> deleteUserById.deleteById(userModel.getId()));
            }
            invitationRequestModel.setInvitationEnum(invitationEnum);
            updateInvitationRequest.update(invitationRequestModel);
        }
        return getAllInvitations();
        //TODO: need to handle it
    }
}
