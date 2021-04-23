package com.uroom.backend.Services;

import com.uroom.backend.Repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public com.uroom.backend.Models.Service insert(com.uroom.backend.Models.Service service){
        try{
            return serviceRepository.save(service);
        }catch (Exception e){
            return null;
        }
    }

    public com.uroom.backend.Models.Service update(com.uroom.backend.Models.Service newService){
        try{
            return serviceRepository.save(newService);
        }catch (Exception e){
            return null;
        }
    }

    public List<com.uroom.backend.Models.Service> select(){return serviceRepository.findAll();}

    public Set<com.uroom.backend.Models.Service> selectBySetNames(Set<String> serviceNames){
        return serviceRepository.findByNameIn(serviceNames);
    }
}
