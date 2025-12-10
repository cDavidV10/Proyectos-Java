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

    public int filaSeleccionada, pagina = 1;
    ArrayList<Tareas> tareas = new ArrayList<>();

    public ControladorTabla(VistaTabla viewTabla, Funciones funciones) {
        this.viewTabla = viewTabla;
        this.funciones = funciones;

        fichero = new GuardarFichero();
        tareas = fichero.getList();

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

        viewTabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                viewTabla.getTablaTareas().setColumnSelectionAllowed(false);
                viewTabla.getTablaTareas().setCellSelectionEnabled(false);
                limpiarBtn();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == viewTabla.getBtnCompletar()) {
            ArrayList<Tareas> lista = fichero.getList();
            int index = pagina * 5 + filaSeleccionada;

            if (filaSeleccionada >= 0 && index >= 0 && index < lista.size()) {
                lista.get(index).setEstado("Completada");
                fichero.setList(lista);
                funciones.cargarDatos(viewTabla);
            } else {
                System.out.println("Índice fuera de rango al completar: fila=" + filaSeleccionada + " index=" + index
                        + " size=" + lista.size());
            }
        }

        if (e.getSource() == viewTabla.getBtnEliminar()) {
            if (filaSeleccionada >= 0) {
                ArrayList<Tareas> lista = fichero.getList();
                int index = pagina * 5 + filaSeleccionada;

                if (index >= 0 && index < lista.size()) {
                    lista.remove(index);
                    fichero.setList(lista);
                    funciones.cargarDatos(viewTabla);
                } else {
                    System.out.println("Índice fuera de rango al eliminar: fila=" + filaSeleccionada + " index=" + index
                            + " size=" + lista.size());
                }
            }
        }

        if (e.getSource() == viewTabla.getBtnSiguiente()) {
            System.out.println(pagina);
            if (pagina < funciones.paginasTotales()) {
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
