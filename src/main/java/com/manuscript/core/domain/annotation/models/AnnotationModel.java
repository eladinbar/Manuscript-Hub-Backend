package com.manuscript.core.domain.annotation.models;

import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.UUID;

@Data
@SuperBuilder
@Getter
public class AnnotationModel {
    UUID id;
    @NotNull String uid;
    @NotNull UUID imageId;
    @NotNull UUID algorithmId;
    String content;
    int startX;
    int startY;
    int endX;
    int endY;
    @NotNull Date createdTime;
    @NotNull Date updatedTime;
}
