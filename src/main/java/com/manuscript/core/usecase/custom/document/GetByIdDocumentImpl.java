package com.manuscript.core.usecase.custom.document;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.document.models.DocumentModel;
import com.manuscript.core.usecase.common.GetByIdUseCaseImpl;

public class GetByIdDocumentImpl extends GetByIdUseCaseImpl<DocumentModel> implements IGetByIdDocument {

    public GetByIdDocumentImpl(IBaseRepositoryService<DocumentModel> _serviceRepo) {
        super(_serviceRepo);
    }
}
