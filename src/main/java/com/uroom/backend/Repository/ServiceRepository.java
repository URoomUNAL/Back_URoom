package com.uroom.backend.Repository;

import com.uroom.backend.Models.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository  extends JpaRepository<Service, Integer> {
}
