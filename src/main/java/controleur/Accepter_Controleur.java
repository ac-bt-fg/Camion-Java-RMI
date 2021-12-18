
package controleur;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Accepter_Controleur implements Runnable{
    
    Socket SgcrtrSocket;
    ArrayList<String> list;
    int num;
    private PrintWriter outToDashboard = null;

    public Accepter_Controleur(Socket SgcrtrSocket, ArrayList<String> list, int num) {
        this.SgcrtrSocket = SgcrtrSocket;
        this.list = list;
        this.num = num;
    }

    @Override
    public void run() {

        while (true) {
            try {
                outToDashboard = new PrintWriter(SgcrtrSocket.getOutputStream());
                outToDashboard.println("Contrôleur: " + num + "  -  " + list.toString()); 
                outToDashboard.flush();
                Thread.sleep(2000);
                
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(Accepter_Controleur.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    
}
