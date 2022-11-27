package com.uroom.backend.services;

import com.uroom.backend.models.entity.User;
import com.uroom.backend.models.ldap.UserLdap;
import com.uroom.backend.repository.entity.UserRepository;
//import org.hibernate.exception.ConstraintViolationException;
//import org.hibernate.exception.DataException;
//import org.springframework.dao.DataIntegrityViolationException;
import com.uroom.backend.repository.ldap.UserLdapRepository;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;
//import org.hibernate.exception.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final LdapTemplate ldapTemplate;
    private final UserLdapRepository userLdapRepository;

    public UserService(UserRepository userRepository, LdapTemplate ldapTemplate, UserLdapRepository userLdapRepository){
        this.userRepository = userRepository;
        this.ldapTemplate = ldapTemplate;
        this.userLdapRepository = userLdapRepository;
    }

    public boolean iterableEmpty(Iterable<User> query){
        List<User> listOfIterable = new ArrayList<>();
        query.forEach(listOfIterable::add);
        return listOfIterable.isEmpty();
    }

    public List<User> select(){
        List<User> users = this.userRepository.findAll();
        for(User user : users){
            UserLdap userLdap = this.userLdapRepository.findByEmail(user.getEmail());
            user.setAge(Integer.parseInt(userLdap.getAge()));
            user.setCellphone(user.getCellphone());
        }
        return users;
    }
    public List<User> selectByEmail(String email) {
        List<User> users = this.userRepository.findByEmail(email);
        for(User user : users){
            UserLdap userLdap = this.userLdapRepository.findByEmail(user.getEmail());
            user.setAge(Integer.parseInt(userLdap.getAge()));
            user.setCellphone(userLdap.getCellphone());
            user.setPassword(userLdap.getPassword());
        }
        return users;
    }
    public List<User> selectById(int id){
        List<User> users = this.userRepository.findById(id);
        for(User user : users){
            UserLdap userLdap = this.userLdapRepository.findByEmail(user.getEmail());
            user.setAge(Integer.parseInt(userLdap.getAge()));
            user.setCellphone(user.getCellphone());
        }
        return users;
    }

    public int insert(User user){
        try{
            List<User> query2 = selectByEmail(user.getEmail());
            if(query2.isEmpty()){// Es un nuevo usuario
                user.setIs_active(true);
                UserLdap userLdap = new UserLdap();
                userLdap.setDn(
                        LdapNameBuilder.newInstance()
                                //.add("dc", "co")
                                //.add("dc", "com")
                                //.add("dc", "uroom")
                                    .add("ou", "crypto")
                                .add("cn", user.getEmail())
                                .build()
                );
                userLdap.setSn(user.getName());
                userLdap.setUid(user.getCellphone());
                userLdap.setGidNumber(user.getCellphone());
                userLdap.setUidNumber(user.getCellphone());
                userLdap.setHomeDirectory("/home/users/test/" + user.getEmail());
                userLdap.setEmail(user.getEmail());
                userLdap.setPassword(user.getPassword());
                userLdap.setAge(user.getAge().toString());
                userLdap.setCellphone(user.getCellphone());
                this.ldapTemplate.create(userLdap);
                user.setDistinguishedName(userLdap.getDn().toString());
                this.userRepository.save(user);
                return 0;
            }else if(iterableEmpty(query2)){
                return 2;
            }else{
                return 1;
            }
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public boolean update(User user){
        try{
            Iterable<User> query = userRepository.findById(user.getId());
            if(iterableEmpty(query)){// Es un nuevo usuario
                return false;
            }
            else{ //Si existe un usuario que se actualizará
                this.userRepository.save(user);
            }
        }catch(Exception e){
            return false;
        }
        return true;
    }

    public boolean delete(User user){
        try{
            Iterable<User> query = userRepository.findById(user.getId());
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
