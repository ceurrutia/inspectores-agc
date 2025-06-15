package com.agc.inspectores.enums;

public enum Area {
    FISCALIZACION_Y_CONTROL("Fiscalización y Control"),
    SEGURIDAD_ALIMENTARIA("Seguridad Alimentaria"),
    HABILITACIONES_Y_PERMISOS("Habilitaciones y Permisos"),
    FISCALIZACION_Y_CONTROL_DE_OBRAS("Fiscalización y Control de Obras");

    private final String label;

    Area(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}