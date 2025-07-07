package com.agc.inspectores.enums;

import com.fasterxml.jackson.annotation.JsonValue;

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
}