package com.manuscript.rest.forms.request;

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
    UUID id;
    String uid;
    UUID imageDataId;
    UUID algorithmId;
    String content;
    int startX;
    int startY;
    int endX;
    int endY;
}
