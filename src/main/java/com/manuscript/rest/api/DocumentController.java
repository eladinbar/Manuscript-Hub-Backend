package com.manuscript.rest.api;

import com.manuscript.rest.response.DocumentResponse;
import com.manuscript.rest.service.IDocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.manuscript.rest.common.Constants.RESOURCE_DOCUMENT;


@RestController
@RequestMapping(RESOURCE_DOCUMENT)
@CrossOrigin("**")
public class DocumentController {
    private final IDocumentService documentService;


    public DocumentController(IDocumentService documentService) {
        this.documentService = documentService;

    }

    @RequestMapping(value = "/getVideoById/{id}", method = RequestMethod.GET)
    public ResponseEntity<DocumentResponse> getDocumentById(@PathVariable UUID id) {
        DocumentResponse result = documentService.get(id);
        return ResponseEntity.ok(result);
    }

}
