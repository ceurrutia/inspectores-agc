package com.agc.inspectores.controller;

import com.agc.inspectores.dto.InspectorDTO;
import com.agc.inspectores.service.InspectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/inspectores")
public class InspectorController {
    private final InspectorService inspectorService;

    public InspectorController(InspectorService inspectorService) {
        this.inspectorService = inspectorService;
    }

    // GET público lista
    @GetMapping
    public ResponseEntity<List<InspectorDTO>> getAll() {
        return ResponseEntity.ok(inspectorService.getAll());
    }

    //GET público por id de cada
    @GetMapping("/{id}")
    public ResponseEntity<InspectorDTO> getById(@PathVariable Long id) {
        return inspectorService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST admin y superadmin
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<InspectorDTO> create(@RequestBody InspectorDTO dto) {
        System.out.println("InspectorController - Recibida solicitud POST para crear inspector: " + dto);
        return ResponseEntity.ok(inspectorService.save(dto));
    }

    //PUT admin y superadmin
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<InspectorDTO> update(@PathVariable Long id, @RequestBody InspectorDTO dto) {
        // Ver el ID y el DTO recibido
        System.out.println("InspectorController - Recibida solicitud PUT para actualizar inspector ID: " + id);
        System.out.println("InspectorController - DTO recibido: " + dto);
        try {
            return inspectorService.update(id, dto)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            System.err.println("InspectorController - Error al intentar actualizar inspector ID: " + id + ". Error: " + e.getMessage());
            e.printStackTrace(); // Imprime la traza completa
            return ResponseEntity.internalServerError().build(); // Devuelve 500
        }
    }

    //DELETE solo hacen admin y superadmin
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return inspectorService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
