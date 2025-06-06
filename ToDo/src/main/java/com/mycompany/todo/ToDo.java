/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.todo;

/**
 *
 * @author antoniosalinas
 */
public class ToDo {

   public static void main(String[] args) {
        // Usa EventQueue.invokeLater para asegurar que la creación y visualización
        // de la GUI se haga en el Event Dispatch Thread (EDT), que es el hilo
        // seguro para operaciones de Swing.
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TareaGUI().setVisible(true); // Crea una nueva instancia de TareaGUI y la hace visible
            }
        });
    }
}