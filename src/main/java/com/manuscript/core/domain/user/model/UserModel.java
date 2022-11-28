package com.manuscript.core.domain.user.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@Getter
@Setter
public class UserModel {
    private Date createdTime;
    private Date updatedTime;
    private Long userId;
    private String email;
    private String name;
    private String phoneNumber;
    private String uid;
}
