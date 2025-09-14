package com.gym.members_microservice.service;

import com.gym.members_microservice.model.Miembro;
import java.util.List;

public interface MiembroService {
    Miembro save(Miembro miembro);
    List<Miembro> findAll();
}


