package com.manuscript.infrastructure.persistence.sql.entities;

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
    @JoinColumn(name = "userId", nullable = false, updatable = false)
    @org.hibernate.annotations.Type(type = "uuid-char")
    UserEntity user;

    @Column(name = "url", columnDefinition = "TEXT")
    String url;
}
