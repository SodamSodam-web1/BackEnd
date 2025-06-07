package goormton.backend.sodamsodam.global.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import goormton.backend.sodamsodam.global.payload.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList(
            "image/jpeg",
            "image/jpg",
            "image/png",
            "image/heic",
            "image/gif"
    );

    public String uploadFile(MultipartFile file) {
        validateImageFormat(file);
        String fileName = createFileName(file.getOriginalFilename());
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        try {
            amazonS3Client.putObject(new PutObjectRequest(
                    bucket,
                    fileName,
                    file.getInputStream(),
                    metadata
            ));
        } catch (IOException e) {
            throw new RuntimeException(ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
        }

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void validateImageFormat(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_IMAGE_TYPES.contains(contentType.toLowerCase())) {
            throw new RuntimeException(ErrorCode.INVALID_IMAGE_FORMAT.getMessage());
        }
    }

    private String createFileName(String originalFileName) {
        try {
            return UUID.randomUUID().toString() + getFileExtension(originalFileName);
        } catch (StringIndexOutOfBoundsException e) {
            throw new RuntimeException(ErrorCode.INVALID_FILE_NAME.getMessage());
        }
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
} 