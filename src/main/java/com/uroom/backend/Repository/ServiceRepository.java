package com.uroom.backend.Repository;

import com.uroom.backend.Models.EntitiyModels.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ServiceRepository  extends JpaRepository<Service, Integer> {
    Set<Service> findByNameIn(Set<String> serviceNames);
}
