/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Funciones;

import java.awt.List;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author cdavi
 */
public class GuardarFichero {

    public File fichero = new File("src/Fichero/fichero.csv");
    ArrayList<Tareas> tareas = new ArrayList<>();

    public void llenarArray() {
        tareas.clear();
        leerDatos();

    }

    public void llenarDatos(Tareas info) {

        llenarArray();
        tareas.add(info);

        guardarDatos();
    }

    public void guardarDatos() {

        try (FileWriter escritura = new FileWriter(fichero)) {

            for (Tareas tarea : tareas) {
                escritura.write(tarea.toCsv());
            }
        } catch (Exception e) {
            System.out.println("Problema al abrir archivo");
        }

    }

    public void leerDatos() {
        Tareas datoTarea;
        String[] dato;

        try (Scanner scFile = new Scanner(fichero)) {
            while (scFile.hasNextLine()) {
                dato = scFile.nextLine().split(",");
                datoTarea = new Tareas(dato[0], dato[1], dato[2]);
                tareas.add(datoTarea);
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public int getSize() {
        llenarArray();
        return tareas.size();
    }

    public ArrayList<Tareas> getList() {
        llenarArray();
        return tareas;
    }

    public void setList(ArrayList<Tareas> datosActualizados) {
        ArrayList<Tareas> aux = new ArrayList<>();
        aux.addAll(datosActualizados);
        tareas.clear();
        tareas.addAll(aux);
        guardarDatos();
    }

    public ArrayList<Tareas> getInfo() {
        llenarArray();
        return tareas;
    }
}
