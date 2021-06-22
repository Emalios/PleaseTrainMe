package fr.pleasetrainme.graphisme;

import javax.swing.*;
import java.awt.*;

public class Principale extends JPanel {

    private Monde monde;
    private Boutons boutons;

    public Principale() {
        setLayout(new BorderLayout());
        this.boutons = new Boutons(this);
        this.monde = new Monde(boutons);
        add(monde, BorderLayout.CENTER);
        add(boutons, BorderLayout.SOUTH);
    }

    public Monde getMonde() {
        return monde;
    }

    public Boutons getBoutons() {
        return boutons;
    }
}
