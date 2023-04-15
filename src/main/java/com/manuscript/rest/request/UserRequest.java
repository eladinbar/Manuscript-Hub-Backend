package com.manuscript.rest.request;

import com.manuscript.core.domain.common.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Data
@AllArgsConstructor
@SuperBuilder
public class UserRequest {
    @NotNull String email;
    @NotNull String name;
    @NotNull String uid;
    @NotNull Role role;
    @NotNull boolean newUser;
}
