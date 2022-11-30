package com.manuscript.rest.request;

import lombok.Value;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Value
@SuperBuilder
public class UserRequest {
    @NotNull UUID userId;
    @NotNull String email;
    @NotNull String name;
    @NotNull String phoneNumber;
    @NotNull String uid;
}
