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
    UUID id;
    String uid;
    String email;
    String name;
    Role role;
    String phoneNumber;
    boolean newUser;
}
