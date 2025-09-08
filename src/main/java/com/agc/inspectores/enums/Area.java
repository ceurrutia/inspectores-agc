package com.agc.inspectores.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum Area {
    FISCALIZACION_Y_CONTROL("DGFyC"),
    SEGURIDAD_ALIMENTARIA("DGHySA"),
    HABILITACIONES_Y_PERMISOS("DGHP"),
    FISCALIZACION_Y_CONTROL_DE_OBRAS("DGFyCO"),
    LEGAL_Y_TECNICA("DGLyT"),
    UOFI("UOFI"),
    GOCI("GOCI"),
    UOPCG("UOPCG"),
    UCA("UCA"),
    AIP("AIP");

    private final String label;

    Area(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static Area fromString(String value) {
        return Arrays.stream(Area.values())
                .filter(a -> a.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Área inválida: " + value));
    }

}