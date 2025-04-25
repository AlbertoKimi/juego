package com.enriquealberto.Lectores;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.enriquealberto.model.Mapa;

public class LectorMapa {
    public static ArrayList<Mapa> leerMapas() {
        ArrayList<Mapa> mapas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                LectorMapa.class.getResourceAsStream("/com/enriquealberto/archivos/escenarios.quive")))) {
            String linea;
            String nombreEscenario = null;
            ArrayList<ArrayList<Integer>> matriz = null;

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();

                // Detectar cabecera
                if (linea.startsWith("#")) {
                    if (nombreEscenario != null && matriz != null) {
                        // Guardar el escenario en el ArrayList
                        Mapa mapa = new Mapa();
                        mapa.setNombre(nombreEscenario);
                        mapa.setMapa(matriz);
                        mapas.add(mapa);
                    }

                    // Procesar nueva cabecera
                    String[] partes = linea.split(":");
                    if (partes.length < 2) {
                        continue; // Saltar líneas mal formadas
                    }
                    nombreEscenario = partes[1].trim();
                    matriz = new ArrayList<>();
                } else if (!linea.isEmpty() && matriz != null) {
                    // Procesar líneas de la matriz
                    ArrayList<Integer> filaNumeros = new ArrayList<>();
                    for (char c : linea.toCharArray()) {
                        filaNumeros.add(Character.getNumericValue(c));
                    }
                    matriz.add(filaNumeros);
                }
            }

            // Guardar el último escenario procesado
            if (nombreEscenario != null && matriz != null) {
                Mapa mapa = new Mapa();
                mapa.setNombre(nombreEscenario);
                mapa.setMapa(matriz);
                mapas.add(mapa);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        return mapas;
    }

    // Método de prueba para verificar la lectura de mapas
    /*public static void main(String[] args) {
        ArrayList<Mapa> mapas = LectorMapa.leerMapas();
        for (Mapa mapa : mapas) {
            System.out.println("Nombre del escenario: " + mapa.getNombre());
            System.out.println("Matriz:");
            for (ArrayList<Integer> fila : mapa.getMapa()) {
                System.out.println(fila);
            }
            System.out.println("-------------------------");
        }
    }*/
}
