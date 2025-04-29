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
            String suelo = null;
            String pared = null;
            ArrayList<ArrayList<Integer>> matriz = null;

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();

                // Cabecera
                if (linea.startsWith("#")) {
                    if (linea.startsWith("#suelo:")) {
                        suelo = linea.split(":", 2)[1].trim();
                    } else if (linea.startsWith("#pared:")) {
                        pared = linea.split(":", 2)[1].trim();
                    } else if (linea.startsWith("#escenario")) {
                        if (nombreEscenario != null && matriz != null) {
                            // Guardar el escenario
                            Mapa mapa = new Mapa();
                            mapa.setNombre(nombreEscenario);
                            mapa.setMapa(matriz);
                            mapa.setSuelo(suelo);
                            mapa.setPared(pared);
                            mapas.add(mapa);
                        }

                        // Nueva cabecera
                        String[] partes = linea.split(":");
                        if (partes.length < 2) {
                            continue; // Saltar líneas mal formadas
                        }
                        nombreEscenario = partes[1].trim();
                        matriz = new ArrayList<>();
                        suelo = null; 
                        pared = null; 
                    }
                } else if (!linea.isEmpty() && matriz != null) {
                    // Procesar líneas de la matriz
                    ArrayList<Integer> filaNumeros = new ArrayList<>();
                    for (char c : linea.toCharArray()) {
                        filaNumeros.add(Character.getNumericValue(c));
                    }
                    matriz.add(filaNumeros);
                }
            }

            // Guardar mapa
            if (nombreEscenario != null && matriz != null) {
                Mapa mapa = new Mapa();
                mapa.setNombre(nombreEscenario);
                mapa.setMapa(matriz);
                mapa.setSuelo(suelo);
                mapa.setPared(pared);
                mapas.add(mapa);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        return mapas;
    }
}
