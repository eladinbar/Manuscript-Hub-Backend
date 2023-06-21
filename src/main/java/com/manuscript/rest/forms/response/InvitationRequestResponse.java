package com.manuscript.rest.forms.response;

import com.manuscript.core.domain.common.enums.InvitationEnum;
import com.manuscript.core.domain.common.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
