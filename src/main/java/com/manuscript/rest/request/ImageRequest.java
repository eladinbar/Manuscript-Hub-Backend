package com.manuscript.rest.request;

import com.manuscript.core.domain.common.enums.Privacy;
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
    @NotNull String uid;
    @NotNull String fileName;
    Status status;
    Privacy privacy;
    byte[] data;
}
