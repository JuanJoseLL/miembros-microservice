package com.gym.members_microservice.controller;

import com.gym.members_microservice.dto.ErrorResponse;
import com.gym.members_microservice.model.Miembro;
import com.gym.members_microservice.service.MiembroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/miembros")
@Tag(name = "Miembros", description = "Gestión de miembros del gimnasio")
public class MiembroController {

    private final MiembroService miembroService;

    public MiembroController(MiembroService miembroService) {
        this.miembroService = miembroService;
    }

    @Operation(summary = "Crear un nuevo miembro", description = "Crea un nuevo miembro del gimnasio (solo administradores).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Miembro creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Miembro.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado (token inválido o expirado)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "No autorizado (requiere rol ADMIN)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Miembro> create(@RequestBody Miembro miembro) {
        Miembro saved = miembroService.save(miembro);
        return ResponseEntity.created(URI.create("/miembros/" + saved.getId())).body(saved);
    }

    @Operation(summary = "Obtener todos los miembros", description = "Devuelve la lista de todos los miembros (solo administradores).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de miembros",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Miembro.class)))),
            @ApiResponse(responseCode = "401", description = "No autenticado (token inválido o expirado)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "No autorizado (requiere rol ADMIN)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Miembro>> findAll() {
        return ResponseEntity.ok(miembroService.findAll());
    }
}
