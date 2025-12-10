/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Funciones;

import javax.swing.table.DefaultTableModel;
import Vista.VistaTabla;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cdavi
 */
public class Funciones {

    private DefaultTableModel tabla = new DefaultTableModel();
    List<Tareas> tareas = new ArrayList<>();
    GuardarFichero fichero = new GuardarFichero();
    public int paginaActual = 0, paginaTotal = 0, datosMostrados = 5;

    public void definirTabla(VistaTabla interfaz) {

        String[] columnas = { "Codigo", "Tarea", "Estado" };

        tabla.setColumnIdentifiers(columnas);
        interfaz.getTablaTareas().setModel(tabla);

    }

    public void cargarDatos(VistaTabla interfaz) {

        tareas = fichero.getInfo();
        tabla.setRowCount(0);
        int inicio = paginaActual * datosMostrados;
        int fin = inicio + datosMostrados;

        if (fin > tareas.size()) {
            fin = tareas.size();
        }

        interfaz.setPaginaFinal(String.valueOf(paginasTotales()));
        interfaz.setPaginaActual(String.valueOf(paginaActual + 1));

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

    public void setPaginaActual(int pagina) {

        paginaActual = pagina;
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

    public void setList(List<Tareas> datosActualozados) {

        tareas = datosActualozados;
    }

}
