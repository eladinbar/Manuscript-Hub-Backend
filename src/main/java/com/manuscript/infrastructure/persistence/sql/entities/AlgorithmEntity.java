package com.manuscript.infrastructure.persistence.sql.entities;

import com.manuscript.infrastructure.persistence.sql.common.entities.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Algorithm")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
public class AlgorithmEntity extends BaseEntity {
//    @Id
//    @GeneratedValue(generator = "uuid2")
//    @GenericGenerator(name = "uuid2", strategy = "uuid2")
//    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
//    private UUID id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "userId", nullable = false, updatable = false, columnDefinition = "VARCHAR(36)")
    UserEntity user;
    @Column(name = "url", columnDefinition = "TEXT")
    String url;
    @Temporal(TemporalType.DATE)
    Date createdTime;
    @Temporal(TemporalType.DATE)
    Date updatedTime;
}
