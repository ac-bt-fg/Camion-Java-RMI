package camion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Camion {
  
    static Socket socket;
    int pause = 1500;
    ObjectOutputStream output;
    ObjectInputStream input;
    String numeroPlaque = "";
    String[] destinations = {"Dakar", "Thies", "Matam", "Ziguinchor", "Diourbel", 
        "Kaffrine", "Louga", "Kaolack", "Kedouguou", "Saint-Louis"};

    static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "abcdefghijklmnopqrstuvwxyz";
    static final String NUMBERS = "0123456789";
    static SecureRandom rnd = new SecureRandom();

    final String randomString() {
        StringBuilder sb = new StringBuilder(5);
        for (int i = 0; i < 2; i++) {
            sb.append(LETTERS.charAt(rnd.nextInt(LETTERS.length())));
        }
        for (int i = 0; i < 3; i++) {
            sb.append(NUMBERS.charAt(rnd.nextInt(NUMBERS.length())));
        }

        return sb.toString();

    }
    
    private Coordonnees coord;
    private Deplacement dep;

    public Coordonnees getCoord() {
        return coord;
    }

    public Deplacement getDep() {
        return dep;
    }

    public void setCoord(Coordonnees coord) {
        this.coord = coord;
    }

    public void setDep(Deplacement dep) {
        this.dep = dep;
    }
    
    Boolean ouvrir_communication() {
        try {
            socket = new Socket("localhost", 2009);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Camion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    void fermer_communication() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Camion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void afficher_donnees() {
        System.out.println("Camion: " + numeroPlaque + 
                "   Localisation: " + coord.getX() + "," + coord.getY() + "," 
                        + "   Vitesse: " + dep.getVitesse() + "   Direction: " 
                + dep.getDirection() + "   Destination: " + dep.getDestination());
    }
    
    String envoyer_caracteristiques(){
        return "Camion:" + numeroPlaque + ";X:" + coord.getX() + ";Y:" 
                + coord.getY()  + ";Vitesse: " + dep.getVitesse() 
                + ";Direction:" + dep.getDirection() + ";Destination: " 
                + dep.getDestination();
    }
    
    public Camion() {
        int x = (int) (10 + rnd.nextInt(NUMBERS.length()) % 8.55555);
        int y = (int) (-10 + rnd.nextInt(NUMBERS.length()) % -8.55555);

        int direction = (int) (rnd.nextInt(NUMBERS.length()) % 360);
        int vitesse = (int) (rnd.nextInt(NUMBERS.length()) % 100);
        
        int index = rnd.nextInt(destinations.length);
        String destination = destinations[index];
        numeroPlaque = randomString();
        coord = new Coordonnees(x, y);
        dep = new Deplacement(direction, vitesse, destination);
    }

    public void calcul_deplacement() throws IOException {
        float cosinus, sinus;
        float dep_x, dep_y;
        

        if (dep.getVitesse() < dep.VITMIN) {
            System.out.println("Le camion" + this.numeroPlaque + " est à l'arrêt\n");
        }
        
        cosinus = (float) cos(dep.getDirection() * 2 * Math.PI / 360);
        sinus = (float) sin(dep.getDirection() * 2 * Math.PI / 360);
        dep.setVitesse(dep.getVitesse() + 3);
        dep_x = cosinus * dep.getVitesse();
        dep_y = sinus * dep.getVitesse();

        // on se déplace d'au moins une case quel que soit la direction et la vitesse
        // sauf si la direction est de 90 degré (angle droit)
        if ((dep_x > 0) && (dep_x < 1)) {
            dep_x = 1;
            dep.setVitesse(dep.getVitesse() + 3);
            dep.setDirection(dep.getDirection() + 30);
        }
        
        if(dep.getVitesse() < 10){
            dep.setVitesse(dep.getVitesse() + 3);
        }
        if(dep.getVitesse() > 10 && dep.getVitesse() < 15){
            dep.setVitesse(dep.getVitesse() - 3);
        }
        
        if(dep.getVitesse() > 30 ){
            dep.setVitesse(dep.getVitesse() - 8);
        }
        
        if(dep.getVitesse() < 1 && dep.getVitesse() > 5){
            dep.setVitesse(dep.getVitesse() + 10);
            dep.setDirection(dep.getDirection() - 5);
        }
        
        if(dep.getVitesse() == 10 || dep.getVitesse() == 9 
                || dep.getVitesse() == 8 || dep.getVitesse() == 11 
                || dep.getVitesse() == 30 ){
            dep.setVitesse(dep.getVitesse() + 7);
            dep.setDirection(dep.getDirection() - 3);
        }
        
        if(dep.getVitesse() > 40){
            dep.setVitesse(dep.getVitesse() + 17);
            dep.setDirection(dep.getDirection() - 10);
        }
        
        if(dep.getVitesse() == 100){
            dep.setVitesse(dep.getVitesse() - 47);
            dep.setDirection(dep.getDirection() + 30);
        }
        if(dep.getVitesse() == 32){
            dep.setVitesse(dep.getVitesse() + 27);
            dep.setDirection(dep.getDirection() - 30);
        }
        
        if ((dep_x < 0) && (dep_x > -1)) {
            dep_x = -150;
            dep.setVitesse(dep.getVitesse() -1);
            dep.setDirection(dep.getDirection() - 30);
        }

        if ((dep_y > 0) && (dep_y < 1)) {
            dep_y = 1;
            dep.setVitesse(dep.getVitesse() + 3);
            dep.setDirection(dep.getDirection() + 30);
        }
        if ((dep_y < 0) && (dep_y > -1)) {
            dep_y = -150;
            dep.setVitesse(dep.getVitesse() - 1);
            dep.setDirection(dep.getDirection() - 30);
        }
        
        if(dep.getDirection() < 1){
            dep.setDirection(5);
        }
        
        if(dep.getDirection() == 5){
            dep.setDirection(20);
        }
        
        if(dep.getDirection() > 360){
            dep.setDirection(120);
        }
        
        coord.setX(coord.getX() + (int) dep_x);
        coord.setY(coord.getY() + (int) dep_y);
    }

    public void se_deplacer() throws IOException, InterruptedException {
        for (int i = 0; i < 100; i++) {
            sleep(pause);
            calcul_deplacement();
            afficher_donnees();
        }
    }
    
    public static void main(String[] args) throws InterruptedException {

        PrintWriter out;

        try {

            Camion camion = new Camion();
            if (camion.ouvrir_communication() == true) {

                String caracteristiques = camion.envoyer_caracteristiques();

                out = new PrintWriter(Camion.socket.getOutputStream());
                out.println(caracteristiques);
                while (true) {
                    sleep(camion.pause);
                   
                    camion.calcul_deplacement();
                    caracteristiques = camion.envoyer_caracteristiques();
                    out.println(caracteristiques);
                    out.flush();
                }
            }

        } catch (IOException | InterruptedException ex) {
            System.out.println("Fin Camion");
        }

    }
}