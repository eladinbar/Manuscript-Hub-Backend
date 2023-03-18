package com.manuscript.infrastructure.persistence.sql.entities;

import com.manuscript.infrastructure.persistence.sql.common.entities.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Annotation")
@Data
@EqualsAndHashCode(callSuper = true)
//@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
public class AnnotationEntity extends BaseEntity {

}
