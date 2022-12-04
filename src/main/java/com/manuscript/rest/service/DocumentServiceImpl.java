package com.manuscript.rest.service;


import com.manuscript.core.domain.document.models.DocumentModel;
import com.manuscript.core.usecase.common.IGetByIdUseCase;
import com.manuscript.rest.mapping.IRestMapper;
import com.manuscript.rest.response.DocumentResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class DocumentServiceImpl implements IDocumentService {
    private final IRestMapper<DocumentModel, DocumentResponse> mapperResponseInfoModel;
    private final IGetByIdUseCase<DocumentModel> getByIdDocumentUseCase;

    @Override
    public DocumentResponse get(UUID id) {
        return mapperResponseInfoModel.modelToRest(getByIdDocumentUseCase.getById(id).get());
        //todo: need to change if represent
    }


}
