/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Funciones.Funciones;
import Funciones.Tareas;
import Vista.VistaPrincipal;
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
    private Vista.VistaPrincipal interfaz;
    private Funciones funciones = new Funciones();
    public int filaSeleccionada, cantidad = 0;
    private DefaultTableModel tabla = new DefaultTableModel();
    Tareas tarea;

    public ControladorPrincipal(VistaPrincipal interfaz, Funciones funciones) {
        this.interfaz = interfaz;
        this.funciones = funciones;

        interfaz.getBtnAgregar().addActionListener(this);
        interfaz.getBtnSalir().addActionListener(this);
        interfaz.getBtnCompletar().addActionListener(this);
        interfaz.getBtnEliminar().addActionListener(this);

        limpiarBtn();

        filaSeleccionada = -1;

        interfaz.getTablaTareas().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                filaSeleccionada = interfaz.getTablaTareas().getSelectedRow();

                if (filaSeleccionada == -1) {
                    interfaz.getTablaTareas().clearSelection();
                    limpiarBtn();
                } else {
                    interfaz.getBtnCompletar().setVisible(true);
                    interfaz.getBtnEliminar().setVisible(true);
                }
            }

        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == interfaz.getBtnAgregar()) {
            if (interfaz.getTxtAgregar().isEmpty()) {
                JOptionPane.showMessageDialog(interfaz, "El campo no puede ir vacio");
                return;
            }
            
            cantidad++;
            String id = funciones.generarID(cantidad);
            String txt = funciones.eliminarEspacios(interfaz.getTxtAgregar()).trim();
            
            tarea = new Tareas(id, txt, "Incompleta");
            funciones.llenarDatos(tarea);
            funciones.definirTabla(interfaz);
            this.limpiarBtn();
            filaSeleccionada = -1;
            interfaz.setTxtAgregar("");
        }

        if (e.getSource() == interfaz.getBtnCompletar()) {
            interfaz.getTablaTareas().setValueAt("Completada", filaSeleccionada, 2);
        }

        if (e.getSource() == interfaz.getBtnEliminar()) {
            if (filaSeleccionada >= 0) {

                DefaultTableModel modelo = (DefaultTableModel) interfaz.getTablaTareas().getModel();
                modelo.removeRow(filaSeleccionada);
            }
        }

        if (e.getSource() == interfaz.getBtnSalir()) {
            System.exit(0);
        }
    }
    
    public void limpiarBtn(){
        interfaz.getBtnCompletar().setVisible(false);
        interfaz.getBtnEliminar().setVisible(false);
    }

}
