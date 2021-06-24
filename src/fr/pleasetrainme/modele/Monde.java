package fr.pleasetrainme.modele;

import fr.pleasetrainme.composants.Boutons;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Monde extends JPanel {

    private static final int END_Y = 20;

    private static final int CELLULE_SIZE = 5, ARRIVE_SIZE = 40;
    private static final Color END_COLOR = Color.GREEN;
    private static final Color CELLULE_COLOR = Color.BLACK;

    private int nombreIteration;
    private Timer game;
    private boolean doitAfficher, doitDeplacer;
    private boolean finIteration;
    private List<Cellule> cellules;

    public Monde(Boutons boutons) {
        setPreferredSize(new Dimension(400, 600));
        int nbPop = boutons.getChoix().getNbPop();
        this.cellules = new ArrayList<>(nbPop);
        for (int i = 0; i < nbPop; i++) {
            Cellule cellule = new Cellule();
            this.cellules.add(cellule);
        }
        setBackground(Color.GRAY);
        this.game = new Timer(0, e -> {
            if(doitDeplacer) deplacerMob();
            if(doitAfficher) repaint();
        });
    }

    private void deplacerMob() {
        int nbCellulesArrive = 0;
        //on déplace les cellules
        for (Cellule cellule : this.cellules) {
            cellule.seDeplacer(getWidth(), getHeight());
            int celluleX = cellule.getX();
            int celluleY = cellule.getY();
            //on teste si la cellule a atteint l'arrivé
            if (getWidth() / 2 - ARRIVE_SIZE / 2 > celluleX || celluleX > getWidth() / 2 - ARRIVE_SIZE / 2 + ARRIVE_SIZE) {
                continue;
            }
            if (END_Y > celluleY || celluleY > END_Y + ARRIVE_SIZE) continue;
            cellule.finIteration();
            nbCellulesArrive++;
        }
        if(nbCellulesArrive >= this.cellules.size()*5/100) {
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
        List<Cellule> cellules = new ArrayList<>(this.cellules.size());
        Collections.sort(this.cellules);
        /*
        On prend les meilleurs de la population afin de les reproduire, pour déterminer combien de meilleurs nous prenons, on calcule '5%' de la population totale
         */
        int nbMeilleurs = this.cellules.size()*5/100;
        //Ensuite on calcule le nombre de cellule qui va reproduire avec un des meilleurs
        int cellulesParMeilleur = (this.cellules.size()-nbMeilleurs)/nbMeilleurs;
        //On fait évoluer les cellules
        for (int i = 0; i < nbMeilleurs; i++) {
            //On ajoute les meilleurs à la nouvelle liste
            Cellule meilleur = this.cellules.get(i);
            System.out.println("Meilleur chemin: " + meilleur.getNbDeplacements());
            cellules.add(meilleur);
            //On fait se reproduire les meilleurs avec les autres cellules et on ajoute les nouvelles cellules à la liste
            for (int j = 0; j < cellulesParMeilleur; j++) {
                Cellule courante = this.cellules.get(i*nbMeilleurs+j);
                cellules.add(meilleur.reproduire(courante));
            }
        }
        this.cellules = cellules;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //on dessine l'arrivé
        g.setColor(END_COLOR);
        g.fillRect(getWidth()/2-ARRIVE_SIZE/2, END_Y, ARRIVE_SIZE, ARRIVE_SIZE);
        //on dessine les cellules
        g.setColor(CELLULE_COLOR);
        this.cellules.forEach(cellule -> {
            g.fillOval(cellule.getX(), cellule.getY(), CELLULE_SIZE, CELLULE_SIZE);
        });
    }

    public void lancerIteration() {
        this.finIteration = false;
        //on place les cellules à leurs place
        this.replacerCellule();
        System.out.println("Lancement d'une itération...");
        this.doitDeplacer = true;
        this.doitAfficher = true;
        this.game.start();
    }
}
