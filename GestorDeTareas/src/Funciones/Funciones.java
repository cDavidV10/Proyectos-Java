/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Funciones;

import javax.swing.table.DefaultTableModel;
import Vista.VistaPrincipal;

/**
 *
 * @author cdavi
 */
public class Funciones {
    
    private DefaultTableModel tabla = new DefaultTableModel();
    private VistaPrincipal interfaz;
    public void definirTabla(VistaPrincipal interfaz, String txt){
    
        String[] columnas = {"Codigo", "Tarea", "Estado"};
        
        tabla.setColumnIdentifiers(columnas);
        interfaz.getTablaTareas().setModel(tabla);
        
        Object[] datos = {
            "001",
            txt,
            "Incompleto"
            
        };
        
        tabla.addRow(datos);
        
        
    }
    
    public void generarID(){
        
    }
}
