package com.uroom.backend.Controllers;

import com.uroom.backend.Models.*;
import com.uroom.backend.POJOS.ImagePOJO;
import com.uroom.backend.POJOS.PostPOJO;
import com.uroom.backend.Services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PostController {
    private final PostService postService;
    private final ImageService imageService;
    private final AzureStorageService azureStorageService;
    private final RuleService ruleService;
    private final ServiceService serviceService;
    private final UserService userService;


    public PostController(PostService postService, ImageService imageService, AzureStorageService azureStorageService, RuleService ruleService ,ServiceService serviceService, UserService userService){
        this.postService = postService;
        this.imageService = imageService;
        this.azureStorageService = azureStorageService;
        this.ruleService = ruleService;
        this.serviceService = serviceService;
        this.userService = userService;
    }

    @GetMapping("get-posts")
    public List<Post> getAll(){
        return postService.select();
    }

    @GetMapping("get-images")
    public List<Image> getAllImages(){
        return imageService.select();
    }

    @PostMapping("/blob")
    public String writeBlobFile(@RequestParam("HOLA") MultipartFile file) throws IOException {
        String url = azureStorageService.writeBlobFile(file,"prueba");
        System.out.println(url);
        return url;
    }
    /*
    public String writeBlobFile(MultipartFile file, String filename) throws IOException {
        String url = azureStorageService.writeBlobFile(file,filename);
        System.out.println(url);
        return url;
    }*/
/*
    @PostMapping(path = "add-ppost")
    public ResponseEntity<Object> addPost(@ModelAttribute PostPOJO newPost) throws IOException {
        //System.out.println(newPost.getRules());

        Post myPost = new Post();
        myPost.setDescription(newPost.getDescription());
        myPost.setAddress(newPost.getAddress());
        myPost.setPrice(newPost.getPrice());
        myPost.setLatitude(newPost.getLatitude());
        myPost.setLongitude(newPost.getLongitude());
        myPost.setTitle(newPost.getTitle());
        String prefix_img = "post_"+myPost.getAddress()+"_image_";
        //String main_img = writeBlobFile(newPost.getMain_img(),prefix_img + "0");
        //myPost.setMain_img(main_img);
        myPost = postService.insert(myPost);
        if(myPost != null){
            //Añadir imágenes
            List<Image> images = new ArrayList<>();

            for(int i = 1; i < newPost.getImages().size(); ++i){ //Añadir imágenes a la base de datos
                Image image = new Image();
                String name_img = prefix_img + String.valueOf(i);
                image.setUrl(writeBlobFile(newPost.getImages().get(i-1),name_img));
                image.setPost(myPost); //Enlaza el post a la imagen
                image = imageService.insert(image);
                if(image != null){
                    images.add(image);
                }
                else{
                    return new ResponseEntity<>("Algo salio mal al agregar una de las imágenes, por favor intente nuevamente.", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            myPost.setImages(images);
            //Añadir servicios
            myPost.setServices(newPost.getServices());
            //Añadir rules
            myPost.setRules(newPost.getRules());
            //Añadir usuario
            User user = userService.selectByEmail(newPost.getUser()).iterator().next();
            myPost.setUser(user);

            myPost = postService.update(myPost);
            return new ResponseEntity<>(myPost, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("Algo salio mal al agregar la nueva publicacion, por favor intente nuevamente.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //return new ResponseEntity<>("jaja no debi aser eso", HttpStatus.OK);
        //return new ResponseEntity<>("Algo salio mal al agregar la nueva publicacion, por favor intente nuevamente.", HttpStatus.INTERNAL_SERVER_ERROR);

    }
    */
    @GetMapping(value = "get-services")
    public ResponseEntity<Object> getServices(){
        List<Service> services = serviceService.select();
        return new ResponseEntity<>(services, HttpStatus.OK);
    }

    @GetMapping(value = "get-rules")
    public ResponseEntity<Object> getRules(){
        List<Rule> rules = ruleService.select();
        return new ResponseEntity<>(rules, HttpStatus.OK);
    }

}
