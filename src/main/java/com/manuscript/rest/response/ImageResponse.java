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

    Date createdTime;
    String uid;
    Date updatedTime;
    UUID documentId;
    String fileName;
    Status status;
    byte[] data;
}
