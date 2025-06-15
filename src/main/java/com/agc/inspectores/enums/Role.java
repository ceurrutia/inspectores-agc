package com.agc.inspectores.enums;

public enum Role {
    SUPERADMIN("Superadmin"),
    ADMIN("Admin");

    private final String label;

    Role(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }

}
