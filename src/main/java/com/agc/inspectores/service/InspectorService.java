package com.agc.inspectores.service;

import com.agc.inspectores.dto.InspectorDTO;
import com.agc.inspectores.entity.Inspector;
import com.agc.inspectores.repository.InspectorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InspectorService {

    private final InspectorRepository inspectorRepository;

    public List<InspectorDTO> buscarPorApellido(String apellido) {
        return inspectorRepository.findByApellidoContainingIgnoreCase(apellido)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public InspectorService(InspectorRepository inspectorRepository) {
        this.inspectorRepository = inspectorRepository;
    }

    public List<InspectorDTO> getAll() {
        return inspectorRepository.findAll().stream().map(this::toDTO).toList();
    }

    public Optional<InspectorDTO> getById(Long id) {
        return inspectorRepository.findById(id).map(this::toDTO);
    }

    public InspectorDTO save(InspectorDTO dto) {
        if (dto.getImagen() == null || dto.getImagen().isBlank()) {
            dto.setImagen("https://png.pngtree.com/png-vector/20221203/ourmid/pngtree-cartoon-style-male-user-profile-icon-vector-illustraton-png-image_6489287.png");
        }
        Inspector saved = inspectorRepository.save(toEntity(dto));
        return toDTO(saved);
    }

    public Optional<InspectorDTO> update(Long id, InspectorDTO dto) {
        return inspectorRepository.findById(id).map(inspector -> {
            inspector.setNombre(dto.getNombre());
            inspector.setApellido(dto.getApellido());
            inspector.setDni(dto.getDni());
            inspector.setArea(dto.getArea());
            inspector.setFuncion(dto.getFuncion());

            if (dto.getImagen() == null || dto.getImagen().isBlank()) {
                inspector.setImagen(inspector.getImagen() != null
                        ? inspector.getImagen()
                        : "https://png.pngtree.com/png-vector/20221203/ourmid/pngtree-cartoon-style-male-user-profile-icon-vector-illustraton-png-image_6489287.png");
            } else {
                inspector.setImagen(dto.getImagen());
            }
            return toDTO(inspectorRepository.save(inspector));
        });
    }

    public boolean delete(Long id) {
        if (inspectorRepository.existsById(id)) {
            inspectorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Mapper
    private InspectorDTO toDTO(Inspector inspector) {
        return new InspectorDTO(
                inspector.getId(),
                inspector.getNombre(),
                inspector.getApellido(),
                inspector.getDni(),
                inspector.getArea(),
                inspector.getFuncion(),
                inspector.getImagen()
        );
    }

    private Inspector toEntity(InspectorDTO dto) {
        return new Inspector(
                dto.getNombre(),
                dto.getApellido(),
                dto.getDni(),
                dto.getArea(),
                dto.getFuncion(),
                dto.getImagen()
        );
    }

}
