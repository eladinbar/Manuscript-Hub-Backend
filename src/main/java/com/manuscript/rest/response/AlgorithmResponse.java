package com.manuscript.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import org.jetbrains.annotations.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@SuperBuilder
public class AlgorithmResponse {
    @NotNull UUID algorithmId;
    @NotNull String userId;
    String url;
    @NotNull Date createdTime;
    @NotNull Date updatedTime;
}
