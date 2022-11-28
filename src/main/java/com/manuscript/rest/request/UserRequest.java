package com.manuscript.rest.request;

import lombok.Value;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

@Value
@SuperBuilder
public class UserRequest {
    @NotNull Long userId;
    @NotNull String email;
    @NotNull String name;
    @NotNull String phoneNumber;
    @NotNull String uid;

}
