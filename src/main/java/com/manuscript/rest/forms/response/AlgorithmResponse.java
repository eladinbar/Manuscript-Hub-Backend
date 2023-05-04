package com.manuscript.rest.forms.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import org.jetbrains.annotations.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@SuperBuilder
public class AlgorithmResponse {
    @NotNull UUID id;
    @NotNull String uid;
    String url;
    @NotNull Date createdTime;
    @NotNull Date updatedTime;
}
