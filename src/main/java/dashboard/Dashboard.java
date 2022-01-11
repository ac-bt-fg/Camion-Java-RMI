/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboard;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import JFrames.DashboardJFrame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Dashboard {

    public static Socket socket = null;
    public static String message = "";
    static String Nom_camion="";
    static String X="";
    static String Y="";
    static String Vitesse="";
    static String Direction="";
    static String Destination="";
    static int num_camion;
           
      
    public static void main(String[] args) {

        try {

            System.out.println("Demande de connexion du tableau de bord");
            socket = new Socket("192.168.43.92", 2010);
            BufferedReader inFromSGCRTR = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            DashboardJFrame rf = new  DashboardJFrame();
            rf.setVisible(true);
        
            while (true) {

                message = inFromSGCRTR.readLine();
                String[] split = message.split(";");    
                    Nom_camion = split[0].split(":")[1];
                    X = split[1].split(":")[1];
                    Y = split[2].split(":")[1];
                    Vitesse = split[3].split(":")[1];
                    Direction = split[4].split(":")[1];
                    Destination = split[5].split(":")[1];
                    num_camion = Integer.parseInt(split[6].split(":")[1]);
                    
                    rf.UpdateDashboard(Nom_camion, X, Y, Vitesse, Direction, Destination, num_camion);
            }

        } catch (IOException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
