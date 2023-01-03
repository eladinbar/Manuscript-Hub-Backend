package com.manuscript.persistence.nosql.service;

import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class ImageUtilsImpl implements IImageUtils {
    @Override
    public String encodeBase64String(byte[] binaryData) {
        return Base64.getEncoder().encodeToString(binaryData);
    }

    @Override
    public byte[] decodeBase64(String base64String) {
       return Base64.getDecoder().decode(base64String);
    }
}
