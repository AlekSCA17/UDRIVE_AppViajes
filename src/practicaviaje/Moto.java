
package practicaviaje;

/**
 *
 * @author Alexander
 */

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

//Palabra extendes para poder aplicar hilos
public class Moto extends Thread {
    
     private volatile boolean running = true;
    Modulo ventana;
    Rectangle rec_tope, rec_moto;
    int posicionX;

    public Moto(Modulo modulo2, int X) {
        
        this.ventana=modulo2;
        
        this.posicionX = X;
        this.rec_moto = this.ventana.moto.getBounds();
        this.rec_tope = this.ventana.topestop.getBounds();
    }
    
    
    
    public void run() {
        
        try{
        while (running) {
            //sleep para controlar la velocidad de la imagen
            sleep(150);//numero en milisegundos
              if (this.rec_tope.intersects(this.rec_moto)) {
                    JOptionPane.showMessageDialog(null, "Fin del viaje ");
                   stopThead();
                } else {
                    
                   this.posicionX+=20;
           
            
            this.ventana.moto.setLocation(this.posicionX, this.ventana.moto.getY());
        this.rec_moto=this.ventana.moto.getBounds();
          this.ventana.repaint();
                }
           System.out.println("Moto aumenta"+ this.posicionX);
        }
        } catch (InterruptedException ex){
            Logger.getLogger(Moto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void stopThead() {
        this.running = false;
    }
}
