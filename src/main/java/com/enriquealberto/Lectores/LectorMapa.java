package com.enriquealberto.Lectores;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.enriquealberto.model.Mapa;

/**
 * Clase utilitaria para leer mapas desde un archivo de texto con formato específico.
 * El formato del archivo debe contener definiciones de escenarios con sus propiedades
 * y matrices que representan el diseño del mapa.
 */
public class LectorMapa {
    // Constantes para identificar las etiquetas del archivo
    private static final String PARED = "#pared:";
    private static final String SUELO = "#suelo:";
    private static final String TAMANO_X = "#tamanoX:";
    private static final String TAMANO_Y = "#tamanoY:";
    private static final String ESCENARIO = "#escenario";
    private static final String NIVEL = "#nivel";

    /**
     * Lee y parsea un archivo que contiene la definición de múltiples mapas/escenarios.
     *
     * @return ArrayList de objetos Mapa cargados desde el archivo
     */
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
            int nivel = 0;
            int lineaMapa = 0;
            boolean matrizCompleta = false;
            int[][] matriz = null;

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();

                // Procesamiento de metadatos del mapa
                if (linea.startsWith("#")) {
                    if (linea.startsWith(SUELO)) {
                        suelo = extraerValor(linea);
                    } else if (linea.startsWith(PARED)) {
                        pared = extraerValor(linea);
                    } else if (linea.startsWith(TAMANO_X)) {
                        x = Integer.parseInt(extraerValor(linea));
                    } else if (linea.startsWith(TAMANO_Y)) {
                        y = Integer.parseInt(extraerValor(linea));
                        matriz = new int[y][x]; // Inicializa matriz con las dimensiones leídas
                    } else if (linea.startsWith(ESCENARIO)) {
                        nombreEscenario = extraerValor(linea);
                    } else if (linea.startsWith(NIVEL)) {
                        nivel = Integer.parseInt(extraerValor(linea));
                    }
                }
                // Procesamiento de la matriz del mapa
                else if (!linea.isEmpty()) {
                    if (matriz == null) {
                        System.err.println("Advertencia: Dimensiones del mapa no definidas antes de los datos");
                        continue;
                    }

                    if (linea.length() >= x) {
                        for (int i = 0; i < x; i++) {
                            matriz[lineaMapa][i] = Character.getNumericValue(linea.charAt(i));
                        }
                        lineaMapa++;

                        // Verifica si se completó la matriz
                        if (lineaMapa == y) {
                            matrizCompleta = true;
                        }
                    } else {
                        System.err.println("Advertencia: Línea demasiado corta, se esperaban " + x +
                                " caracteres: " + linea);
                    }
                }

                // Cuando se completa un mapa, lo añade a la lista
                if (matrizCompleta) {
                    Mapa mapa = new Mapa(nivel, nombreEscenario, suelo, pared, matriz);
                    mapas.add(mapa);

                    // Reinicia variables para el próximo mapa
                    lineaMapa = 0;
                    matriz = null;
                    matrizCompleta = false;
                    nombreEscenario = null;
                    suelo = null;
                    pared = null;
                    x = 0;
                    y = 0;
                    nivel = 0;
                }
            }

        } catch (IOException e) {
            System.err.println("Error de E/S al leer el archivo: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir valor numérico: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }

        return mapas;
    }

    /**
     * Extrae el valor de una línea con formato "clave:valor".
     *
     * @param linea Línea de texto a procesar
     * @return El valor después de los dos puntos, o cadena vacía si no hay valor
     */
    private static String extraerValor(String linea) {
        String[] partes = linea.split(":");
        if (partes.length > 1) {
            return partes[1].trim();
        } else {
            System.err.println("Advertencia: Formato incorrecto en la línea: " + linea);
            return "";
        }
    }
}
