package com.manuscript.core.usecase.custom.document;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.document.models.DocumentModel;
import com.manuscript.core.usecase.common.CreateUseCaseImpl;

public class CreateDocumentImpl extends CreateUseCaseImpl<DocumentModel> implements ICreateDocument {

    public CreateDocumentImpl(IBaseRepositoryService<DocumentModel> _serviceRepo) {
        super(_serviceRepo);
    }
}
