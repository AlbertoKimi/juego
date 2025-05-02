package com.enriquealberto.Lectores;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.enriquealberto.model.Mapa;

public class LectorMapa {
    private static final String PARED = "#pared:";
    private static final String SUELO = "#suelo:";

    public static ArrayList<Mapa> leerMapas() {
        ArrayList<Mapa> mapas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                LectorMapa.class.getResourceAsStream("/com/enriquealberto/archivos/escenarios.quive")))) {
            String linea;
            String nombreEscenario = null;
            String suelo = null;
            String pared = null;
            int x = 0;
            int y = 0;
            int lineaMapa = 0;
            boolean matrizCompleta = false;
            int[][] matriz = null; // Inicializar matriz como un array vacío

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();

                // Cabecera
                if (linea.startsWith("#")) {
                    if (linea.startsWith(SUELO)) {
                        suelo = extraerValor(linea);
                    } else if (linea.startsWith(PARED)) {
                        pared = extraerValor(linea);
                    } else if (linea.startsWith("#tamanoX:")) {
                        x = Integer.parseInt(extraerValor(linea));
                    } else if (linea.startsWith("#tamanoY:")) {
                        y = Integer.parseInt(extraerValor(linea));
                        matriz = new int[y][x];

                    } else if (linea.startsWith("#escenario")) {
                        nombreEscenario = extraerValor(linea);

                    }
                } else if (!linea.isEmpty()) {
                    for (int i = 0; i < y; i++) {
                        matriz[lineaMapa][i] = Character.getNumericValue(linea.charAt(i));
                    }
                    lineaMapa++;
                    if (lineaMapa == y) {
                        matrizCompleta = true;
                    }
                }
                // Guardar mapa
                if (matrizCompleta) {
                    Mapa mapa = new Mapa(nombreEscenario, suelo, pared, matriz);
                    mapas.add(mapa);
                    lineaMapa = 0; 
                    matriz = null; 
                    matrizCompleta = false;
                }
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        return mapas;
    }

    private static String extraerValor(String linea) {
        return linea.split(":")[1].trim();
    }
}
