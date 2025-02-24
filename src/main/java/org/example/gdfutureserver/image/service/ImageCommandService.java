package org.example.gdfutureserver.image.service;

import org.example.gdfutureserver.image.model.ImageFile;
import org.springframework.web.multipart.MultipartFile;

public interface ImageCommandService {
    ImageFile uploadImage(MultipartFile multipartFile) throws Exception;
    void deleteImage(ImageFile imageFile);
    ImageFile updateImage(ImageFile oldImage, MultipartFile newMultipartFile) throws Exception;
}
