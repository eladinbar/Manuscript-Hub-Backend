package com.manuscript.infrastructure.persistence.nosql.service;

public interface IImageUtils {

    String encodeBase64String(byte[] binaryData);
    byte[] decodeBase64(String base64String);
}
