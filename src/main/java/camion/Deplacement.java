package camion;

public class Deplacement {

    String destination;
    int direction;
    int vitesse;
    int VITMIN = 1;
    int VITMAX = 100;
    
    public Deplacement(int direction, int vitesse, String destination) {
        this.direction = direction;
        this.vitesse = vitesse;
        this.destination = destination;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        if((direction >= 0) && (direction < 360)){
            this.direction = direction;
        }
        else{
            this.direction = direction - 360;
        }
    }

    public int getVitesse() {
        return vitesse;
    }

    public void setVitesse(int vitesse) {
        if (vitesse < 0)
            this.vitesse = 0;
        else if (vitesse > VITMAX)
            this.vitesse = VITMAX;
        else
            this.vitesse = vitesse;
    }

}
