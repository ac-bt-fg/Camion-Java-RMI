
package console;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import camion.Connection_Camion;
import controleur.Connection_Controleur;


public class SGCRTR {
    public static ArrayList<String> list;

    public static void main(String[] zero) {

        ServerSocket serversocketCamion;
        ServerSocket serverSocketControleur;
        ServerSocket serversocketDashboard;

        try {

            list = new ArrayList<>();

            serversocketCamion = new ServerSocket(2009);
            serversocketDashboard = new ServerSocket(2010);
            serverSocketControleur = new ServerSocket(2011);
            
            Socket socketDashboard = serversocketDashboard.accept();

            System.out.println("Le SGCRTR est prêt!");

            Connection_Camion cA = new Connection_Camion(serversocketCamion, socketDashboard,list);
            cA.start();
            
            Connection_Controleur cC = new Connection_Controleur(serverSocketControleur,list);
            cC.start();
        } catch (IOException e) {
            System.out.println("Fin du SGCRTR");
        }
    }
}
