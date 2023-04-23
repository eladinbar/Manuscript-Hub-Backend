package com.manuscript.rest.request;

import com.manuscript.core.domain.common.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserRequest {
    @NotNull String uid;
    @NotNull String email;
    @NotNull String name;
    @NotNull Role role;
    @NotNull String phoneNumber;
    @NotNull boolean newUser;
}
