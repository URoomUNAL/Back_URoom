package com.uroom.backend.controllers;

import com.uroom.backend.models.entity.*;
import com.uroom.backend.models.requests.CalificationRequest;
import com.uroom.backend.models.requests.PostRequest;
import com.uroom.backend.models.responses.*;
import com.uroom.backend.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.*;

@RestController
public class PostController {

    private final PostService postService;
    private final ImageService imageService;
    private final StorageService storageService;
    private final RuleService ruleService;
    private final ServiceService serviceService;
    private final UserService userService;
    private final CalificationService calificationService;
    private final QuestionService questionService;
    private final VisitService visitService;
    private final InterestedService interestedService;
    private final RentService rentService;


    public PostController(PostService postService, ImageService imageService, StorageService storageService, RuleService ruleService, ServiceService serviceService, UserService userService, CalificationService calificationService, QuestionService questionService, VisitService visitService, InterestedService interestedService, RentService rentService){
        this.postService = postService;
        this.imageService = imageService;
        this.storageService = storageService;
        this.ruleService = ruleService;
        this.serviceService = serviceService;
        this.userService = userService;
        this.calificationService = calificationService;
        this.questionService = questionService;
        this.visitService = visitService;
        this.interestedService = interestedService;
        this.rentService = rentService;
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
    public List<PostResponse> getAll(){
        User user = getCurrentUser();
        List<Post> posts = postService.selectActivePosts();
        if(user == null) return post_to_postResponse(posts);
        List<PostResponse> postResponses = new ArrayList<>();
        for(Post post : posts){
            PostResponse aux = new PostResponse(post);
            aux.setIs_favorite(user.getFavorites().contains(post));
            postResponses.add(aux);
        }
        return postResponses;
    }

    @GetMapping(path="get-my-posts")
    public  ResponseEntity<Object> getMyPosts(){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<User> users = userService.selectByEmail(principal.getUsername());
        if(!principal.getUsername().equals(users.iterator().next().getEmail())){
            return new ResponseEntity<>("Usted no tiene permisos para ver las publicaciones de esta cuenta de usuario", HttpStatus.BAD_REQUEST);
        }
        if(users.size() == 0){
            return new ResponseEntity<>("Por favor regístrese para ver sus publicaciones.", HttpStatus.BAD_REQUEST);
        }
        else{
            List <Post> posts = postService.selectMyPosts(users.iterator().next());
            if(posts.size() == 0){
                return new ResponseEntity<>("Aún no tiene publicaciones.", HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(post_to_postResponse(posts), HttpStatus.OK);
            }
        }
    }

    @GetMapping(path="get-post")
    public ResponseEntity<Object> getPost(@RequestParam(name = "id") int post_id){
        Post post = postService.selectById(post_id);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(post == null || !post.isIs_active()){
            return new ResponseEntity<>("No se encontró el post", HttpStatus.BAD_REQUEST);
        }else{
            if(principal.toString().equals("anonymousUser")){
                addVisit(post);
            }else{
                UserDetails userActive = (UserDetails) principal;
                User user = this.userService.selectByEmail(userActive.getUsername()).iterator().next();
                if(user.getId()!=post.getUser().getId()){
                    addVisit(post);
                }
            }

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

    @PostMapping(path="change-active")
    public  ResponseEntity<Object> changeActive(@RequestParam(name = "id") int post_id){
        Post post = postService.selectById(post_id);
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(post == null){
            return new ResponseEntity<>("No existe la publicación.", HttpStatus.BAD_REQUEST);
        }
        if(!principal.getUsername().equals(post.getUser().getEmail())){
            return new ResponseEntity<>("Usted no tiene permisos para desactivar esta publicación", HttpStatus.BAD_REQUEST);
        }
        else{
            post.setIs_active(!post.isIs_active());
            postService.update(post);
            return new ResponseEntity<>("Actualización exitosa.", HttpStatus.OK);
        }
    }


    @GetMapping("get-posts-even-no-actives")
    public List<PostResponse> getAllEvenNoActives(){
        return post_to_postResponse(postService.select());
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

    @PostMapping(path = "add-post")
    public ResponseEntity<Object> addPost(@ModelAttribute PostRequest requestPost) throws IOException {
        Post post = new Post();
        post.setDescription(requestPost.getDescription());
        post.setAddress(requestPost.getAddress());
        post.setPrice(requestPost.getPrice());
        post.setLatitude(requestPost.getLatitude());
        post.setLongitude(requestPost.getLongitude());
        post.setTitle(requestPost.getTitle());
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userService.selectByEmail(principal.getUsername()).iterator().next();
        if(!principal.getUsername().equals(user.getEmail())){
            return new ResponseEntity<>("Usted no tiene permisos para registrar esta publicación", HttpStatus.BAD_REQUEST);
        }
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
            String main_img = this.storageService.writeBlobFile(requestPost.getMain_img(),prefix_img + "0." + extention[extention.length - 1]);
            post.setMain_img(main_img);

            //Añadir imagenes secundarias
            if(requestPost.getImages() != null && requestPost.getImages().size() > 0) {
                List<Image> images = new ArrayList<>();
                if(requestPost.getImages()!=null){
                    for(int i = 0; i < requestPost.getImages().size(); i++){ //Añadir imágenes a la base de datos
                        Image image = new Image();
                        int h = i + 1 ;
                        extention = Objects.requireNonNull(requestPost.getImages().get(i).getOriginalFilename()).split("\\.");
                        main_img = this.storageService.writeBlobFile(requestPost.getMain_img(),prefix_img + "0." + extention[extention.length - 1]);
                        String name_img = prefix_img + h + "." + extention;
                        image.setUrl(this.storageService.writeBlobFile(requestPost.getImages().get(i), name_img));
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
            if(!user.isIs_student()) return new ResponseEntity<>("El usuario es un propietario", HttpStatus.BAD_REQUEST);
            Post post = postService.selectById(id);
            user.getFavorites().add(post);
            userService.update(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("Usuario o Post no encontrado", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("remove-favorite")
    public ResponseEntity<Object> removeFavorite(@RequestParam int id){
        try{
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.selectByEmail(principal.getUsername()).iterator().next();
            if(!user.isIs_student()) return new ResponseEntity<>("El usuario es un propietario", HttpStatus.BAD_REQUEST);
            Post post = postService.selectById(id);
            user.getFavorites().remove(post);
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
            if(!user.isIs_student()) return new ResponseEntity<>("El usuario es un propietario", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(user.getFavorites(), HttpStatus.OK);

        }catch(Exception e){
            return new ResponseEntity<>("Usuario o Post no encontrado", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/contact-owner")
    public ResponseEntity<Object> contact(@RequestParam int PostId){
        try{
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(principal.toString()==""){
                return new ResponseEntity<>("El usuario no se encuentra autenticado", HttpStatus.BAD_REQUEST);
            }else{
                Post post = this.postService.selectById(PostId);
                User interestedUser = this.userService.selectByEmail(principal.getUsername()).iterator().next();
                Date date = new Date(System.currentTimeMillis());
                InterestedKey id = new InterestedKey(interestedUser.getId(),post.getId());
                Interested interested = new Interested(id,interestedUser,post,date);
                this.interestedService.insert(interested);
                post.getInterestedUsers().add(interested);
                this.postService.update(post);
                interestedUser.getInterestedPosts().add(interested);
                this.userService.update(interestedUser);
                return new ResponseEntity<>(post.getUser().getCellphone(), HttpStatus.OK);
            }
        }catch(Exception e){
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("get-interested-users")
    public ResponseEntity<Object> getInterestedUsers(@RequestParam(name = "post_id") int post_id){
        User authenticaded_user = getCurrentUser();
        if(authenticaded_user == null){
            return new ResponseEntity<>("El usuario no se encuentra autenticado", HttpStatus.BAD_REQUEST);
        }else{
            Post post = this.postService.selectById(post_id);
            List<Interested> interesteds = this.interestedService.selectByPost(post);
            List<UserInterestedResponse> interested_users = new ArrayList<>();
            for (Interested interested : interesteds){
                boolean add = interested_users.add(new UserInterestedResponse(interested.getUser()));
            }
            return new ResponseEntity<>(interested_users, HttpStatus.OK);
        }
    }
    /*
    @PostMapping("add-answer")
    public ResponseEntity<Object> addAnswer(@RequestParam int PostId, @RequestParam int QuestionIndex){

    }
    */

    @PostMapping("rate-post")
    public ResponseEntity<Object> ratePost(@RequestBody CalificationRequest calificationRequest){
        User authenticaded_user = getCurrentUser();
        if(authenticaded_user == null){
            return new ResponseEntity<>("El usuario no se encuentra autenticado", HttpStatus.BAD_REQUEST);
        }else{
            User user = this.userService.selectByEmail(calificationRequest.getUser_username()).iterator().next();
            if(user.getId()!=authenticaded_user.getId()){
                return new ResponseEntity<>("El usuario autenticado no corresponde con el calificador", HttpStatus.BAD_REQUEST);
            }
            Post post = postService.selectById(calificationRequest.getPost_id());
            Calification calification = new Calification();
            calification.setPost(post);
            calification.setUser(user);
            calification.setScore(calificationRequest.getScore());
            calification.setComment(calificationRequest.getComment());
            calification.setPros(calificationRequest.getPros());
            calification.setCons(calificationRequest.getCons());
            double score;
            if(post.getCalifications().size()==0){
                score = calification.getScore();
            }else{
                score = ((post.getScore() * post.getCalifications().size()) + calification.getScore())/(post.getCalifications().size() + 1);
            }
            post.setScore(score);
            post.getCalifications().add(calification);
            postService.update(post);
            return new ResponseEntity<>("El post fue calificado satisfactoriamente", HttpStatus.OK);
        }
    }

    public List<PostResponse> post_to_postResponse(List<Post> posts){
        List<PostResponse> postResponses = new ArrayList<>();
        for(Post post : posts){
            PostResponse postResp = new PostResponse(post);
            List<Rent> rented = this.rentService.selectByPostAndStatus(post, Rent.Status.RENT);
            postResp.setIs_rented(rented.size() > 0);
            if(rented.size() > 0){
                postResp.setVisits(0);
                postResp.setInterested(0);
            }else{
                LocalDate lastTimeRented = this.rentService.getLastRent(post);
                long DAY_IN_MS = 1000 * 60 * 60 * 24L;
                Date end = new Date();
                Date begin = new Date(end.getTime() - (30 * DAY_IN_MS));
                LocalDate localDate = begin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if(lastTimeRented.isBefore(localDate)){
                    int NumberVisits = this.visitService.NumberVisits(post,begin,end);
                    postResp.setVisits(NumberVisits);
                    int NumberInterested = this.interestedService.NumberInterested(post,begin,end);
                    postResp.setInterested(NumberInterested);
                }else{
                    begin = Date.from(lastTimeRented.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    int NumberVisits = this.visitService.NumberVisits(post,begin,end);
                    postResp.setVisits(NumberVisits);
                    int NumberInterested = this.interestedService.NumberInterested(post,begin,end);
                    postResp.setInterested(NumberInterested);
                }

            }
            postResponses.add(postResp);

        }
        return postResponses;
    }

    public User getCurrentUser(){
        try {
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userService.selectByEmail(principal.getUsername()).iterator().next();
        }catch(Exception e){
            return null;
        }
    }

    @PostMapping(path = "add-visit")
    public  ResponseEntity<Object> addVisit(@RequestParam int postId, @RequestParam int time){
        Post myPost = this.postService.selectById(postId);
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        Date date = new Date(System.currentTimeMillis() - (time * DAY_IN_MS));
        Visit visit = new Visit();
        visit.setPost(myPost);
        visit.setDate(date);
        this.visitService.insert(visit);
        return new ResponseEntity<>("Si se añadio xd", HttpStatus.OK);
    }

    public void addVisit(Post post){
        Date date = new Date(System.currentTimeMillis());
        Visit visit = new Visit();
        visit.setPost(post);
        visit.setDate(date);
        this.visitService.insert(visit);
    }
}
