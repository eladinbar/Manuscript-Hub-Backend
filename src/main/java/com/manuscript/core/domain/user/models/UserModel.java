package com.manuscript.core.domain.user.models;

import com.manuscript.core.domain.common.enums.Role;
import com.manuscript.core.domain.common.enums.Status;
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
public class UserModel {

    private UUID id;
    private String uid;
    private String email;
    private String name;
    private Role role;
    private Status status;
    private String phoneNumber;
    private Date createdTime;
    private Date updatedTime;
}
