package com.manuscript.infrastructure.persistence.sql.mapping;

import com.manuscript.core.domain.invitation_request.models.InvitationRequestModel;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.infrastructure.persistence.sql.common.mapping.IRepositoryEntityMapper;
import com.manuscript.infrastructure.persistence.sql.entities.InvitationRequestEntity;
import org.springframework.stereotype.Service;

@Service
public class InvitationRequestEntityMapperImpl implements IRepositoryEntityMapper<InvitationRequestModel, InvitationRequestEntity> {
    @Override
    public InvitationRequestEntity modelToEntity(InvitationRequestModel invitationRequestModel) {
        return InvitationRequestEntity.builder()
                .id(invitationRequestModel.getId())
                .uid(invitationRequestModel.getUser().getUid())
                .email(invitationRequestModel.getUser().getEmail())
                .name(invitationRequestModel.getUser().getName())
                .role(invitationRequestModel.getUser().getRole())
                .status(invitationRequestModel.getUser().getStatus())
                .phoneNumber(invitationRequestModel.getUser().getPhoneNumber())
                .invitationEnum(invitationRequestModel.getInvitationEnum())
                .createdTime(invitationRequestModel.getCreatedTime())
                .updatedTime(invitationRequestModel.getUpdatedTime())
                .build();
    }

    @Override
    public InvitationRequestModel entityToModel(final InvitationRequestEntity invitationRequestEntity) {
        return InvitationRequestModel.builder()
                .id(invitationRequestEntity.getId())
                .user(UserModel.builder()
                        .uid(invitationRequestEntity.getUid())
                        .email(invitationRequestEntity.getEmail())
                        .name(invitationRequestEntity.getName())
                        .role(invitationRequestEntity.getRole())
                        .status(invitationRequestEntity.getStatus())
                        .phoneNumber(invitationRequestEntity.getPhoneNumber()).build())
                .invitationEnum(invitationRequestEntity.getInvitationEnum())
                .createdTime(invitationRequestEntity.getCreatedTime())
                .updatedTime(invitationRequestEntity.getUpdatedTime())
                .build();
    }
}
