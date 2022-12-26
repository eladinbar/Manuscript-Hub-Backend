package com.manuscript.rest.response;

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
    Date updatedTime;
    UUID documentId;
    String fileName;
    byte[] data;
}
