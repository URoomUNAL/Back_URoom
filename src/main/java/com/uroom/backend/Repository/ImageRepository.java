package com.uroom.backend.Repository;

import com.uroom.backend.Models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {
    Image findByUrl(String url);
}
