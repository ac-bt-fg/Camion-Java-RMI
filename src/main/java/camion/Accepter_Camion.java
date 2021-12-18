
package camion;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


public class Accepter_Camion implements Runnable{
    BufferedReader inFromCamion;
    private final Socket socket;
    private final Socket socketDashboard;
    private final int nbrCamion ;
    private PrintWriter outToRadar = null;
    ArrayList<String> list;

    public Accepter_Camion(Socket socket, Socket socketDashboard, ArrayList<String> list,int nbrCamion) {
        this.socket = socket;
        this.socketDashboard = socketDashboard;
        this.list = list;
        this.nbrCamion = nbrCamion;
    }

    @Override
    public void run() {

        try {
            
            while (true) {

                inFromCamion = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message_From_Camion = inFromCamion.readLine();
                

                String nom_camion = message_From_Camion.substring(7, 12);

                if (!list.contains(nom_camion.trim())) {                
                    list.add(nom_camion.trim());
                }

                outToRadar = new PrintWriter(socketDashboard.getOutputStream());
                message_From_Camion = message_From_Camion + ";Numéro du camion:" +nbrCamion;
                outToRadar.println(message_From_Camion);
                outToRadar.flush();
            }

        } catch (IOException e) {

            System.out.println("Fin accepter camion");
        }
    }
}
