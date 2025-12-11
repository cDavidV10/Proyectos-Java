/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Funciones.Funciones;
import Funciones.GuardarFichero;
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
    public int filaSeleccionada, cantidad, datosIniciales, datosFinales;
    private DefaultTableModel tabla = new DefaultTableModel();
    GuardarFichero fichero;
    Tareas tarea;

    public ControladorPrincipal(VistaPrincipal interfaz, VistaTabla viewTabla, Funciones funciones) {
        this.interfaz = interfaz;
        this.funciones = funciones;
        this.viewTabla = viewTabla;

        interfaz.getBtnAgregar().addActionListener(this);
        interfaz.getBtnSalir().addActionListener(this);
        fichero = new GuardarFichero();
        funciones.definirTabla(viewTabla);

        funciones.cargarDatos(viewTabla, false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == interfaz.getBtnAgregar()) {
            if (interfaz.getTxtAgregar().isEmpty()) {
                JOptionPane.showMessageDialog(interfaz, "El campo no puede ir vacio");
                return;
            }

            cantidad = fichero.getLastId() + 1;
            datosIniciales = fichero.getSize();
            String id = funciones.generarID(cantidad);
            String txt = funciones.eliminarEspacios(interfaz.getTxtAgregar()).trim();

            tarea = new Tareas(id, txt, "Incompleta");
            fichero.llenarDatos(tarea);
            funciones.cargarDatos(viewTabla, false);
            filaSeleccionada = -1;
            interfaz.setTxtAgregar("");

            datosFinales = fichero.getSize();

            if (datosFinales > datosIniciales) {
                JOptionPane.showMessageDialog(interfaz, "Tarea Agregada correctamente");
            }
        }

        if (e.getSource() == interfaz.getBtnSalir()) {
            System.exit(0);
        }
    }

}
