/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camion;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connection_Camion extends Thread{
     ServerSocket SgcrtrSocket;
    Socket SocketDashboard;
    ArrayList<String> list;
    Socket socket;
    

    public Connection_Camion(ServerSocket SgcrtrSocket, Socket SocketDashboard, ArrayList<String> list) {
        this.SgcrtrSocket = SgcrtrSocket;
        this.SocketDashboard = SocketDashboard;
        this.list = list;
    }
    
    

    @Override
    public void run() {
        int nbrCamion = 0;
        while (true) {
            
            try {
                socket = SgcrtrSocket.accept();
                Thread t = new Thread(new Accepter_Camion(socket, SocketDashboard,list,nbrCamion));
                t.start();
                AfficherMessage("Le camion numéro " + nbrCamion + " a démarré");
                nbrCamion++;
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
