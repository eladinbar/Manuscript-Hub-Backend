package com.manuscript.rest.forms.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Data
@AllArgsConstructor
@SuperBuilder
public class ImageDataRequest {
    UUID imageDataId;
    @NotNull UUID imageId;
    @NotNull String fileName;
    @NotNull byte[] data;
    @NotNull int index;
}
