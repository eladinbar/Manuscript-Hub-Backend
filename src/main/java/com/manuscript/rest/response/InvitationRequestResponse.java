package com.manuscript.rest.response;

import com.manuscript.core.domain.common.enums.InvitationEnum;
import com.manuscript.core.domain.common.enums.Role;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.core.domain.user.models.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class InvitationRequestResponse {
    private String email;
    private String name;
    private Role role;
    InvitationEnum invitationEnum;
}
