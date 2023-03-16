package com.manuscript.infrastructure.persistence.nosql.documents;

import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.infrastructure.persistence.nosql.common.documents.BaseDocument;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "documents")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
public class ImageDocument extends BaseDocument {
    private String fileName;
    private Status status;
    private String uid;
    private String data;
}
