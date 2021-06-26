package fr.pleasetrainme;

import fr.pleasetrainme.composants.Boutons;
import fr.pleasetrainme.controleur.MondeListener;
import fr.pleasetrainme.modele.Monde;
import fr.pleasetrainme.vue.VueGraphique;

import javax.swing.*;
import java.awt.*;

public class Principale extends JPanel {

    public static final int WIDTH_MONDE = 400;
    public static final int HEIGHT_MONDE = 600;

    public static final int TAILLE_ARRIVE = 40;
    public static final int X_ARRIVE = WIDTH_MONDE / 2 - TAILLE_ARRIVE / 2;
    public static final int Y_ARRIVE = 20;

    private Monde monde;
    private Boutons boutons;

    public Principale() {
        setLayout(new BorderLayout());
        this.boutons = new Boutons(this);
        VueGraphique vueGraphique = new VueGraphique();
        this.monde = new Monde(vueGraphique, boutons);
        vueGraphique.addMouseListener(new MondeListener(monde));
        add(vueGraphique, BorderLayout.CENTER);
        add(boutons, BorderLayout.SOUTH);
    }

    public Monde getMonde() {
        return monde;
    }

    public Boutons getBoutons() {
        return boutons;
    }
}
