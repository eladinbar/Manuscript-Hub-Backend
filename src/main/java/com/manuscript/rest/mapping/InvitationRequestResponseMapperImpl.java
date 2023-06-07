package com.manuscript.rest.mapping;

import com.manuscript.core.domain.common.enums.InvitationEnum;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.core.domain.invitation_request.models.InvitationRequestModel;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.rest.forms.response.InvitationRequestResponse;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class InvitationRequestResponseMapperImpl implements IRestMapper<InvitationRequestModel, InvitationRequestResponse> {
    @Override
    public InvitationRequestResponse modelToRest(InvitationRequestModel model) {
        return InvitationRequestResponse.builder()
                .email(model.getUser().getEmail())
                .name(model.getUser().getName())
                .role(model.getUser().getRole())
                .invitationEnum(model.getInvitationEnum())
                .build();

    }

    @Override
    public InvitationRequestModel restToModel(InvitationRequestResponse rest) {
        return InvitationRequestModel.builder()
                .user(UserModel.builder()
                        .email(rest.getEmail())
                        .name(rest.getName())
                        .role(rest.getRole())
                        .status(Status.Enabled)
                        .createdTime(new Date())
                        .updatedTime(new Date())
                        .build())
                .invitationEnum(InvitationEnum.Pending)
                .createdTime(new Date())
                .updatedTime(new Date())
                .build();
    }
}
