package com.manuscript.rest.forms.request;

import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.domain.common.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@SuperBuilder
public class ImageRequest {
    UUID id;
    @NotNull String uid;
    @NotNull String title;
    String author;
    Date publicationDate;
    String description;
    List<String> tags;
    List<String> sharedUserIds;
    @NotNull Status status;
    @NotNull Privacy privacy;
    Date createdTime;
    Date updatedTime;
}
