/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.todo;

import java.util.Date;

/**
 *
 * @author antoniosalinas
 */
public class Tarea {
    private int id; // Es buena práctica hacer los atributos privados y usar getters/setters
    private String titulo;
    private String descripcion;
    private String estado;
    private Date fechaLimite;


    public Tarea() {
        // Constructor vacío, útil para frameworks o cuando necesitas instanciar y luego setear propiedades
    }


    public Tarea(int id, String titulo, String descripcion, String estado, Date fechaLimite) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaLimite = fechaLimite;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    // Opcional pero muy recomendado para depuración:
    @Override
    public String toString() {
        return "Tarea{" +
               "id=" + id +
               ", titulo='" + titulo + '\'' +
               ", descripcion='" + descripcion + '\'' +
               ", estado='" + estado + '\'' +
               ", fechaLimite=" + fechaLimite +
               '}';
    }
}