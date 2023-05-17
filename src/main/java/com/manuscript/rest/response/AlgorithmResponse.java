package com.manuscript.rest.response;

import com.manuscript.core.domain.common.enums.AlgorithmModelType;
import com.manuscript.core.domain.common.enums.AlgorithmStatus;
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
    @NotNull String title;
    @NotNull AlgorithmModelType modelType;
    @NotNull String description;
    @NotNull String url;
    @NotNull AlgorithmStatus status;
    @NotNull Date createdTime;
    @NotNull Date updatedTime;

}
