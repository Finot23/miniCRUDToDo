/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.todo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author antoniosalinas
 */
public class ConexionDB {
    

        // se establece coneccion con la DB
   private static final String DB_URL = "jdbc:mysql://localhost:3306/tarea_dao";
   private static final String USER = "admin";
   private static final String PASS = "admin";
      
   
    public static Connection getConnection() {
        Connection conn = null; 

        try {
            
            System.out.println("Intentando conectar a la base de datos...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("¡Conexión exitosa!");

        } catch (SQLException se) {
            
            se.printStackTrace(); 
            JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos: " + se.getMessage(), "Error de DB", JOptionPane.ERROR_MESSAGE);
        }
        return conn; 
    }

   
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
    
