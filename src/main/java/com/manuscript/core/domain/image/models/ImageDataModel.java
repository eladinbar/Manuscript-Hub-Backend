package com.manuscript.core.domain.image.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Data
@SuperBuilder
@Getter
@Setter
public class ImageDataModel {
    private UUID id;
    @NotNull private UUID imageId;
    @NotNull private String fileName;
    @NotNull private byte[] data;
    @NotNull private int index;

}
