package com.manuscript.rest.forms.response;

import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.domain.common.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ImageInfoResponse {
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
