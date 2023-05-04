package com.manuscript.rest.forms.response;

import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.domain.common.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ImageResponse {

    UUID imageId;
    @NotNull String userId;
    @NotNull String title;
    String author;
    Date publicationDate;
    String description;
    ArrayList<String> tags;
    ArrayList<String> sharedUserIds;
    @NotNull Status status;
    @NotNull Privacy privacy;
    Date createdTime;
    Date updatedTime;

}
