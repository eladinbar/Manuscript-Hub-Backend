package com.manuscript.rest.request;

import com.manuscript.core.domain.common.enums.Role;
import com.manuscript.core.domain.common.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class InvitationRequestRequest {
    UUID id;
    String uid;
    String email;
    String name;
    Role role;
    Status status;
    String phoneNumber;
}
