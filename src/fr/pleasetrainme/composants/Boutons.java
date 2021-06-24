package fr.pleasetrainme.composants;

import fr.pleasetrainme.Principale;

import javax.swing.*;
import java.awt.*;

public class Boutons extends JPanel {

    private Choix choix;
    private ControleAlgo controleAlgo;

    public Boutons(Principale principale) {
        setLayout(new BorderLayout());
        this.choix = new Choix();
        this.controleAlgo = new ControleAlgo(principale);
        add(choix, BorderLayout.NORTH);
        add(controleAlgo, BorderLayout.SOUTH);
    }

    public Choix getChoix() {
        return choix;
    }
}
