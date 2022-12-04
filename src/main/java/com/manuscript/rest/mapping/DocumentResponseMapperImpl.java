package com.manuscript.rest.mapping;

import com.manuscript.core.domain.document.models.DocumentModel;
import com.manuscript.rest.response.DocumentResponse;
import org.springframework.stereotype.Service;

@Service
public class DocumentResponseMapperImpl implements IRestMapper<DocumentModel, DocumentResponse> {
    public DocumentResponse modelToRest(DocumentModel model) {
        return DocumentResponse.builder()
                .documentId(model.getId())
                .fileName(model.getFileName())
                .data(model.getData())
                .build();

    }

    public DocumentModel restToModel(DocumentResponse rest) {
        return DocumentModel.builder()
                .id(rest.getDocumentId())
                .createdTime(rest.getCreatedTime())
                .updatedTime(rest.getUpdatedTime())
                .fileName(rest.getFileName())
                .data(rest.getData())
                .build();
    }

}
