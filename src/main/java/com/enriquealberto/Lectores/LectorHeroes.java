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

public class LectorHeroes {
    //en esta clase se leen los heroes
    //se guardan en un arraylist y se devuelven

    public static ArrayList<Heroe> leerHeroes(){
        ArrayList<Heroe> heroes = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(App.class.getResource("/com/enriquealberto/archivos/heroes.csv").toURI())),StandardCharsets.UTF_8))) {
            String linea = br.readLine();
            while ((linea = br.readLine())!=null){
                String[] atributos = linea.split(",");
                try {
                    heroes.add(new Heroe(atributos[0],atributos[1], Integer.parseInt(atributos[2]), Integer.parseInt(atributos[3]),Integer.parseInt(atributos[4]), Integer.parseInt(atributos[5]),Integer.parseInt(atributos[6])));
                } catch (NumberFormatException e) {
                    System.err.println("Error al parsear los atributos de la línea: " + linea);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e){
            e.printStackTrace();
        }
        
        return heroes;
    }
}
