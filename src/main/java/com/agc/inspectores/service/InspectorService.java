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
        Inspector saved = inspectorRepository.save(toEntity(dto));
        return toDTO(saved);
    }

    public Optional<InspectorDTO> update(Long id, InspectorDTO dto) {
        return inspectorRepository.findById(id).map(inspector -> {
            inspector.setNombre(dto.getNombre());
            inspector.setApellido(dto.getApellido());
            inspector.setDni(dto.getDni());
            inspector.setArea(dto.getArea());
            inspector.setImagen(dto.getImagen());
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
                inspector.getImagen()
        );
    }

    private Inspector toEntity(InspectorDTO dto) {
        return new Inspector(
                dto.getNombre(),
                dto.getApellido(),
                dto.getDni(),
                dto.getArea(),
                dto.getImagen()
        );
    }

}
