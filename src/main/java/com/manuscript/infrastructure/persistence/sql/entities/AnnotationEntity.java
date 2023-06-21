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
    @JoinColumn(name = "user_id", nullable = false)
    @org.hibernate.annotations.Type(type = "uuid-char")
    UserEntity user;

    @Column(name = "image_data_id", nullable = false, updatable = false)
    @org.hibernate.annotations.Type(type = "uuid-char")
    UUID imageDataId;

    @ManyToOne()
    @JoinColumn(name = "algorithm_id")
    @org.hibernate.annotations.Type(type = "uuid-char")
    AlgorithmEntity algorithm;

    @Column(name = "content", columnDefinition = "TEXT")
    String content;

    @Column(name = "start_x", columnDefinition = "INT", nullable = false)
    int startX;

    @Column(name = "start_y", columnDefinition = "INT", nullable = false)
    int startY;

    @Column(name = "end_x", columnDefinition = "INT", nullable = false)
    int endX;

    @Column(name = "end_y", columnDefinition = "INT", nullable = false)
    int endY;
}
