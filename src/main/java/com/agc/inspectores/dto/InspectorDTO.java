package com.agc.inspectores.dto;

import com.agc.inspectores.enums.Area;
import com.agc.inspectores.enums.Funcion;

public class InspectorDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private Area area;
    private Funcion funcion;
    private String imagen;

    //constructor vacio
    public InspectorDTO() {
    }

    //constructor con parametros

    public InspectorDTO(Long id, String nombre, String apellido,
                        String dni, Area area, Funcion funcion, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.area = area;
        this.funcion = funcion;
        this.imagen = imagen;
    }

    //getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
