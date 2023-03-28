package com.manuscript.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import org.jetbrains.annotations.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AnnotationResponse {
    @NotNull UUID annotationId;
    @NotNull UUID userId;
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
