package com.manuscript.rest.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AlgorithmRequest {
    UUID algorithmId;
    String userId;
    UUID imageId;
    String url;
}
