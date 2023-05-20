package com.manuscript.infrastructure.persistence.sql.entities;

import com.manuscript.infrastructure.persistence.sql.common.entities.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Annotation")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
public class AnnotationEntity extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @org.hibernate.annotations.Type(type = "uuid-char")
    UserEntity user;

    @Column(name = "image_data_id", nullable = false, updatable = false)
    @org.hibernate.annotations.Type(type = "uuid-char")
    UUID imageDataId;

    @ManyToOne()
    @JoinColumn(name = "algorithm_id", updatable = false)
    @org.hibernate.annotations.Type(type = "uuid-char")
    AlgorithmEntity algorithm;

    @Column(name = "content", columnDefinition = "TEXT")
    String content;

    @Column(name = "startX", columnDefinition = "INT")
    int startX;

    @Column(name = "startY", columnDefinition = "INT")
    int startY;

    @Column(name = "endX", columnDefinition = "INT")
    int endX;

    @Column(name = "endY", columnDefinition = "INT")
    int endY;
}
