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
import com.enriquealberto.model.Heroe;

/**
 * Clase utilitaria para leer héroes desde un archivo CSV.
 * Procesa el archivo y crea objetos Heroe que son almacenados en una lista.
 */
public class LectorHeroes {

    /**
     * Lee y parsea un archivo CSV con información de héroes.
     * El archivo debe estar ubicado en: /com/enriquealberto/archivos/heroes.csv
     *
     * @return ArrayList de objetos Heroe cargados desde el archivo
     * @throws RuntimeException Si ocurre un error al leer el archivo
     */
    public static ArrayList<Heroe> leerHeroes() {
        ArrayList<Heroe> heroes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(
                                new File(App.class.getResource("/com/enriquealberto/archivos/heroes.csv").toURI())),
                        StandardCharsets.UTF_8))) {

            // Saltar la cabecera del CSV
            String linea = br.readLine();

            // Procesar cada línea del archivo
            while ((linea = br.readLine()) != null) {
                String[] atributos = linea.split(",");
                try {
                    // Crear nuevo héroe y añadirlo a la lista
                    heroes.add(new Heroe(
                            atributos[0],    // nombre
                            atributos[1],    // imagen
                            Integer.parseInt(atributos[2]),  // vida
                            Integer.parseInt(atributos[3]),  // ataque
                            Integer.parseInt(atributos[4]),  // defensa
                            Integer.parseInt(atributos[5]),  // velocidad
                            Integer.parseInt(atributos[6]))); // porcentaje
                } catch (NumberFormatException e) {
                    System.err.println("Error al parsear los atributos de la línea: " + linea);
                    e.printStackTrace();
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Línea mal formada, faltan atributos: " + linea);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Error de E/S al leer el archivo de héroes");
            e.printStackTrace();
            throw new RuntimeException("Error al leer el archivo de héroes", e);
        } catch (URISyntaxException e) {
            System.err.println("Error en la URI del recurso");
            e.printStackTrace();
            throw new RuntimeException("Error en la ubicación del archivo de héroes", e);
        }

        return heroes;
    }
}