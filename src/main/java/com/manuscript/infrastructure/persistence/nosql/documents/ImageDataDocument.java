package com.manuscript.infrastructure.persistence.nosql.documents;

import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.infrastructure.persistence.nosql.common.documents.BaseDocument;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
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
    @NotNull private UUID imageId;
    @NotNull private String fileName;
    @NotNull private String data;
    @NotNull private int index;
}
