package com.manuscript.rest.response;

import lombok.Value;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.UUID;

@Value
@SuperBuilder
public class DocumentResponse {

    Date createdTime;
    Date updatedTime;
    UUID documentId;
    String fileName;
    byte[] data;
}
