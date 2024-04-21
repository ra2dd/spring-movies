package com.example.movies.services;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service implements FileService {

    public static final String BUCKET_NAME = "movies-s3bucket";

    S3Template s3Template;

    public S3Service(S3Template s3Template) {
        this.s3Template = s3Template;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        // Prepare a Key
        var fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        var key = UUID.randomUUID().toString() + "." + fileExtension;

        var metadata = new ObjectMetadata.Builder()
                .contentType(file.getContentType())
                .contentLength(file.getSize())
                .build();

        String videoUrl;

        // Upload a file
        try {
            S3Resource s3Resource = s3Template.upload(
                    BUCKET_NAME,
                    key,
                    file.getInputStream()
                    );
            videoUrl = s3Resource.getURL().toString();

        } catch (IOException ioException) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while uploading a file.");
        }

        return videoUrl;
    }
}
