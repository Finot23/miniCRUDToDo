/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.todo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author antoniosalinas
 */
public class TareaDAO {
    
    
   // Metodos
     public List<Tarea> obtenerTodasLasTareas() {
      
        List<Tarea> tareas = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            
            conn = ConexionDB.getConnection();

            //Consulta
            String sql = "SELECT id, titulo, descripcion, estado, fecha_limite FROM tareas"; 
            ps = conn.prepareStatement(sql); 

        
            rs = ps.executeQuery();

          
            while (rs.next()) {
                
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String descripcion = rs.getString("descripcion");
                String estado = rs.getString("estado");

                
                Timestamp timestamp = rs.getTimestamp("fecha_limite");
                Date fechaLimite = null;
                if (timestamp != null) {
                    fechaLimite = new Date(timestamp.getTime());
                }

                
                Tarea tarea = new Tarea(id, titulo, descripcion, estado, fechaLimite);
                
                tareas.add(tarea);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener todas las tareas: " + e.getMessage());
            e.printStackTrace(); 
        } finally {
            
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) ConexionDB.closeConnection(conn); 
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos en TareaDAO.obtenerTodasLasTareas(): " + e.getMessage());
                e.printStackTrace();
            }
        }
        return tareas;
     }

    
  
    
    private void actualizarTarea(){
        
    }
    
    private void getEstado(){
        
    }
    
}
