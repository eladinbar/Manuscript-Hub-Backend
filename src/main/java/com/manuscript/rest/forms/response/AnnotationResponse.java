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
public class AnnotationResponse {
    @NotNull UUID id;
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
