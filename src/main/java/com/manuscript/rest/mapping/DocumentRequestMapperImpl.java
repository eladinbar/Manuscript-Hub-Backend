package com.manuscript.rest.mapping;


import com.manuscript.core.domain.document.models.DocumentModel;
import com.manuscript.rest.request.DocumentRequest;
import org.springframework.stereotype.Service;

@Service
public class DocumentRequestMapperImpl implements IRestMapper<DocumentModel, DocumentRequest> {
    public DocumentRequest modelToRest(DocumentModel model) {
        return DocumentRequest.builder()
                .fileName(model.getFileName())
                .data(model.getData())
                .documentId(model.getId())
                .build();

    }

    public DocumentModel restToModel(DocumentRequest rest) {
        return DocumentModel.builder()
                .fileName(rest.getFileName())
                .data(rest.getData())
                .id(rest.getDocumentId())
                .build();
    }
}
