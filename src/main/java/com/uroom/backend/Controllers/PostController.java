package com.uroom.backend.Controllers;

import com.uroom.backend.Models.*;
import com.uroom.backend.POJOS.PostPOJO;
import com.uroom.backend.Services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @PostMapping(path = "add-post")
    public ResponseEntity<Object> addPost(@ModelAttribute PostPOJO requestPost) throws IOException {
        Post post = new Post();
        post.setDescription(requestPost.getDescription());
        post.setAddress(requestPost.getAddress());
        post.setPrice(requestPost.getPrice());
        post.setLatitude(requestPost.getLatitude());
        post.setLongitude(requestPost.getLongitude());
        post.setTitle(requestPost.getTitle());
        User user = this.userService.selectByEmail(requestPost.getUser()).iterator().next();
        if(user == null){
            System.out.println("User does not exist in the database."); // TODO: User does not exist in the database.
        }else{
            post.setUser(user);
        }
/*
        int random_int = (int)Math.floor(Math.random() * (1000+1));
        String prefix_img = "post_"+ random_int +"_image_";
        String main_img = this.azureStorageService.writeBlobFile(requestPost.getMain_img(),prefix_img + "0.jpg");
        post.setMain_img(main_img);
*/

        post.setMain_img("asdfasdf");
        post = this.postService.insert(post);

        if(post != null){
            /*
            if(requestPost.getImages() != null) {
                List<Image> images = new ArrayList<>();
                for(int i = 0; i < requestPost.getImages().size(); i++){ //Añadir imágenes a la base de datos
                    Image image = new Image();
                    String name_img = prefix_img + i + ".jpg";
                    image.setUrl(this.azureStorageService.writeBlobFile(requestPost.getImages().get(i + 1), name_img));
                    image.setPost(post); //Enlaza el post a la imagen
                    image = this.imageService.insert(image);
                    if (image != null) {
                        images.add(image);
                    }else{
                        return new ResponseEntity<>("Algo salio mal al agregar una de las imágenes, por favor intente nuevamente.", HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
                post.setImages(images);
            }
            */
            //Añadir servicios
            Set<String> serviceNames = requestPost.getServices();
            Set<Service> services = this.serviceService.selectBySetNames(serviceNames);
            post.setServices(services);

            //Añadir rules
            Set<String> ruleNames = requestPost.getRules();
            Set<Rule> rules = this.ruleService.selectBySetNames(ruleNames);
            post.setRules(rules);


            post = this.postService.update(post);
            if(post != null){
                return new ResponseEntity<>(post, HttpStatus.CREATED);
            }
            else{
                //TODO: Delete post
                return new ResponseEntity<>("(Services)Algo salio mal al agregar la nueva publicacion, por favor intente nuevamente.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else{
            return new ResponseEntity<>("Algo salio mal al agregar la nueva publicacion, por favor intente nuevamente.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //return new ResponseEntity<>("jaja no debi aser eso", HttpStatus.OK);
        //return new ResponseEntity<>("Algo salio mal al agregar la nueva publicacion, por favor intente nuevamente.", HttpStatus.INTERNAL_SERVER_ERROR);

    }

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
