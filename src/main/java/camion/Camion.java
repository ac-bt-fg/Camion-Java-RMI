package camion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Camion {

    Coordonnees m = new Coordonnees();
    Deplacement d = new Deplacement();
    InetAddress adr = null;
    Socket s = null;
    // Interface inter;
    ObjectOutputStream output;
    ObjectInputStream input;
    String numeroPlaque;
    String etat;

    public void initialiser_camion() {

    }

    public void afficher_donnees() {

    }

    public void changer_vitesse() {

    }

    public void changer_direction() {

    }

    public void calcul_deplacement() throws IOException {
    }

    public void se_deplacer() throws IOException {
    }

    public void connexion() throws IOException, ClassNotFoundException {
        int x = 1;
        // se_deplacer();
        InetAddress adr = InetAddress.getByName("Alhamdoulilah");
        s = new Socket(adr, 9632);
        // initialiser_avion();
        output = new ObjectOutputStream(s.getOutputStream());
        input = new ObjectInputStream(s.getInputStream());
        // se_deplacer();
        // output.writeObject(new String(afficher_donnees()));
        String ch = (String) input.readObject();
        // ses_deplace();

    }

    /*
     * public Camion ( Interface inter) throws IOException{
     * this.inter=inter;
     * }
     */

}