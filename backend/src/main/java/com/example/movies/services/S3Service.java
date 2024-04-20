package com.example.movies.services;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Template;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

public class S3Service implements FileService {

    public static final String BUCKET_NAME = "movies-s3bucket";
    private final S3Template s3Template;
    private static final LocalDate startDate = LocalDate.of(2024, 1, 1);
    private static final LocalDate endDate = LocalDate.of(2024, 12, 31);

    public S3Service(S3Template s3Template) {
        this.s3Template = s3Template;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        // Prepare a Key
        var fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        var key = UUID.randomUUID().toString() + " " + fileExtension;

        var metadata = new ObjectMetadata.Builder()
                .contentType(file.getContentType())
                .contentLength(file.getSize())
                .build();

        try {
            s3Template.upload(
                    BUCKET_NAME,
                    key,
                    file.getInputStream(),
                    metadata
                    );
        } catch (IOException ioException) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while uploading a file.");
        }

        return s3Template.createSignedGetURL(
                BUCKET_NAME,
                key,
                Duration.between(startDate, endDate)
        ).toString();
    }
}
