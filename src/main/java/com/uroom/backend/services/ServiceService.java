package com.uroom.backend.services;

import com.uroom.backend.repository.entity.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public com.uroom.backend.models.entity.Service insert(com.uroom.backend.models.entity.Service service){
        try{
            return serviceRepository.save(service);
        }catch (Exception e){
            return null;
        }
    }

    public com.uroom.backend.models.entity.Service update(com.uroom.backend.models.entity.Service newService){
        try{
            return serviceRepository.save(newService);
        }catch (Exception e){
            return null;
        }
    }

    public List<com.uroom.backend.models.entity.Service> select(){return serviceRepository.findAll();}

    public Set<com.uroom.backend.models.entity.Service> selectBySetNames(Set<String> serviceNames){
        return serviceNames == null ? null : serviceRepository.findByNameIn(serviceNames);
    }
}
