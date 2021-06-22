package fr.pleasetrainme.graphisme;

import javax.swing.*;
import java.awt.*;

public class Boutons extends JPanel {

    private Choix choix;
    private ControleAlgo controleAlgo;

    public Boutons(Principale principale) {
        setLayout(new BorderLayout());
        //setPreferredSize(new Dimension(400, 100));
        this.choix = new Choix();
        this.controleAlgo = new ControleAlgo(principale);
        add(choix, BorderLayout.NORTH);
        add(controleAlgo, BorderLayout.SOUTH);
    }

    public Choix getChoix() {
        return choix;
    }
}
