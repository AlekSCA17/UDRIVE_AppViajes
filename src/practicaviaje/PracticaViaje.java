/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaviaje;

/**
 *
 * @author Alexander
 */
import java.io.File; // Proporciona la clase File que representa un archivo o directorio en el sistema de archivos.
import java.io.FileInputStream; // Proporciona la clase FileInputStream que se utiliza para leer datos de un archivo como una secuencia de bytes.
import java.io.FileNotFoundException; // Proporciona la excepción FileNotFoundException que se lanza cuando no se encuentra un archivo
import java.io.FileOutputStream; // Proporciona la clase FileOutputStream que se utiliza para escribir datos en un archivo como una secuencia de bytes.
import java.io.IOException; // Proporciona la excepción IOException que se lanza cuando ocurre un error durante la lectura o escritura de datos.
import java.io.ObjectInputStream; // Proporciona la clase ObjectInputStream que se utiliza para deserializar objetos desde un flujo de entrada.
import java.io.ObjectOutputStream; // Proporciona la clase ObjectOutputStream que se utiliza para serializar objetos a un flujo de salida.
import java.io.PrintWriter; // Proporciona la clase PrintWriter que se utiliza para escribir texto formateado a un flujo de salida.
import java.util.Scanner;
import java.util.ArrayList;


public class PracticaViaje {

    public static ArrayList<viaje> viajar = new ArrayList<viaje>();
    public static ArrayList<String> inicioList = new ArrayList<>();
    public static ArrayList<String> finList = new ArrayList<>();
    private static int counterRoutes = 0;
        private static int counterTrips = 0;
        
    
        
    public static void main(String[] args) {
       Modulo ventana= new Modulo();
        ventana.setVisible(true);
    }

public static int generateId(String option) {
        int id = 0;
        switch (option) {
            case "route":
                id = counterRoutes + 1;
                counterRoutes++;
                break;
            case "trip":
                id = counterTrips + 1;
                counterTrips++;
                break;
        }

        return id;
    }
}
