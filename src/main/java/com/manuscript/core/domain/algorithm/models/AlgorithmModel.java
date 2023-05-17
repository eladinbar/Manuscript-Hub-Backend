package com.manuscript.core.domain.algorithm.models;

import com.manuscript.core.domain.common.enums.AlgorithmModelType;
import com.manuscript.core.domain.common.enums.AlgorithmStatus;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.UUID;

@Data
@SuperBuilder
@Getter
public class AlgorithmModel {
    UUID id;
    @NotNull String uid;
    @NotNull String title;
    @NotNull AlgorithmModelType modelType;
    @NotNull String description;
    @NotNull String url;
    @NotNull AlgorithmStatus status;
    @NotNull Date createdTime;
    @NotNull Date updatedTime;
}
