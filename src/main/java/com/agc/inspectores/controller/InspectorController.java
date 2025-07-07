package com.agc.inspectores.controller;

import com.agc.inspectores.dto.InspectorDTO;
import com.agc.inspectores.service.InspectorService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
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
    public ResponseEntity<List<InspectorDTO>> getAllOrSearchByApellido(
            @RequestParam(required = false) String apellido) {

        List<InspectorDTO> inspectores;
        if (apellido != null && !apellido.trim().isEmpty()) {
            inspectores = inspectorService.buscarPorApellido(apellido);
        } else {
            inspectores = inspectorService.getAll();
        }
        return ResponseEntity.ok(inspectores);
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
        //ID y el DTO recibido
        System.out.println("InspectorController - Recibida solicitud PUT para actualizar inspector ID: " + id);
        System.out.println("InspectorController - DTO recibido: " + dto);
        try {
            return inspectorService.update(id, dto)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            System.err.println("InspectorController - Error al intentar actualizar inspector ID: " + id + ". Error: " + e.getMessage());
            e.printStackTrace(); //imprimir stacktrace
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

    //Metodo para descarga en .csv
    @GetMapping("/export")
    public void exportInspectoresToCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"inspectores.csv\"");

        List<InspectorDTO> inspectores = inspectorService.getAll();

        PrintWriter writer = new PrintWriter(response.getOutputStream(), true, java.nio.charset.StandardCharsets.UTF_8);
        writer.write('\ufeff'); // BOM para Excel

        // Encabezado CSV
        writer.println("ID,Nombre,Apellido,DNI,Área, Funcion");

        // Datos
        for (InspectorDTO inspector : inspectores) {
            writer.printf(
                    "%s,%s,%s,%s,%s,%s%n",
                    String.valueOf(inspector.getId()),
                    inspector.getNombre(),
                    inspector.getApellido(),
                    inspector.getDni(),
                    inspector.getArea(),
                    inspector.getFuncion()
            );
        }

        writer.flush();
        writer.close();
    }


}
