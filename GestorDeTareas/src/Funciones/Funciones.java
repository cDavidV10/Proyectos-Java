/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Funciones;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Vista.VistaPrincipal;
import Vista.VistaTabla;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cdavi
 */
public class Funciones {

    private DefaultTableModel tabla = new DefaultTableModel();
    ArrayList<Tareas> tareas = new ArrayList<>();
    GuardarFichero fichero = new GuardarFichero();
    public int paginaActual = 0, paginaTotal = 0, datosMostrados = 5;

    public void definirTabla(VistaTabla interfaz) {

        String[] columnas = { "Codigo", "Tarea", "Estado" };

        tabla.setColumnIdentifiers(columnas);
        interfaz.getTablaTareas().setModel(tabla);

    }

    public void cargarDatos(VistaTabla interfaz, boolean filtro) {

        if (!filtro) {
            tareas = fichero.getInfo();
        } else {
            Filtro();
            if (tareas.size() == 0) {
                JOptionPane.showMessageDialog(interfaz, "Sin tareas Completadas");
                tareas = fichero.getInfo();
                interfaz.getCheckBox().setSelected(false);
            }
        }

        tabla.setRowCount(0);
        int inicio = paginaActual * datosMostrados;
        int fin = inicio + datosMostrados;

        if (fin > tareas.size()) {
            fin = tareas.size();
        }
        interfaz.setPaginaFinal(String.valueOf(paginasTotales()));
        interfaz.setPaginaActual(String.valueOf(paginaActual + 1));

        ocultarBtn(interfaz);

        for (int i = inicio; i < fin; i++) {
            Tareas dato = tareas.get(i);

            Object[] datos = {
                    dato.getId(),
                    dato.getInfo(),
                    dato.getEstado()

            };

            tabla.addRow(datos);

        }
    }

    public void Filtro() {
        ArrayList<Tareas> aux = new ArrayList<>();
        aux.addAll(tareas);
        tareas.clear();
        for (Tareas dato : aux) {
            if (dato.getEstado().equals("Completada")) {

                tareas.add(dato);
            }
        }

    }

    public String eliminarEspacios(String txt) {
        String[] cadenas = txt.split("\\s+");

        String textoFinal = String.join(" ", cadenas);

        return textoFinal;

    }

    public String generarID(int cantidad) {
        String correlativo = String.valueOf(cantidad);
        if (cantidad < 10) {
            return "Tarea-00" + correlativo;
        }

        if (cantidad >= 10 && cantidad < 100) {
            return "Tarea-0" + cantidad;
        }
        return "Tarea-" + cantidad;
    }

    public void ocultarBtn(VistaTabla interfaz) {
        if ((paginaActual + 1) == 1) {
            interfaz.getBtnAnterior().setVisible(false);
        } else {

            interfaz.getBtnAnterior().setVisible(true);
        }

        if ((paginaActual + 1) >= paginasTotales()) {
            interfaz.getBtnSiguiente().setVisible(false);
        } else {
            interfaz.getBtnSiguiente().setVisible(true);

        }
    }

    public void setPaginaActual(int pagina) {
        paginaActual = pagina - 1;
    }

    public int paginasTotales() {

        if (sizeData() % 5 == 0) {

            return paginaTotal = sizeData() / datosMostrados;
        }

        return paginaTotal = (sizeData() / datosMostrados) + 1;
    }

    public int sizeData() {

        return tareas.size();
    }

    public ArrayList<Tareas> getDatosTabla() {
        return tareas;
    }

    public void setList(ArrayList<Tareas> datosActualozados) {

        tareas = datosActualozados;
    }

}
