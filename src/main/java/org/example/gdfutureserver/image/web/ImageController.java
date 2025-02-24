package org.example.gdfutureserver.image.web;


import org.example.gdfutureserver.image.model.ImageFile;
import org.example.gdfutureserver.image.repo.ImageFileRepo;
import org.example.gdfutureserver.image.service.ImageCommandService;
import org.example.gdfutureserver.image.service.ImageQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/gdfuture/server/api/images")
public class ImageController {

    private final ImageCommandService imageCommandService;
    private final ImageQueryService imageQueryService;
    private final ImageFileRepo imageFileRepository;

    public ImageController(ImageCommandService imageCommandService,
                           ImageQueryService imageQueryService,
                           ImageFileRepo imageFileRepository) {
        this.imageCommandService = imageCommandService;
        this.imageQueryService = imageQueryService;
        this.imageFileRepository = imageFileRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<ImageFile> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            ImageFile imageFile = imageCommandService.uploadImage(file);
            return ResponseEntity.ok(imageFile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<String> getImageUrl(@PathVariable String fileName) {
        String url = imageQueryService.getImageUrl(fileName);
        return ResponseEntity.ok(url);
    }

    @DeleteMapping("/{fileName}")
    public ResponseEntity<String> deleteImage(@PathVariable String fileName) {
        ImageFile imageFile = imageFileRepository.findByFileName(fileName);
        if (imageFile == null) {
            return ResponseEntity.notFound().build();
        }
        imageCommandService.deleteImage(imageFile);
        return ResponseEntity.ok("Image deleted successfully");
    }

    @PutMapping("/{fileName}")
    public ResponseEntity<ImageFile> updateImage(@PathVariable String fileName,
                                                 @RequestParam("file") MultipartFile file) {
        ImageFile oldImage = imageFileRepository.findByFileName(fileName);
        if (oldImage == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            ImageFile updatedImage = imageCommandService.updateImage(oldImage, file);
            return ResponseEntity.ok(updatedImage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

