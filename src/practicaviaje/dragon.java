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
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class dragon extends Thread {

   
       
    
    
     private volatile boolean running = true;
    Modulo ventana;
    Rectangle rec_tope, rec_dragon;
    int posicionX;

    public dragon(Modulo modulo2, int X) {
        
        this.ventana=modulo2;
        
        this.posicionX = X;
        this.rec_dragon = this.ventana.carro1.getBounds();
        this.rec_tope = this.ventana.topestop.getBounds();
    }   
    public void run() {
        
        try{
        while (running) {
            //sleep para controlar la velocidad de la imagen
            sleep(150);//numero en milisegundos
              if (this.rec_tope.intersects(this.rec_dragon)) {
                    JOptionPane.showMessageDialog(null, "Fin del viaje ");
                   stopThead();
                } else {
                    
                   this.posicionX+=20;
           
            
            this.ventana.carro1.setLocation(this.posicionX, this.ventana.carro1.getY());
        this.rec_dragon=this.ventana.carro1.getBounds();
          this.ventana.repaint();
                }
           System.out.println("Carro1 aumenta"+ this.posicionX);
        }
        } catch (InterruptedException ex){
            Logger.getLogger(dragon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void stopThead() {
        this.running = false;
    }
}
