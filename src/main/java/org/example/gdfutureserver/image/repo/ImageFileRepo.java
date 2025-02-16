package org.example.gdfutureserver.image.repo;


import org.example.gdfutureserver.image.model.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageFileRepo extends JpaRepository<ImageFile, Long> {
}

