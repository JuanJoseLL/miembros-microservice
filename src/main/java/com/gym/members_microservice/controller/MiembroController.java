package com.gym.members_microservice.controller;

import com.gym.members_microservice.model.Miembro;
import com.gym.members_microservice.service.MiembroService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/miembros")
public class MiembroController {

    private final MiembroService miembroService;

    public MiembroController(MiembroService miembroService) {
        this.miembroService = miembroService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Miembro> create(@RequestBody Miembro miembro) {
        Miembro saved = miembroService.save(miembro);
        return ResponseEntity.created(URI.create("/miembros/" + saved.getId())).body(saved);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Miembro>> findAll() {
        return ResponseEntity.ok(miembroService.findAll());
    }
}


