package com.manuscript.core.usecase.custom.document;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.document.models.DocumentModel;
import com.manuscript.core.usecase.common.UpdateUseCaseImpl;

public class UpdateDocumentImpl extends UpdateUseCaseImpl<DocumentModel> implements IUpdateDocument {

    public UpdateDocumentImpl(IBaseRepositoryService<DocumentModel> _serviceRepo) {
        super(_serviceRepo);
    }
}
