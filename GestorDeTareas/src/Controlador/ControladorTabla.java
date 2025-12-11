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
    public boolean filtroActivo = false;
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
        viewTabla.getCheckBox().addActionListener(this);

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

                if (filtroActivo) {
                    viewTabla.getBtnCompletar().setVisible(false);

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

        if (e.getSource() == viewTabla.getCheckBox()) {
            if (viewTabla.getCheckBox().isSelected()) {
                filtroActivo = true;
                funciones.Filtro();
                pagina = 1;
                limpiarBtn();
                funciones.setPaginaActual(pagina);
                funciones.cargarDatos(viewTabla, filtroActivo);
            } else {
                filtroActivo = false;
                funciones.setList(fichero.getList());
                pagina = 1;
                funciones.setPaginaActual(pagina);
                funciones.cargarDatos(viewTabla, filtroActivo);
                limpiarBtn();
            }

        }

        if (e.getSource() == viewTabla.getBtnCompletar()) {
            ArrayList<Tareas> lista = fichero.getList();
            int index = (pagina - 1) * 5 + filaSeleccionada;

            if (filaSeleccionada >= 0 && index >= 0 && index < lista.size()) {
                lista.get(index).setEstado("Completada");
                fichero.setList(lista);
                funciones.cargarDatos(viewTabla, filtroActivo);
            } else {
                System.out.println("Índice fuera de rango al completar: fila=" + filaSeleccionada + " index=" + index
                        + " size=" + lista.size());
            }
        }

        if (e.getSource() == viewTabla.getBtnEliminar()) {
            if (filaSeleccionada >= 0) {
                ArrayList<Tareas> lista = fichero.getList();
                int index = (pagina - 1) * 5 + filaSeleccionada;

                if (filtroActivo) {
                    ArrayList<Tareas> aux = funciones.getDatosTabla();
                    int indexReal = 0;

                    for (int i = 0; i < lista.size(); i++) {
                        if (lista.get(i).getId().equals(aux.get(index).getId())) {
                            indexReal = i;
                            break;
                        }
                    }
                    lista.remove(indexReal);
                    fichero.setList(lista);
                    funciones.cargarDatos(viewTabla, filtroActivo);
                    return;
                }

                if (index >= 0 && index < lista.size()) {
                    System.out.println("entra");

                    System.out.println(lista.get(index).toCsv());
                    lista.remove(index);
                    fichero.setList(lista);
                    funciones.cargarDatos(viewTabla, filtroActivo);
                } else {
                    System.out.println("Índice fuera de rango al eliminar: fila=" +
                            filaSeleccionada + " index=" + index
                            + " size=" + lista.size());
                }
            }
        }

        if (e.getSource() == viewTabla.getBtnSiguiente()) {
            limpiarCheckBox();
            if (pagina < funciones.paginasTotales()) {
                pagina++;
                funciones.setPaginaActual(pagina);
                funciones.cargarDatos(viewTabla, filtroActivo);

            }

        }

        if (e.getSource() == viewTabla.getBtnAnterior()) {
            limpiarCheckBox();
            if ((pagina + 1) > 0) {
                pagina--;
                funciones.setPaginaActual(pagina);
                funciones.cargarDatos(viewTabla, filtroActivo);

            }

        }

    }

    public void limpiarCheckBox() {
        if (!viewTabla.getCheckBox().isSelected()) {
            filtroActivo = false;
        }
    }

    public void limpiarBtn() {
        viewTabla.getBtnCompletar().setVisible(false);
        viewTabla.getBtnEliminar().setVisible(false);
    }
}
