package com.agc.inspectores.entity;


import com.agc.inspectores.enums.Area;
import com.agc.inspectores.enums.Funcion;
import jakarta.persistence.*;

@Entity
@Table(name = "inspectores")
public class Inspector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Funcion funcion;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Area area;

    private String imagen;

    //constructor vacio
    public Inspector() {
    }

    // constructor con parametros
    public Inspector(Long id, String nombre, String apellido, String dni, Area area, Funcion funcion, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.area = area;
        this.funcion = funcion;
        this.imagen = imagen;
    }
    //construcotr sin id para que lo tome el dto

    public Inspector(String nombre, String apellido, String dni, Area area, Funcion funcion, String imagen) {
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
