/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Funciones.Funciones;
import Funciones.Tareas;
import Vista.VistaPrincipal;
import Vista.VistaTabla;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cdavi
 */
public class ControladorPrincipal implements ActionListener {
    private VistaPrincipal interfaz;
    private VistaTabla viewTabla = new VistaTabla();
    private Funciones funciones = new Funciones();
    public int filaSeleccionada, cantidad = 0, datosIniciales, datosFinales;
    private DefaultTableModel tabla = new DefaultTableModel();
    Tareas tarea;

    public ControladorPrincipal(VistaPrincipal interfaz, VistaTabla viewTabla, Funciones funciones) {
        this.interfaz = interfaz;
        this.funciones = funciones;
        this.viewTabla = viewTabla;

        interfaz.getBtnAgregar().addActionListener(this);
        interfaz.getBtnSalir().addActionListener(this);


        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == interfaz.getBtnAgregar()) {
            if (interfaz.getTxtAgregar().isEmpty()) {
                JOptionPane.showMessageDialog(interfaz, "El campo no puede ir vacio");
                return;
            }
            
            datosIniciales = funciones.sizeData();
            cantidad++;
            String id = funciones.generarID(cantidad);
            String txt = funciones.eliminarEspacios(interfaz.getTxtAgregar()).trim();

            tarea = new Tareas(id, txt, "Incompleta");
            funciones.llenarDatos(tarea);
            funciones.definirTabla(viewTabla);
            filaSeleccionada = -1;
            interfaz.setTxtAgregar("");
            
            datosFinales = funciones.sizeData();
            
            if(datosFinales > datosIniciales){
                JOptionPane.showMessageDialog(interfaz, "Tarea Agregada correctamente");
            }
        }

        if (e.getSource() == interfaz.getBtnSalir()) {
            System.exit(0);
        }
    }


}
