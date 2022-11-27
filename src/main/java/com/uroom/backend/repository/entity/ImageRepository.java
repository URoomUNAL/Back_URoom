package com.uroom.backend.repository.entity;

import com.uroom.backend.models.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {
    Image findByUrl(String url);
}
