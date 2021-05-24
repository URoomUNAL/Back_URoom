package com.uroom.backend.Controllers;

import com.uroom.backend.Models.EntitiyModels.*;
import com.uroom.backend.Models.RequestModels.PostRequest;
import com.uroom.backend.Models.RequestModels.UserRequest;
import com.uroom.backend.Models.ResponseModels.CalificationResponse;
import com.uroom.backend.Models.ResponseModels.PostResponse;
import com.uroom.backend.Models.ResponseModels.QuestionResponse;
import com.uroom.backend.Services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.HTML;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
public class PostController {

    private final PostService postService;
    private final ImageService imageService;
    private final AzureStorageService azureStorageService;
    private final RuleService ruleService;
    private final ServiceService serviceService;
    private final UserService userService;
    private final CalificationService calificationService;
    private final QuestionService questionService;

    public PostController(PostService postService, ImageService imageService, AzureStorageService azureStorageService, RuleService ruleService ,ServiceService serviceService, UserService userService, CalificationService calificationService, QuestionService questionService){
        this.postService = postService;
        this.imageService = imageService;
        this.azureStorageService = azureStorageService;
        this.ruleService = ruleService;
        this.serviceService = serviceService;
        this.userService = userService;
        this.calificationService = calificationService;
        this.questionService = questionService;
    }


    @GetMapping("test-favorite")
    public void testFavorite(){
        Post myPost = this.postService.selectById(27);
        User user = this.userService.selectById(30).get(0);
        user.getFavorites().add(myPost);
        userService.update(user);
        User updatedUser = this.userService.selectById(30).get(0);
        System.out.println("Lista de favoritos vacía?: "+updatedUser.getFavorites().isEmpty());
        for(Post p : updatedUser.getFavorites()){
            System.out.println(p.getId());
        }
    }


    @GetMapping("get-posts")
    public List<Post> getAll(){
        return postService.selectActivePosts();
    }

    @PostMapping(path="get-my-posts", consumes = "application/json")
    public  ResponseEntity<Object> getMyPosts(@RequestBody UserRequest user_req){
        List<User> users = userService.selectById(user_req.getId());
        if(users.size() == 0){
            return new ResponseEntity<>("Por favor regístrese para ver sus publicaciones.", HttpStatus.BAD_REQUEST);
        }
        else{
            List <Post> posts = postService.selectMyPosts(users.iterator().next());
            if(posts.size() == 0){
                return new ResponseEntity<>("Aún no tiene publicaciones.", HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(posts, HttpStatus.OK);
            }
        }
    }

    @GetMapping(path="get-post")
    public ResponseEntity<Object> getPost(@RequestParam(name = "id") int post_id){
        Post post = postService.selectById(post_id);
        if(post == null || !post.isIs_active()){
            return new ResponseEntity<>("No se encontró el post", HttpStatus.BAD_REQUEST);
        }
        else{
            List<Calification> califications = calificationService.selectByPost(post);
            List<Question> questions = questionService.selectByPost(post);
            PostResponse postResponse = new PostResponse(post);

            List<CalificationResponse> calificationResponses = new ArrayList<>();
            for(Calification calification : califications){
                calificationResponses.add(new CalificationResponse(calification));
            }

            List<QuestionResponse> questionResponses = new ArrayList<>();
            for(Question question : questions){
                questionResponses.add(new QuestionResponse(question));
            }
            postResponse.setCalifications(calificationResponses);
            postResponse.setQuestions(questionResponses);
            return new ResponseEntity<>(postResponse, HttpStatus.OK);
        }
    }

    @PostMapping(path="change-active", consumes = "application/json")
    public  ResponseEntity<Object> getMyPosts(@RequestBody PostRequest post_req){
        List<Post> posts = postService.selectByAddress(post_req.getAddress());
        if(posts.size() == 0){
            return new ResponseEntity<>("No existe la publicación.", HttpStatus.BAD_REQUEST);
        }
        else{
            Post post = posts.iterator().next();
            post.setIs_active(post_req.isIs_active());
            postService.update(post);
            return new ResponseEntity<>("Actualización exitosa.", HttpStatus.OK);
        }
    }


    @GetMapping("get-posts-even-no-actives")
    public List<Post> getAllEvenNoActives(){
        return postService.select();
    }

    @GetMapping("get-images")
    public List<Image> getAllImages(){
        return imageService.select();
    }

    @GetMapping("test-calification")
    public Calification testCalification(){
        Calification myCalification = new Calification();
        myCalification.setComment("Hola soy un comentario, Buena muchacho");
        myCalification.setScore(4.5);
        Post post  = postService.selectById(122);
        User user = userService.selectById(147).iterator().next();
        System.out.println("Post: "+post.getDescription());
        System.out.println("Usuario: "+user.getName());
        myCalification.setPost(post);
        myCalification.setUser(user);
        Calification calification = calificationService.insert(myCalification);
        System.out.println(calification.getComment());
        return calification;
    }


    @GetMapping("test-question")
    public Question testQuestion(){
        Question myQuestion = new Question();
        myQuestion.setQuestion("Se permiten Uribistas?");
        myQuestion.setAnswer("Lamentablemente no");
        Post post = postService.selectById(122);
        System.out.println("Post: "+post.getDescription());
        User user = userService.selectById(147).iterator().next();
        System.out.println("Usuario: "+user.getName());
        myQuestion.setPost(post);
        myQuestion.setAnonymous(false);
        myQuestion.setUser(user);
        Question question = questionService.insert(myQuestion);
        return question;
    }

    @PostMapping(path = "add-post")
    public ResponseEntity<Object> addPost(@ModelAttribute PostRequest requestPost) throws IOException {
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
        post.setMain_img("buenaMuchacho");
        post = this.postService.insert(post);

        if(post != null){
            //Añadir imagen principal
            String prefix_img = "post_"+ post.getId() +"_image_";
            String[] extention = Objects.requireNonNull(requestPost.getMain_img().getOriginalFilename()).split("\\.");
            System.out.println("Nombre: " + extention[1]);
            String main_img = this.azureStorageService.writeBlobFile(requestPost.getMain_img(),prefix_img + "0." + extention[extention.length - 1]);
            post.setMain_img(main_img);

            //Añadir imagenes secundarias
            if(requestPost.getImages() != null && requestPost.getImages().size() > 0) {
                List<Image> images = new ArrayList<>();
                if(requestPost.getImages()!=null){
                    for(int i = 0; i < requestPost.getImages().size(); i++){ //Añadir imágenes a la base de datos
                        Image image = new Image();
                        int h = i + 1 ;
                        extention = Objects.requireNonNull(requestPost.getImages().get(i).getOriginalFilename()).split("\\.");
                        main_img = this.azureStorageService.writeBlobFile(requestPost.getMain_img(),prefix_img + "0." + extention[extention.length - 1]);
                        String name_img = prefix_img + h + "." + extention;
                        image.setUrl(this.azureStorageService.writeBlobFile(requestPost.getImages().get(i), name_img));
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
            }
            //Añadir servicios
            Set<String> serviceNames = requestPost.getServices();
            Set<Service> services = this.serviceService.selectBySetNames(serviceNames);
            post.setServices(services);

            //Añadir rules
            Set<String> ruleNames = requestPost.getRules();
            Set<Rule> rules = this.ruleService.selectBySetNames(ruleNames);
            post.setRules(rules);


            Post updated_post = this.postService.update(post);
            if(updated_post != null){
                return new ResponseEntity<>(updated_post, HttpStatus.CREATED);
            }
            else{
                postService.delete(post);
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

    @PostMapping("add-favorite")
    public ResponseEntity<Object> addFavorite(@RequestParam int id){
        try{
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.selectByEmail(principal.getUsername()).iterator().next();
            Post post = postService.selectById(id);
            user.getFavorites().add(post);
            userService.update(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("Usuario o Post no encontrado", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("get-favorites")
    public ResponseEntity<Object> getFavorites(){
        try{
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.selectByEmail(principal.getUsername()).iterator().next();
            return new ResponseEntity<>(user.getFavorites(), HttpStatus.OK);

        }catch(Exception e){
            return new ResponseEntity<>("Usuario o Post no encontrado", HttpStatus.BAD_REQUEST);
        }
    }
}
