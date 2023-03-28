package com.manuscript.infrastructure.persistence.sql.entities;

import com.manuscript.infrastructure.persistence.sql.common.entities.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.Date;
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
//    @Id
//    @GeneratedValue(generator = "uuid2")
//    @GenericGenerator(name = "uuid2", strategy = "uuid2")
//    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
//    private UUID id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "userId", nullable = false, updatable = false, columnDefinition = "VARCHAR(36)")
    UserEntity user;
    @Column(name = "imageId", nullable = false, updatable = false, columnDefinition = "VARCHAR(36)")
    UUID imageId;
    @ManyToOne(optional = false)
    @JoinColumn(name = "algorithmId", nullable = false, updatable = false, columnDefinition = "VARCHAR(36)")
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
    @Temporal(TemporalType.DATE)
    Date createdTime;
    @Temporal(TemporalType.DATE)
    Date updatedTime;
}
