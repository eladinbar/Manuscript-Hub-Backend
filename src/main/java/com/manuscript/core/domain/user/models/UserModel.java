package com.manuscript.core.domain.user.models;

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
    private Date createdTime;
    private Date updatedTime;
    private UUID id;
    private String email;
    private String name;
    private String phoneNumber;
    private String status;
    private String uid;

}
