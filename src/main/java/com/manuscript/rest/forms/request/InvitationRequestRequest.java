package com.manuscript.rest.forms.request;

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
public class InvitationRequestRequest {
    UUID id;
    @NotNull String uid;
    @NotNull String email;
    @NotNull String name;
    @NotNull Role role;
    String phoneNumber;
}
