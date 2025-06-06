/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.todo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 
import java.sql.Timestamp; 
import java.util.ArrayList;
import java.util.List;
import java.util.Date; 

/**
 *
 * @author antoniosalinas
 */
public class TareaDAO {

    
    public List<Tarea> obtenerTodasLasTareas() {
        List<Tarea> tareas = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexionDB.getConnection(); // Obtiene la conexión
            if (conn == null) {
                System.err.println("No se pudo establecer conexión a la base de datos en obtenerTodasLasTareas.");
                return tareas; // Retorna lista vacía si no hay conexión
            }

            // Consulta SQL para seleccionar todas las tareas
            // Se añade ORDER BY id para asegurar un orden consistente en la tabla
            String sql = "SELECT id, titulo, descripcion, estado, fecha_limite FROM tareas ORDER BY id";
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery(); // Ejecuta la consulta

            // Itera sobre los resultados y crea objetos Tarea
            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String descripcion = rs.getString("descripcion");
                String estado = rs.getString("estado");

                // Manejo de la fecha: obtener como Timestamp y convertir a java.util.Date
                Timestamp timestamp = rs.getTimestamp("fecha_limite");
                Date fechaLimite = (timestamp != null) ? new Date(timestamp.getTime()) : null; // Convierte a java.util.Date

                Tarea tarea = new Tarea(id, titulo, descripcion, estado, fechaLimite);
                tareas.add(tarea);
            }

        } catch (SQLException e) {
            System.err.println("Error SQL al obtener todas las tareas: " + e.getMessage());
            e.printStackTrace();
        } finally {
            
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) ConexionDB.closeConnection(conn); // Cierra la conexión
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos en TareaDAO.obtenerTodasLasTareas(): " + e.getMessage());
                e.printStackTrace();
            }
        }
        return tareas;
    }

    public boolean insertarTarea(Tarea tarea) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;

        try {
            conn = ConexionDB.getConnection();
            if (conn == null) {
                System.err.println("No se pudo establecer conexión a la base de datos en insertarTarea.");
                return false;
            }

            // SQL para insertar una tarea. El 'id' se generará automáticamente.
            String sql = "INSERT INTO tareas (titulo, descripcion, estado, fecha_limite) VALUES (?, ?, ?, ?)";
            // Statement.RETURN_GENERATED_KEYS es para que PreparedStatement devuelva el ID autoincremental generado
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, tarea.getTitulo());
            ps.setString(2, tarea.getDescripcion());
            ps.setString(3, tarea.getEstado());
            // Convierte java.util.Date a java.sql.Timestamp para la base de datos
            ps.setTimestamp(4, (tarea.getFechaLimite() != null) ? new Timestamp(tarea.getFechaLimite().getTime()) : null);

            int filasAfectadas = ps.executeUpdate(); // Ejecuta la inserción
            if (filasAfectadas > 0) {
                // Si la inserción fue exitosa, intenta recuperar el ID generado
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        tarea.setId(generatedKeys.getInt(1)); // Asigna el ID generado al objeto Tarea
                    }
                }
                exito = true;
            }

        } catch (SQLException e) {
            System.err.println("Error SQL al insertar tarea: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) ConexionDB.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos en TareaDAO.insertarTarea(): " + e.getMessage());
                e.printStackTrace();
            }
        }
        return exito;
    }

    public boolean actualizarTarea(Tarea tarea) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;

        try {
            conn = ConexionDB.getConnection();
            if (conn == null) {
                System.err.println("No se pudo establecer conexión a la base de datos en actualizarTarea.");
                return false;
            }

            // SQL para actualizar una tarea por su ID
            String sql = "UPDATE tareas SET titulo = ?, descripcion = ?, estado = ?, fecha_limite = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);

            ps.setString(1, tarea.getTitulo());
            ps.setString(2, tarea.getDescripcion());
            ps.setString(3, tarea.getEstado());
            ps.setTimestamp(4, (tarea.getFechaLimite() != null) ? new Timestamp(tarea.getFechaLimite().getTime()) : null);
            ps.setInt(5, tarea.getId()); // El ID para identificar qué tarea actualizar

            int filasAfectadas = ps.executeUpdate(); // Ejecuta la actualización
            if (filasAfectadas > 0) {
                exito = true;
            }

        } catch (SQLException e) {
            System.err.println("Error SQL al actualizar tarea: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) ConexionDB.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos en TareaDAO.actualizarTarea(): " + e.getMessage());
                e.printStackTrace();
            }
        }
        return exito;
    }

    public boolean eliminarTarea(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;

        try {
            conn = ConexionDB.getConnection();
            if (conn == null) {
                System.err.println("No se pudo establecer conexión a la base de datos en eliminarTarea.");
                return false;
            }

            // SQL para eliminar una tarea por su ID
            String sql = "DELETE FROM tareas WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id); // El ID de la tarea a eliminar

            int filasAfectadas = ps.executeUpdate(); // Ejecuta la eliminación
            if (filasAfectadas > 0) {
                exito = true;
            }

        } catch (SQLException e) {
            System.err.println("Error SQL al eliminar tarea: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) ConexionDB.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos en TareaDAO.eliminarTarea(): " + e.getMessage());
                e.printStackTrace();
            }
        }
        return exito;
    }

}