package org.example.gdfutureserver.image.service;


import org.springframework.stereotype.Service;

@Service
public class ImageQueryServiceImpl implements ImageQueryService {

    @Override
    public String getImageUrl(String fileName) {
        return "https://pub-a3ef7c0785614646b8c727fd367bb92d.r2.dev/" + fileName;
    }
}

