package com.enriquealberto.Lectores;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.enriquealberto.App;
import com.enriquealberto.model.Enemigo;

/**
 * Clase utilitaria para leer enemigos/monstruos desde un archivo CSV.
 * Procesa el archivo y crea objetos Enemigo que son almacenados en una lista.
 */
public class LectorMostruo {

    /**
     * Lee y parsea un archivo CSV con información de monstruos/enemigos.
     * El archivo debe estar ubicado en: /com/enriquealberto/archivos/monstruos.csv
     *
     * @return ArrayList de objetos Enemigo cargados desde el archivo
     * @throws RuntimeException Si ocurre un error al leer el archivo
     */
    public static ArrayList<Enemigo> leerMostruo() {
        ArrayList<Enemigo> enemigos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(
                                new File(App.class.getResource("/com/enriquealberto/archivos/monstruos.csv").toURI())),
                        StandardCharsets.UTF_8))) {

            // Saltar la cabecera del CSV
            String linea = br.readLine();

            // Procesar cada línea del archivo
            while ((linea = br.readLine()) != null) {
                String[] atributos = linea.split(",");
                try {
                    // Validar que la línea tenga suficientes atributos
                    if (atributos.length < 9) {
                        System.err.println("Línea incompleta, se esperaban 9 atributos: " + linea);
                        continue;
                    }

                    // Crear nuevo enemigo y añadirlo a la lista
                    enemigos.add(new Enemigo(
                            atributos[0],    // nombre
                            atributos[1],   // imagen
                            Integer.parseInt(atributos[2]),  // vida
                            Integer.parseInt(atributos[3]),  // ataque
                            Integer.parseInt(atributos[4]),  // defensa
                            Integer.parseInt(atributos[5]),  // velocidad
                            Integer.parseInt(atributos[6]),  // porcentaje
                            Integer.parseInt(atributos[7]),  // tipo de enemigo (1-3)
                            Integer.parseInt(atributos[8]))); // percepción
                } catch (NumberFormatException e) {
                    System.err.println("Error al convertir atributos numéricos en la línea: " + linea);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Error de E/S al leer el archivo de monstruos");
            e.printStackTrace();
            throw new RuntimeException("Error al leer el archivo de monstruos", e);
        } catch (URISyntaxException e) {
            System.err.println("Error en la URI del recurso");
            e.printStackTrace();
            throw new RuntimeException("Error en la ubicación del archivo de monstruos", e);
        } catch (Exception e) {
            System.err.println("Error inesperado al leer monstruos");
            e.printStackTrace();
            throw new RuntimeException("Error inesperado", e);
        }

        return enemigos;
    }
}