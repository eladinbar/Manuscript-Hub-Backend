package com.manuscript.core.domain.invitation_request.models;


import com.manuscript.core.domain.common.enums.InvitationEnum;
import com.manuscript.core.domain.user.models.UserModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.UUID;

@Data
@SuperBuilder
@Getter
@Setter
public class InvitationRequestModel {
    private UUID id;
    private UserModel user;
    private InvitationEnum invitationEnum;
    private Date createdTime;
    private Date updatedTime;
}
