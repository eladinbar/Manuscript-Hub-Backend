package com.manuscript.core.domain.image.models;

import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.domain.common.enums.Status;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@SuperBuilder
@Getter
@Setter
public class ImageModel {
    private UUID imageId;
    @NotNull private String userId;
    @NotNull private String title;
    private String author;
    private Date publicationDate;
    private String description;
    private ArrayList<String> tags;
    private ArrayList<String> sharedUserIds;
    @NotNull private Status status;
    @NotNull private Privacy privacy;
    @NotNull private Date createdTime;
    @NotNull private Date updatedTime;
}
