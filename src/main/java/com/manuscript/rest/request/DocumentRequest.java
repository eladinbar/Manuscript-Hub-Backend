package com.manuscript.rest.request;

import lombok.Value;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Value
@SuperBuilder
public class DocumentRequest {
    @NotNull UUID documentId;
    @NotNull String fileName;
    byte[] data;
}
