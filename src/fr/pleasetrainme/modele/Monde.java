package fr.pleasetrainme.modele;

import fr.pleasetrainme.Principale;
import fr.pleasetrainme.composants.Boutons;
import fr.pleasetrainme.vue.VueGraphique;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

public class Monde extends Observable {

    /**
     * représente le timer principale du jeu
     */
    private final Timer game;

    private boolean doitAfficher;
    /**
     * nombre courant d'itération
     */
    private int nombreIteration;

    /**
     * liste des cellules présentes dans le monde
     */
    private List<Cellule> cellules;

    /**
     * liste des positions des murs présents dans le monde
     */
    private List<Position> murs;
    private boolean finIteration;
    private boolean doitDeplacer;

    public Monde(VueGraphique vueGraphique, Boutons boutons) {
        this.murs = new ArrayList<>();
        this.cellules = new ArrayList<>();
        addObserver(vueGraphique);
        this.game = new Timer(0, e -> {
            if(doitDeplacer) deplacerMob();
            if(doitAfficher) {
                setChanged();
                notifyObservers();
            }
        });
    }

    private void deplacerMob() {
        int nbCellulesArrive = 0;
        //on déplace les cellules
        for (Cellule cellule : this.cellules) {
            //TODO: refactor ce code en déplaçant la logique de sortir hors de la map ici
            //On teste si la cellule ne va pas contre un mur
            Position newPos = cellule.peekNextMove();
            int newX = newPos.getX();
            int newY = newPos.getY();
            boolean seDeplace = true;
            int i = 0;
            while (seDeplace && i < murs.size()) {
                Position posMur = this.murs.get(i++);
                int murX = posMur.getX();
                int murY = posMur.getY();
                if((murX <= newX && newX <= murX + VueGraphique.MUR_SIZE) && (murY <= newY && newY <= murY + VueGraphique.MUR_SIZE)) {
                    //si la cellule est dans un mur alors elle ne se déplace pas elle augmente juste son déplacement
                    seDeplace = false;
                    cellule.augmenterGene();
                }
            }
            //s'il ne peut pas se déplacer on passe à la cellule suivante
            if(!seDeplace) continue;
            cellule.seDeplacer(Principale.WIDTH_MONDE, Principale.HEIGHT_MONDE);
            //on teste si la cellule a atteint l'arrivé
            if (Principale.X_ARRIVE > newX || newX > Principale.X_ARRIVE + Principale.TAILLE_ARRIVE) {
                continue;
            }
            if (Principale.Y_ARRIVE > newY || newY > Principale.Y_ARRIVE + Principale.TAILLE_ARRIVE) continue;
            cellule.finIteration();
            nbCellulesArrive++;

        }
        /*
        Une itération est terminé si au moins 5% de la population totale est arrivé
         */
        int truc = this.cellules.size()*5/100;
        if(truc == 0) truc = 1;
        if(nbCellulesArrive >= truc) {
            this.evoluer();
            this.replacerCellule();
            System.out.println("Début de l'itération: " + this.nombreIteration++);
            finIteration = false;
            this.doitDeplacer = true;
            this.doitAfficher = true;
        }
    }

    /**
     * Méthode plaçant toutes les cellules à leur position de départ.
     */
    private void replacerCellule() {
        this.cellules.forEach(cellule -> cellule.setPos(198, 525));
    }

    /**
     * Méthode faisant évoluer la liste courante des cellules afin d'améliorer leur score.
     */
    private void evoluer() {
        List<Cellule> newCellules = new ArrayList<>(this.cellules.size());
        Collections.sort(this.cellules);
        /*
        On prend les meilleurs de la population afin de les reproduire, pour déterminer combien de meilleurs nous prenons, on calcule '5%' de la population totale
         */
        int nbMeilleurs = this.cellules.size()*5/100;
        //on s'inspire aussi de 5% des pires
        //Ensuite on calcule le nombre de cellule qui va reproduire avec un des meilleurs
        int cellulesParMeilleur = (this.cellules.size()-nbMeilleurs)/nbMeilleurs;
        int sommeChemin = 0;
        //On fait évoluer les cellules
        for (int i = 0; i < nbMeilleurs; i++) {
            //On ajoute les meilleurs à la nouvelle liste
            Cellule meilleur = this.cellules.get(i);
            //on ajoute le meilleur chemin afin de calculer le meilleur chemin moyen parmis les 5%
            sommeChemin += meilleur.getNbDeplacements();
            //On créer une copie du meilleur et on le fait muter (en resetant son compteur de déplacement) afin de l'ajouter à la nouvelle liste
            Cellule meilleurCopie = meilleur.clone();
            meilleurCopie.muter();
            newCellules.add(meilleurCopie);
            //On fait se reproduire les meilleurs avec les autres cellules et on ajoute les nouvelles cellules à la liste
            for (int j = 0; j < cellulesParMeilleur; j++) {
                Cellule courante = this.cellules.get(i*cellulesParMeilleur+j+nbMeilleurs);
                Cellule worstCurrent = this.cellules.get(this.cellules.size() - (i * cellulesParMeilleur + j + nbMeilleurs));
                newCellules.add(meilleur.reproduire(worstCurrent).reproduire(courante));
            }
        }
        System.out.println("Meilleur chemin moyen: " + sommeChemin/nbMeilleurs);
        this.cellules = newCellules;
    }

    public void lancerIteration(int nbPop, int nbIteration) {
        this.cellules = new ArrayList<>(nbPop);
        for (int i = 0; i < nbPop; i++) {
            Cellule cellule = new Cellule();
            this.cellules.add(cellule);
        }
        this.finIteration = false;
        //on place les cellules à leurs place
        this.replacerCellule();
        System.out.println("Lancement d'une itération...");
        this.doitDeplacer = true;
        this.doitAfficher = true;
        this.game.start();
    }

    /**
     * Méthode permettant d'enregistrer un mur dans le monde
     * @param x posX du mur
     * @param y posY du mur
     */
    public void creerMur(int x, int y) {
        this.murs.add(new Position(x, y));
        setChanged();
        notifyObservers();
    }

    public List<Cellule> getCellules() {
        return new ArrayList<>(cellules);
    }

    public List<Position> getMurs() { return new ArrayList<>(murs); }
}
