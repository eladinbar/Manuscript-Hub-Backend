package com.manuscript.infrastructure.persistence.sql.impl;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.UUID;


@SpringBootTest
public class AnnotationServiceSqlTests {
    @Autowired
    private AnnotationServiceSqlImpl repoService;
    private AnnotationModel newAnnotation;

    @BeforeTestClass
    public void initialize() {
        newAnnotation = AnnotationModel.builder()
                .annotationId(UUID.randomUUID())
                .algorithmId(UUID.randomUUID())
                .userId(UUID.randomUUID())
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
