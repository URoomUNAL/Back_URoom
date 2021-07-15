package com.uroom.backend.Services;

import com.uroom.backend.Models.EntitiyModels.Rent;
import com.uroom.backend.Models.EntitiyModels.Rent.Status;
import com.uroom.backend.Repository.RentRepository;
import org.springframework.stereotype.Service;

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

    public List<Rent> selectByUser_id(int user_id){
        try{
            return rentRepository.findByUser_id(user_id);
        }catch (Exception e){
            System.out.println("Error al buscar los arriendos del usuario "+user_id);
            return null;
        }
    }

    public List<Rent> selectByPost_id(int post_id){
        try{
            return rentRepository.findByPost_id(post_id);
        }catch (Exception e){
            System.out.println("Error al seleccionar los arriendos asociados al post "+post_id);
            return null;
        }
    }
}
