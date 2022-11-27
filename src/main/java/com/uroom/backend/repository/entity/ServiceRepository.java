package com.uroom.backend.repository.entity;

import com.uroom.backend.models.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ServiceRepository  extends JpaRepository<Service, Integer> {
    Set<Service> findByNameIn(Set<String> serviceNames);
}
