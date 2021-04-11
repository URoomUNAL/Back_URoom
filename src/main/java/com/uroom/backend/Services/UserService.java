package com.uroom.backend.Services;

import com.uroom.backend.Models.User;
import com.uroom.backend.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public boolean iterableEmpty(Iterable<User> query){
        List<User> listOfIterable = new ArrayList<User>();
        query.forEach(listOfIterable::add);
        System.out.println(listOfIterable);
        return listOfIterable.size() == 0;
    }
    public List<User> select(){//Hace un select a la base de datos, en este caso los devuelve todos
        return userRepository.findAll();
    }
    public List<User> selectByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public List<User> selectByCellphone(String key_cellphone){//Busca en los Usuarios por número celular
        return userRepository.findByCellphone(key_cellphone);
    }

    public boolean insert(User user){//Inserta un Usuario en la base de datos
        try{
            List<User> query = selectByCellphone(user.getCellphone());

            if(query.size() == 0){// Es un nuevo usuario
                user.setIs_active(true);
                userRepository.save(user);
            }
            else{
                System.out.println("Ya existe un usuario con ese teléfono");
                return false;
            }
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean update(User user){
        try{
            Iterable<User> query = selectByCellphone(user.getCellphone());
            if(iterableEmpty(query)){// Es un nuevo usuario
                System.out.println("No existe un usuario con ese teléfono");
                return false;
            }
            else{ //Si existe un usuario que se actualizará
                userRepository.save(user);
            }
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean delete(User user){
        try{
            Iterable<User> query = selectByCellphone(user.getCellphone());
            if(iterableEmpty(query)){//No existe ningun usuario para eliminar
                System.out.println("No existe un usuario con ese teléfono");
                return false;
            }
            else{
                userRepository.delete(user);
            }
        }catch(Exception e){
            return false;
        }
        return true;
    }

}
