package com.manuscript.core.usecase.custom.document;

import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.document.models.DocumentModel;
import com.manuscript.core.usecase.common.GetAllUseCaseImpl;

public class GetAllDocumentImpl extends GetAllUseCaseImpl<DocumentModel> implements IGetAllDocument {

    public GetAllDocumentImpl(IBaseRepositoryService<DocumentModel> _serviceRepo) {
        super(_serviceRepo);
    }
}
