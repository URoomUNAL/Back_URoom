package com.uroom.backend.Services;

import com.uroom.backend.Models.EntitiyModels.Post;
import com.uroom.backend.Models.EntitiyModels.Rent;
import com.uroom.backend.Models.EntitiyModels.Rent.Status;
import com.uroom.backend.Models.EntitiyModels.User;
import com.uroom.backend.Repository.RentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RentService {
    final RentRepository rentRepository;

    public RentService(RentRepository rentRepository){
        this.rentRepository = rentRepository;
    }

    public Rent insert(Rent newRent){
        try{
            return rentRepository.save(newRent);
        }catch (Exception e){
            System.out.println("Error al insertar arriendo: "+e.getMessage());
            return null;
        }
    }

    public boolean delete(Rent rent){
        try{
            rentRepository.delete(rent);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public Rent update(Rent rent) {
        try {
            return rentRepository.save(rent);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Rent> selectByStatus(Rent.Status status){
        try{
            return rentRepository.findByStatus(status);
        }catch (Exception e){
            System.out.println("Error al seleccionar por estado "+status.toString());
            return null;
        }
    }

    public Rent selectById(int rent_id){
        try{
            return rentRepository.findById(rent_id);
        }catch (Exception e){
            System.out.println("Error al seleccionar Arriendo con id "+rent_id);
            return null;
        }
    }

    public List<Rent> selectByUser(User user){
        try{
            return rentRepository.findByUser(user);
        }catch (Exception e){
            System.out.println("Error al buscar los arriendos del usuario "+user);
            return null;
        }
    }

    public List<Rent> selectByPost(Post post){
        try{
            return rentRepository.findByPost(post);
        }catch (Exception e){
            System.out.println("Error al seleccionar los arriendos asociados al post "+post);
            return null;
        }
    }

    public List<Rent> selectByPostAndStatus(Post post, Status status){
        try{
            return rentRepository.findByPostAndStatus(post, status);
        }catch (Exception e){
            System.out.println("Error al seleccionar los arriendos asociados al post y status"+post);
            return null;
        }
    }

    public double getStudentScore(User user){
        try{
            List<Rent> rents = rentRepository.findByUser(user);
            double score = 0;
            int NumberScores = 0;
            for(Rent rent : rents){
                if(rent.getStudentScore()!=null){
                    score += rent.getStudentScore();
                    NumberScores++;
                }
            }
            return score / NumberScores;
        }catch (Exception e){
            System.out.println("Error al buscar los arriendos del usuario "+user);
            return 0;
        }
    }

    public LocalDate getLastRent(Post post){
        List<Rent> rents = rentRepository.findByPostAndStatus(post, Rent.Status.ENDED);
        if(rents.size() > 0){
            LocalDate date = rents.get(0).getEnd();
            for(Rent rent : rents){
                if(date.isBefore(rent.getEnd())){
                    date = rent.getEnd();
                }
            }
            return date;
        }
        else{
            return LocalDate.MIN;
        }
    }
}
