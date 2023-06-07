package com.manuscript.rest.mapping;


import com.manuscript.core.domain.common.enums.InvitationEnum;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.core.domain.invitation_request.models.InvitationRequestModel;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.rest.forms.request.InvitationRequestRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class InvitationRequestRequestMapperImpl implements IRestMapper<InvitationRequestModel, InvitationRequestRequest> {
    @Override
    public InvitationRequestRequest modelToRest(InvitationRequestModel model) {
        return InvitationRequestRequest.builder()
                .uid(model.getUser().getUid())
                .email(model.getUser().getEmail())
                .name(model.getUser().getName())
                .role(model.getUser().getRole())
                .phoneNumber(model.getUser().getPhoneNumber())
                .build();

    }

    @Override
    public InvitationRequestModel restToModel(InvitationRequestRequest rest) {
        return InvitationRequestModel.builder()
                .id(rest.getId())
                .user(UserModel.builder().
                        uid(rest.getUid())
                        .email(rest.getEmail())
                        .name(rest.getName())
                        .role(rest.getRole())
                        .status(Status.Enabled)
                        .phoneNumber(rest.getPhoneNumber())
                        .createdTime(new Date())
                        .updatedTime(new Date()).build())
                .createdTime(new Date())
                .updatedTime(new Date())
                .invitationEnum(InvitationEnum.Pending)
                .build();
    }
}
