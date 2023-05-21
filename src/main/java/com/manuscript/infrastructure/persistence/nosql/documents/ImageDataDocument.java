package com.manuscript.infrastructure.persistence.nosql.documents;

import com.manuscript.infrastructure.persistence.nosql.common.documents.BaseDocument;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;


@Document(collection = "documents")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
public class ImageDataDocument extends BaseDocument {
    private UUID imageId;
    private String fileName;
    private String data;
    private int index;
}
