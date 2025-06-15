package com.agc.inspectores.repository;

import com.agc.inspectores.entity.Inspector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InspectorRepository extends JpaRepository<Inspector, Long> {
    List<Inspector> findByApellidoContainingIgnoreCase(String apellido);
}
