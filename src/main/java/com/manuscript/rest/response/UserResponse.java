package com.manuscript.rest.response;

import lombok.Value;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.UUID;

@Value
@SuperBuilder
public class UserResponse {

    Date createdTime;
    Date updatedTime;
    Long userId;
    String email;
    String name;
    String phoneNumber;
    String uid;


}
