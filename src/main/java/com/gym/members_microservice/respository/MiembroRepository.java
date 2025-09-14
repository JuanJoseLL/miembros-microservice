package com.gym.members_microservice.respository;

import com.gym.members_microservice.model.Miembro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MiembroRepository extends JpaRepository<Miembro, String> {
    boolean existsByEmail(String email);
}


