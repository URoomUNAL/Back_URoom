package com.uroom.backend.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;

@Service
public class StorageService {

    public String writeBlobFile(MultipartFile file, String filename) throws IOException {
        String link = Paths.get("src")
                .resolve("main")
                .resolve("resources")
                .resolve("images")
                .resolve(filename).toAbsolutePath().toString();
        File f = new File(link);
        try(OutputStream os = new FileOutputStream(f)){
            os.write(file.getBytes());
        }
        return link;
    }
}