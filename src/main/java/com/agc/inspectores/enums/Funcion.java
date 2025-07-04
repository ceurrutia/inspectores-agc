package com.agc.inspectores.enums;

public enum Funcion {
    INSPECTOR("Inspector/a"),
    VERIFICCADOR("Verificador/a"),
    NOTIFICADOR("Notificador/a"),
    GERENTE_OPERATIVO("Gerente Operativo"),
    SUBGERENTE_OPERATIVO("Suberente Operativo"),
    DIRECTOR_GENERAL("Director General"),
    COORDINADOR("Coordinador/a"),
    RELEVADOR("Relevador/a");


    private final String label;

    Funcion(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
