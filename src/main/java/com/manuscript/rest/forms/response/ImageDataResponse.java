package com.manuscript.rest.forms.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Data
@AllArgsConstructor
@SuperBuilder
public class ImageDataResponse {
    UUID id;
    @NotNull UUID infoId;
    @NotNull String fileName;
    @NotNull byte[] data;
    @NotNull int index;
}
