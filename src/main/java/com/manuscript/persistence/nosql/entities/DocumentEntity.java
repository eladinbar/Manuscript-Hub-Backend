package com.manuscript.persistence.nosql.entities;

import com.manuscript.persistence.common.entities.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import java.util.UUID;


@Entity
@Document(collection = "documents")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
public class DocumentEntity extends BaseEntity {
    @Id
    private UUID id;
    private String fileName;
    private byte[] data;
}
