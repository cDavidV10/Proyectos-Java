/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Funciones;

import javax.swing.table.DefaultTableModel;
import Vista.VistaPrincipal;
import java.awt.List;
import java.util.ArrayList;

/**
 *
 * @author cdavi
 */
public class Funciones {
    
    private DefaultTableModel tabla = new DefaultTableModel();
    private VistaPrincipal interfaz;

    
    public void definirTabla(VistaPrincipal interfaz, String txt, int cantidad){
    
        String[] columnas = {"Codigo", "Tarea", "Estado"};
        
        tabla.setColumnIdentifiers(columnas);
        interfaz.getTablaTareas().setModel(tabla);
        
        Object[] datos = {
            this.generarID(cantidad),
            txt,
            "Incompleto"
            
        };
        
        tabla.addRow(datos);
        
        
    }
    
    public String eliminarEspacios(String txt){
        String[] cadenas = txt.split("\\s+");
        
        String textoFinal = String.join(" ", cadenas);
        
        return textoFinal;
    
    }
    
    public String generarID(int cantidad){
        String correlativo = String.valueOf(cantidad);
        if(cantidad < 10){
            return "Tarea-00" + correlativo;
        }
        
        if(cantidad >= 10 && cantidad < 100){
            return "Tarea-0" + cantidad;
        }
        return "Tarea-0" + cantidad;
    }
    
}
