package com.gym.members_microservice.service;

import com.gym.members_microservice.model.Miembro;
import com.gym.members_microservice.respository.MiembroRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MiembroServiceImpl implements MiembroService {

    private final MiembroRepository miembroRepository;

    public MiembroServiceImpl(MiembroRepository miembroRepository) {
        this.miembroRepository = miembroRepository;
    }

    @Override
    public Miembro save(Miembro miembro) {
        if (miembro.getFechaInscripcion() == null) {
            miembro.setFechaInscripcion(LocalDate.now());
        }
        return miembroRepository.save(miembro);
    }

    @Override
    public List<Miembro> findAll() {
        return miembroRepository.findAll();
    }
}


