package com.uroom.backend.controllers;

import com.uroom.backend.models.entity.Post;
import com.uroom.backend.models.entity.Question;
import com.uroom.backend.models.entity.User;
import com.uroom.backend.models.pojos.QuestionNotification;
import com.uroom.backend.models.requests.QuestionRequest;
import com.uroom.backend.models.responses.QuestionResponse;
import com.uroom.backend.services.EmailService;
import com.uroom.backend.services.PostService;
import com.uroom.backend.services.QuestionService;
import com.uroom.backend.services.UserService;
import com.uroom.backend.auth.configuration.EmailConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RestController
public class QuestionController {
    private QuestionService questionService;
    private PostService postService;
    private UserService userService;
    private final EmailService emailService;

    public QuestionController(QuestionService questionService, PostService postService, UserService userService, EmailConfiguration emailConfiguration, EmailService emailService) {
        this.questionService = questionService;
        this.postService = postService;
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping("add-question")
    public ResponseEntity<Object> addQuestion(@RequestBody QuestionRequest questionRequest){
        try {
            User user = getCurrentUser();
            if(user == null) return new ResponseEntity<>("Debe registrarse para hacer una pregunta al arrendatario.", HttpStatus.INTERNAL_SERVER_ERROR);
            Post post = postService.selectById(questionRequest.getPost_id());
            Question newQuestion = new Question();
            newQuestion.setUser(user);
            newQuestion.setPost(post);
            newQuestion.setAnonymous(questionRequest.isAnonymous());
            newQuestion.setQuestion(questionRequest.getQuestion());
            Question addedQuestion = questionService.insert(newQuestion);
            try{
                emailService.send_question_email(post.getUser().getEmail(), user.getName(), addedQuestion.getQuestion(), post.getId());
            }catch(Exception e){
                new ResponseEntity<>("No pudimos enviar correo de notificación", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>(new QuestionResponse(addedQuestion), HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>("Algo salio mal al agregar la pregunta, por favor intente nuevamente.", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PostMapping("remove-question")
    public ResponseEntity<Object> removeQuestion(@RequestParam int question_id){
        try {
            User user = getCurrentUser();
            Question question = questionService.sectById(question_id).get(0);
            if (question == null)
                return new ResponseEntity<>("La pregunta no existe.", HttpStatus.INTERNAL_SERVER_ERROR);
            if (user.getId() != question.getUser().getId())
                return new ResponseEntity<>("No tiene permiso para borrar esa pregunta.", HttpStatus.INTERNAL_SERVER_ERROR);
            return questionService.delete(question) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>("Algo salio mal al eliminar la pregunta.", HttpStatus.INTERNAL_SERVER_ERROR);
        }catch(Exception e) {
            return new ResponseEntity<>("Algo salio mal al eliminar la pregunta.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("update-question")
    public ResponseEntity<Object> updateQuestion(@RequestBody QuestionRequest questionRequest){
        try {
            User user = getCurrentUser();
            if (user.getId() != questionRequest.getUser_id())
                return new ResponseEntity<>("No tiene permiso para editar la pregunta.", HttpStatus.INTERNAL_SERVER_ERROR);
            Question question = questionService.sectById(questionRequest.getId()).get(0);
            question.setAnonymous(questionRequest.isAnonymous());
            question.setQuestion(questionRequest.getQuestion());
            return questionService.update(question) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>("Algo salio mal al actualizar la pregunta.", HttpStatus.INTERNAL_SERVER_ERROR);
        }catch(Exception e){
            return new ResponseEntity<>("Algo salio mal al actualizar la pregunta.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("update-answer")
    public ResponseEntity<Object> updateAnswer(@RequestParam int questionId, @RequestParam String answer){
        try {
            User user = getCurrentUser();
            Question question = questionService.sectById(questionId).get(0);
            if (user.getId() != question.getPost().getUser().getId())
                return new ResponseEntity<>("No tiene permiso para actualizar la respuesta.", HttpStatus.INTERNAL_SERVER_ERROR);
            question.setAnswer(answer);
            return questionService.update(question) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>("Algo salio mal al actualizar la respuesta.", HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e){
            return new ResponseEntity<>("Algo salio mal al actualizar la respuesta.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("test-question")
    public ResponseEntity<Object> testQuestion(){
        try{
            Post myPost = postService.selectById(17);
            User myUser = userService.selectById(16).get(0);
            Question myQuestion = new Question();
            myQuestion.setQuestion("¿Y si los niños no son juiciosos? :c");
            myQuestion.setAnonymous(true);
            myQuestion.setPost(myPost);
            myQuestion.setUser(myUser);
            Question question = questionService.insert(myQuestion);
            System.out.println(question == null? "Insertado Bien :D": "GG no insertó");
            myQuestion.setAnonymous(false);
            myQuestion.setQuestion("¿Y si los niños no son juiciosos? F?");
            myQuestion.setAnswer("Si, F xd.");
            System.out.println(questionService.update(myQuestion) ? "Actualizado bien" : "GG no actualizó");
            System.out.println( questionService.delete(myQuestion) ? "Eliminado correctamente" : "GG no eliminó");
            return new ResponseEntity<Object>(new QuestionResponse(myQuestion),HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("Algo salio mal :(", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public User getCurrentUser(){
        try {
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userService.selectByEmail(principal.getUsername()).iterator().next();
        }catch(Exception e){
            return null;
        }
    }



    //public sendEmail(question)
    @PostMapping("/testNotification")
    public ResponseEntity<Object> questionNotificationTest(@RequestBody QuestionNotification questionNotification, BindingResult bindingResult) throws MessagingException, UnsupportedEncodingException {
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>("mal pa", HttpStatus.BAD_REQUEST);
        }
        emailService.send_question_email(questionNotification.getEmail(), questionNotification.getName(), questionNotification.getQuestion(), 17);

        return new ResponseEntity<>("buena muchacho", HttpStatus.OK);
    }
}
