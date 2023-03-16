package com.manuscript.core.domain.image.models;

import com.manuscript.core.domain.common.enums.Status;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.UUID;

@Data
@SuperBuilder
@Getter
@Setter
public class ImageModel {
    private Date createdTime;
    private String uid;
    private Date updatedTime;
    private UUID id;
    private String fileName;
    private byte[] data;
    private Status status;

}
