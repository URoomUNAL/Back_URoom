package com.uroom.backend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

@Service
public class AzureStorageService {

    @Autowired
    private ResourceLoader resourceLoader;

    public String readBlobFile(String filename) throws IOException {
        String link = "azure-blob://images/" + filename;
        Resource blobFile = resourceLoader.getResource(link);
        return StreamUtils.copyToString(
                blobFile.getInputStream(),
                Charset.defaultCharset());
    }

    public String writeBlobFile(MultipartFile file, String filename) throws IOException {
        String link = "azure-blob://images/" + filename;
        Resource blobFile = resourceLoader.getResource(link);
        try (OutputStream os = ((WritableResource) blobFile).getOutputStream()) {
            os.write(file.getBytes());
        }
        return "https://uroom20211.blob.core.windows.net/images/" + filename;
    }
}