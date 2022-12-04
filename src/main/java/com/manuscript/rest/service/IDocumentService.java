package com.manuscript.rest.service;

import com.manuscript.rest.response.DocumentResponse;

import java.util.UUID;

public interface IDocumentService {
    DocumentResponse get(UUID id);

}


