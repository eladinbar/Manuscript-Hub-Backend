package com.manuscript.rest.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AnnotationRequest {
    UUID annotationId;
    UUID userId;
    UUID imageId;
    UUID algorithmId;
    String content;
    int startX;
    int startY;
    int endX;
    int endY;
}
