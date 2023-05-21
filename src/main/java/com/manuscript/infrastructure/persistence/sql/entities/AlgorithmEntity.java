package com.manuscript.infrastructure.persistence.sql.entities;

import com.manuscript.core.domain.common.enums.AlgorithmModelType;
import com.manuscript.core.domain.common.enums.AlgorithmStatus;
import com.manuscript.infrastructure.persistence.sql.common.entities.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "Algorithm")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
public class AlgorithmEntity extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @org.hibernate.annotations.Type(type = "uuid-char")
    UserEntity user;

    @Column(name = "title", nullable = false, columnDefinition = "TEXT")
    String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "model_type", nullable = false)
    AlgorithmModelType modelType;

    @Column(name = "description", nullable = false)
    String description;

    @Column(name = "url", nullable = false, unique = true, columnDefinition = "TEXT")
    String url;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    AlgorithmStatus status;
}
