package com.manuscript.rest.request;

import com.manuscript.core.domain.common.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Data
@AllArgsConstructor
@SuperBuilder
public class ImageRequest {
    UUID documentId;
    String uid;
    @NotNull String fileName;
    Status status;
    byte[] data;
}
