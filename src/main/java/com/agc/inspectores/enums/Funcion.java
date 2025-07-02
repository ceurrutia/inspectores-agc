package com.agc.inspectores.enums;

public enum Funcion {
    INSPECTOR("Inspector"),
    VERIFICCADOR("Verificador");

    private final String label;

    Funcion(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
