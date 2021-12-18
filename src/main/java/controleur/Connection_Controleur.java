
package controleur;

import camion.Accepter_Camion;
import camion.Connection_Camion;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Connection_Controleur extends Thread{
    ServerSocket SgcrtrSocket;
    ArrayList<String> list;
    Socket socket;

    public Connection_Controleur(ServerSocket SgcrtrSocket, ArrayList<String> list) {
        this.SgcrtrSocket = SgcrtrSocket;      
        this.list = list;
    }

    @Override
    public void run() {
        int nbrController = 1;
        while (true) {

            
            try {
                
                System.out.println(list.toString());
                
                socket = SgcrtrSocket.accept();
                Thread t = new Thread(new Accepter_Controleur(socket,list,nbrController));
                t.start();
                AfficherMessage("Le contrôleur numéro " + nbrController + " a démarré");
                nbrController++;

            } catch (IOException ex) {
                
                try {
                socket.close();
                System.out.println("Fin");
                
            } catch (IOException ex2) {
                Logger.getLogger(Accepter_Camion.class.getName()).log(Level.SEVERE, null, ex2);
            } 
                
                Logger.getLogger(Connection_Camion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void AfficherMessage(String msg) {
        System.out.println(msg);        
    }   
}
