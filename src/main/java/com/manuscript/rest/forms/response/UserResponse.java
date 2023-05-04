package com.manuscript.rest.forms.response;

import com.manuscript.core.domain.common.enums.Role;
import com.manuscript.core.domain.common.enums.Status;
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
public class UserResponse {

    UUID id;
    String uid;
    String email;
    String name;
    Role role;
    Status status;
    String phoneNumber;
    Date createdTime;
    Date updatedTime;
}
