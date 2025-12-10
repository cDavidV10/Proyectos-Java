/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Funciones.Funciones;
import Funciones.GuardarFichero;
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
    GuardarFichero fichero;

    public int filaSeleccionada, pagina = 0;
    ArrayList<Tareas> tareas = new ArrayList<>();

    public ControladorTabla(VistaTabla viewTabla, Funciones funciones) {
        this.viewTabla = viewTabla;
        this.funciones = funciones;

        fichero = new GuardarFichero();
        tareas = fichero.getList();
        // System.out.println(tareas.size());

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
            int modificacion;
            if (pagina >= 1) {
                modificacion = filaSeleccionada + 5;
                tareas.get(modificacion).setEstado("Completada");
            } else {
                modificacion = filaSeleccionada;
                tareas.get(filaSeleccionada).setEstado("Completada");
            }
            for (Tareas tareas2 : tareas) {
                System.out.println(tareas2.toCsv());
            }
            System.out.println(tareas.size());
            fichero.setList(tareas);
            funciones.cargarDatos(viewTabla);
        }

        if (e.getSource() == viewTabla.getBtnEliminar()) {
            if (filaSeleccionada >= 0) {

                int eliminacion;

                if (pagina >= 1) {
                    eliminacion = filaSeleccionada + 5;
                    tareas.remove(eliminacion);
                } else {
                    eliminacion = filaSeleccionada;
                    tareas.remove(eliminacion);
                }
                funciones.setList(tareas);
                funciones.cargarDatos(viewTabla);
            }
        }

        if (e.getSource() == viewTabla.getBtnSiguiente()) {
            if ((pagina + 1) < funciones.paginasTotales()) {
                pagina++;
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
