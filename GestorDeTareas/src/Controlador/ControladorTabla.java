/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Funciones.Funciones;
import Funciones.Tareas;
import Vista.VistaTabla;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cdavi
 */
public class ControladorTabla implements ActionListener {
    private VistaTabla viewTabla;
    private Funciones funciones;

    public int filaSeleccionada, pagina = 0;

    public ControladorTabla(VistaTabla viewTabla, Funciones funciones) {
        this.viewTabla = viewTabla;
        this.funciones = funciones;

        viewTabla.getBtnCompletar().addActionListener(this);
        viewTabla.getBtnEliminar().addActionListener(this);
        viewTabla.getBtnSiguiente().addActionListener(this);
        viewTabla.getBtnAnterior().addActionListener(this);

        filaSeleccionada = -1;

        viewTabla.getTablaTareas().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                filaSeleccionada = viewTabla.getTablaTareas().getSelectedRow();

                if (filaSeleccionada == -1) {
                    viewTabla.getTablaTareas().clearSelection();
                } else {
                    viewTabla.getBtnCompletar().setVisible(true);
                    viewTabla.getBtnEliminar().setVisible(true);
                }
            }

        });
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == viewTabla.getBtnCompletar()) {
            System.out.println(filaSeleccionada);
            viewTabla.getTablaTareas().setValueAt("Completada", filaSeleccionada, 2);
        }

        if (e.getSource() == viewTabla.getBtnEliminar()) {
            if (filaSeleccionada >= 0) {

                DefaultTableModel modelo = (DefaultTableModel) viewTabla.getTablaTareas().getModel();
                modelo.removeRow(filaSeleccionada);
            }
        }

        if (e.getSource() == viewTabla.getBtnSiguiente()) {
            if ((pagina + 1) < funciones.paginasTotales()) {
                pagina++;
                System.out.println(pagina);
                funciones.setPaginaActual(pagina);
                funciones.cargarDatos(viewTabla);

            }

        }

        if (e.getSource() == viewTabla.getBtnAnterior()) {
            if ((pagina + 1) > 0) {
                pagina--;
                funciones.setPaginaActual(pagina);
                funciones.cargarDatos(viewTabla);

            }

        }

    }

    public void limpiarBtn() {
        viewTabla.getBtnCompletar().setVisible(false);
        viewTabla.getBtnEliminar().setVisible(false);
    }
}
