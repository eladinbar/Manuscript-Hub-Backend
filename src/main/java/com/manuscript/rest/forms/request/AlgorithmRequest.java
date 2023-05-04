package com.manuscript.rest.forms.request;

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
    UUID id;
    String uid;
    UUID imageId;
    String url;
}
