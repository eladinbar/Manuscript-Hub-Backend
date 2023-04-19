package com.manuscript.infrastructure.persistence.sql.impl;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.UUID;

public class AnnotationServiceSqlTests {
    private AnnotationServiceSqlImpl repoService;
    private AnnotationModel newAnnotation;

    @BeforeTestClass
    public void initialize() {
        newAnnotation = AnnotationModel.builder()
                .id(UUID.randomUUID())
                .algorithmId(UUID.randomUUID())
                .uid("uid")
                .imageId(UUID.randomUUID())
                .content("This is an annotation")
                .startX(0).startY(0)
                .endX(5).endY(5).build();
    }

    @Test
    public void saveSuccess() {
//        repoService.save(newAnnotation);
    }
}
