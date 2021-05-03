package com.uroom.backend.Services;

import com.uroom.backend.Models.EntitiyModels.Image;
import com.uroom.backend.Repository.ImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {
    final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository){this.imageRepository = imageRepository;}

    public List<Image> select(){return imageRepository.findAll();}

    public Image insert(Image image){
        try{
            return imageRepository.save(image);
        }catch (Exception e){
            return null;
        }
    }

    public Image selectByUrl(String url){
        return imageRepository.findByUrl(url);
    }
}
