#include <math.h>
#include <time.h>
#include "camion.h"
#include <string.h>


// caractéristiques du déplacement du camion
struct deplacement dep;

// coordonnées du camion
struct coordonnees coord;

// numéro de la plaque d'immatriculation du camion
char numero_plaque[6];


char etat[] = "";


int ouvrir_communication()
{
  // fonction à implémenter qui permet d'entrer en communication via TCP avec le contrôleur
    return 1;
}


void fermer_communication()
{
  // fonction à implémenter qui permet de fermer la communication
  // avec le gestionnaire de vols
}

void envoyer_caracteristiques()
{
  // fonction � impl�menter qui envoie l'ensemble des caract�ristiques
  // courantes de l'avion au gestionnaire de vols
}



// initialise aléatoirement les paramétres du camion
void initialiser_camion()
{
  // initialisation aléatoire du compteur aléatoire
    int seed;
    time(&seed);
    srandom(seed);

  // intialisation des paramètres de l'avion
    coord.x = 1000 + random() % 1000;
    coord.y = 1000 + random() % 1000;
    char orientation[12][10] = {"Dakar", "Matam", "Thies", "Louga", "Saint-Louis", "Ziguinchor", "Kolda", "Mbour", "Diourbel"};
    dep.destination = random(orientation);
    dep.direction = random() % 360;
    dep.vitesse = random(VITMIN, VITMAX);

  // initialisation du numéro de la plaque d'immatriculation du camion : chaine de 5 caractères

    numero_plaque[0] = (random() % 26) + 'C';
    numero_plaque[1] = (random() % 26) + 'C';
    sprintf (&numero_plaque[2], "%03d", (random() % 999) + 1);
    numero_plaque[5] = 0;

    int x = random(-1,1);
    if(x == 0){
        strcat(etat, "normal");
    }
    else if(x == 1){
        strcat(etat, "alarme intermédiare");
    }
    else{
        strcat(etat, "alarme critique");
    }

}

// modifie la vitesse du camion avec la valeur passée en paramètre
void changer_vitesse(int vitesse)
{
    if (vitesse < 0)
        dep.vitesse = 0;
    else if (vitesse > VITMAX)
        dep.vitesse = VITMAX;
    else dep.vitesse = vitesse;
}

// modifie la direction du camion avec la valeur passée en paramètre
void changer_direction(int direction)
{
    if ((direction >= 0) && (direction < 360))
        dep.direction = direction;

    else
        dep.direction = direction - 360;
}



// affiche les caractèristiques courantes du camion
void afficher_donnees()
{
    printf("Camion %s -> localisation : (%d,%d), vitesse : %d, direction : %d\n", numero_plaque, coord.x, coord.y, dep.vitesse, dep.direction);
}

// recalcule la localisation du camion en fonction de sa vitesse et de sa direction
void calcul_deplacement()
{
    float cosinus, sinus;
    float dep_x, dep_y;
    int nb;

    if (dep.vitesse < VITMIN)
        {
            printf("Le camion à l'arrêt\n");
            fermer_communication();
            // La fonction exit permet de mettre fin au programme en spécifiant un code de retour
            exit(2);
        }

    cosinus = cos(dep.direction * 2 * M_PI / 360);
    sinus = sin(dep.direction * 2 * M_PI / 360);

    dep_x = cos(dep.direction * M_PI / 360) * dep.vitesse * 2 ;
    dep_y = sin(dep.direction * M_PI / 360) * dep.vitesse * 2 ;

  // on se déplace d'au moins une case quels que soient la direction et la vitesse
  // sauf si la direction est un des angles droit
    if ((dep_x > 0) && (dep_x < 1)) dep_x = 1;
    if ((dep_x < 0) && (dep_x > -1)) dep_x = -1;

    if ((dep_y > 0) && (dep_y < 1)) dep_y = 1;
    if ((dep_y < 0) && (dep_y > -1)) dep_y = -1;

  //printf(" x : %f y : %f\n", dep_x, dep_y);

    coord.x = coord.x + (int)dep_x;
    coord.y = coord.y + (int)dep_y;

    afficher_donnees();
}

// fonction principale : gère le déplacement du camion au fil du temps
void se_deplacer()
{
    while(1)
        {
        sleep(PAUSE);
        calcul_deplacement();
        envoyer_caracteristiques();
        }
}

int main()
{
  // on initialise l'avion
  initialiser_avion();

  afficher_donnees();
  // on quitte si on arrive � pas contacter le gestionnaire de vols
  if (!ouvrir_communication())
    {
      printf("Impossible de contacter le gestionnaire de vols\n");
      exit(1);
    }

  // on se d�place une fois toutes les initialisations faites
  se_deplacer();
}

