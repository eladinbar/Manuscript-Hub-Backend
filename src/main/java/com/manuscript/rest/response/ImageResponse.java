package com.manuscript.rest.response;

import com.manuscript.core.domain.common.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ImageResponse {
    UUID documentId;
    UUID userId;
    String uid;
    String fileName;
    byte[] data;
    Status status;
    Date createdTime;
    Date updatedTime;
}
