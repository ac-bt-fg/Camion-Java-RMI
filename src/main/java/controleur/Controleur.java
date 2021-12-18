
package controleur;


import JFrames.ControleurJFrame;
import camion.Camion;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static dashboard.Dashboard.message;



public class Controleur {

    public static Socket socket = null;

    Boolean ouvrir_communication() {

        try {
            socket = new Socket("localhost", 2011);
            return true;
            
        } catch (IOException ex) {
            Logger.getLogger(Camion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

   
    public static void main(String[] args) throws InterruptedException {

        PrintWriter out;

        try {

            Controleur cont = new Controleur();
            
            ControleurJFrame cjf =  new ControleurJFrame();
            cjf.setVisible(true);
            
            if (cont.ouvrir_communication() == true) {
                while (true) {
                    BufferedReader inFromSGCRTR = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    message = inFromSGCRTR.readLine();
                    String[] split = message.split("-");
                    cjf.UpdateTitle(split[0]);
                    cjf.Updatebody(split[1]);
                    
                    
                }
            }
            
        } catch (IOException ex) {
           System.out.println("Fin Controleur");
        }
    }
}

