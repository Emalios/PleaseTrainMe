package fr.pleasetrainme.modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cellule implements Comparable<Cellule> {

    /**
     * Indique de combien de pixel la cellule doit bouger à chaque déplacement
     */
    private static final int DEPLACEMENT = 5;

    /**
     * variable valant vrai si la cellule touche la zone d'arrivé, sinon faux
     */
    private boolean estArrive;
    /**
     * coordonnées de la cellule
     */
    private int x, y;
    /**
     * Liste des génes de la cellule
     */
    private List<Gene> genes;
    /**
     * variable utilisé pour savoir quel gene doit être utilisé pour le prochain déplacement, correspond au nombre de déplacement fait par la cellule
     */
    private int currentGene;

    public Cellule() {
        //On génére une liste de genes de très grande taille, il serait peut être plus intéressant de la construire dynamiquement.
        construireGenesAleatoires(9999);
        this.currentGene = 0;
    }

    /**
     * Méthode construisant de nouveaux génes de manière aléatoire de taille taille
     * @param taille taille de la liste de génes
     */
    private void construireGenesAleatoires(int taille) {
        this.genes = new ArrayList<>(taille);
        Random random = new Random();
        for (int i = 0; i < taille; i++) {
            int nombreAleatoire = random.nextInt(Gene.values().length);
            this.genes.add(Gene.values()[nombreAleatoire]);
        }
    }

    /**
     * Méthode indiquant à la cellule qu'elle doit se déplacer à l'intérieur du monde
     * @param maxX x à ne pas dépasser
     * @param maxY y à ne pas dépasser
     */
    public void seDeplacer(int maxX, int maxY) {
        //si la cellule est arrivé on ne fait rien
        if(this.estArrive) return;
        //si l'indice courant dépasse la taille de la liste on ne fait rien
        if(this.currentGene >= this.genes.size()) {
            System.out.println("épuisement des génes");
            return;
        }
        //on récupère le géne actuel et on incrémente
        Gene current = this.genes.get(this.currentGene++);
        //on déplace la cellule
        switch (current) {
            case EST: this.x+=DEPLACEMENT; break;
            case OUEST: this.x-=DEPLACEMENT; break;
            case NORD: this.y-=DEPLACEMENT; break;
            case SUD: this.y+=DEPLACEMENT; break;
        }
        //on vérifie que les coordonées sont correctes, sinon on rectifie
        if(y < 0) y = 0;
        if(x < 0) x = 0;
        if(y > maxY) y = maxY;
        if(x > maxX) x = maxX;
    }

    /**
     * Méthode reproduisant une cellule avec comme parent, l'objet Cellule courant plus l'objet Cellule passé en paramètre
     * @param autreParent deuxième parent
     * @return une cellule composé des génes des deux parents
     */
    public Cellule reproduire(Cellule autreParent) {
        Cellule cellule = new Cellule();
        //On va itérer sur tous les génes des deux parents et aléatoirement en choisir des bouts à ajouté à la cellule enfant
        int i = 0;
        Random random = new Random();
        while (i < this.currentGene && i < autreParent.currentGene) {
            int nombreRandom = random.nextInt(2);
            if(nombreRandom == 0) cellule.genes.set(i, this.genes.get(i));
            else cellule.genes.set(i, autreParent.genes.get(i));
            i++;
        }
        cellule.muter();
        return cellule;
    }

    private void muter() {
        Random random = new Random();
        for (int j = 0; j < this.genes.size(); j++) {
            int nombreRandom = random.nextInt(20);
            if(nombreRandom == 0) this.genes.set(j, Gene.getRandomGene());
        }
    }

    private void reset() {
        this.estArrive = false;
        this.currentGene = 0;
        this.muter();
    }

    /**
     * Méthode indiquant à la cellule qu'elle n'a plus à bouger (elle est arrivé)
     */
    public void finIteration() {
        this.estArrive = true;
        //on "coupe" les génes de la cellule pour ne garder que ceux utiles
        List<Gene> genesUtiles = new ArrayList<>(this.currentGene);
        for (int i = 0; i < currentGene; i++) {
            genesUtiles.add(this.genes.get(i));
        }
        this.genes = genesUtiles;
    }

    /**
     * méthode settant les positions à y et x
     * @param x x
     * @param y y
     */
    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return y;
    }

    public boolean estArrive() {
        return estArrive;
    }

    public int getNbDeplacements() {
        return currentGene;
    }

    @Override
    public int compareTo(Cellule o) {
        return Integer.compare(this.getNbDeplacements(), o.getNbDeplacements());
    }
}
