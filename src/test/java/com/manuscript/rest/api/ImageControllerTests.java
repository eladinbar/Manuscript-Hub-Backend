package com.manuscript.rest.api;

import com.google.cloud.ByteArray;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.rest.request.ImageRequest;
import com.manuscript.rest.api.ImageController;
import com.manuscript.rest.response.AnnotationResponse;
import com.manuscript.rest.response.ImageResponse;
import com.manuscript.rest.service.IImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.stubbing.BaseStubbing;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ImageControllerTests {

    private IImageService imageService;
    private ImageController imageController;
    private ImageRequest imageRequest;
    private ImageResponse testImageResponse;
    private MockMultipartFile multipartFile;
    private String uid;

    @BeforeEach
    public void beforeEach() {
        //controller setup
        imageService = Mockito.mock(IImageService.class);
        imageController = new ImageController(imageService);

        //request setup
        UUID nilId = UUID.fromString("00000000-0000-0000-0000-000000000000");
        uid = "uid";
        String fileName = "fileName";
        Status status = Status.active;
        byte[] data = {0};
        imageRequest = new ImageRequest(nilId,uid,fileName,status,data);

        //multipart file setup
        multipartFile = new MockMultipartFile(fileName,data);

        //test response setup
        UUID imageId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Date createdTime = new Date();
        Date updatedTime = new Date();
        testImageResponse = new ImageResponse(imageId,userId,uid,fileName,data,status,createdTime,updatedTime);
    }

    @Test
    public void uploadDocumentSuccess() {
        when(imageService.save(any())).thenReturn(testImageResponse);
        try{
            ResponseEntity<ImageResponse> response = imageController.uploadDocument(multipartFile,uid);
            assertTrue(response.hasBody());
            ImageResponse imageResponse = response.getBody();
            assertNotNull(imageResponse);
        }
        catch (Exception e){
            fail("exception detected:" + e);
        }
    }

    @Test
    public void updateDocumentSuccess() {
        when(imageService.update(any())).thenReturn(testImageResponse);
        ResponseEntity<ImageResponse> response = imageController.updateDocument(imageRequest);
        assertTrue(response.hasBody());
        ImageResponse imageResponse = response.getBody();
        assertNotNull(imageResponse);
    }
}
