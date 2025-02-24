package org.example.gdfutureserver.image.service;

import jakarta.transaction.Transactional;
import org.example.gdfutureserver.cloud.model.CloudflareR2Properties;
import org.example.gdfutureserver.image.model.ImageFile;
import org.example.gdfutureserver.image.repo.ImageFileRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@Transactional
public class ImageCommandServiceImpl implements ImageCommandService {

    private final S3Client s3Client;
    private final CloudflareR2Properties properties;
    private final ImageFileRepo imageFileRepository;

    public ImageCommandServiceImpl(S3Client s3Client, CloudflareR2Properties properties, ImageFileRepo imageFileRepository) {
        this.s3Client = s3Client;
        this.properties = properties;
        this.imageFileRepository = imageFileRepository;
    }

    @Override
    public ImageFile uploadImage(MultipartFile multipartFile) throws Exception {
        File tempFile = File.createTempFile("upload-", multipartFile.getOriginalFilename());
        Files.copy(multipartFile.getInputStream(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        String fileName = UUID.randomUUID().toString() + "-" + multipartFile.getOriginalFilename();

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(properties.getBucket())
                        .key(fileName)
                        .build(),
                RequestBody.fromFile(tempFile)
        );
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8)
                .replace("+", "%20");

        String url = "https://pub-a3ef7c0785614646b8c727fd367bb92d.r2.dev/" + encodedFileName;

        ImageFile imageFile = ImageFile.builder()
                .fileName(encodedFileName)
                .url(url)
                .build();

        return imageFileRepository.save(imageFile);
    }

    @Override
    public void deleteImage(ImageFile imageFile) {
        s3Client.deleteObject(
                DeleteObjectRequest.builder()
                        .bucket(properties.getBucket())
                        .key(imageFile.getFileName())
                        .build()
        );
        imageFileRepository.delete(imageFile);
    }

    @Override
    public ImageFile updateImage(ImageFile oldImage, MultipartFile newMultipartFile) throws Exception {
        deleteImage(oldImage);
        return uploadImage(newMultipartFile);
    }
}
