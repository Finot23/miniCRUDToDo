/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.todo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane; 

/**
 *
 * @author antoniosalinas
 */
public class ConexionDB {

    // Configuración de la conexión a la base de datos
    private static final String DB_URL = "jdbc:mysql://localhost:3306/tarea_dao"; // URL de tu base de datos
    private static final String USER = "admin"; // Usuario de la base de datos
    private static final String PASS = "admin"; // Contraseña del usuario

    public static Connection getConnection() {
        Connection conn = null;

        try {
            System.out.println("Intentando conectar a la base de datos: " + DB_URL);
            
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("¡Conexión exitosa a la base de datos!");

        } catch (SQLException se) {
            System.err.println("Error de conexión a la base de datos:");
            System.err.println("SQL State: " + se.getSQLState());
            System.err.println("Error Code: " + se.getErrorCode());
            System.err.println("Message: " + se.getMessage());
            se.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos: " + se.getMessage() +
                    "\nPor favor, verifica que el servidor de la base de datos esté corriendo y los datos de conexión sean correctos.",
                    "Error de Conexión a la DB", JOptionPane.ERROR_MESSAGE);
        }
        return conn;
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexión a la base de datos cerrada.");
            } catch (SQLException se) {
                System.err.println("Error al cerrar la conexión a la base de datos: " + se.getMessage());
                se.printStackTrace();
            }
        }
    }
}