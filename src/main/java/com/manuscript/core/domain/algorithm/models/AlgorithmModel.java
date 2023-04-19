package com.manuscript.core.domain.algorithm.models;

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
    UUID algorithmId;
    @NotNull String uid;
    String url;
    @NotNull Date createdTime;
    @NotNull Date updatedTime;
}
