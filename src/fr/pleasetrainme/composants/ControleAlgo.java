package fr.pleasetrainme.composants;

import fr.pleasetrainme.Principale;

import javax.swing.*;
import java.awt.*;

public class ControleAlgo extends JPanel {

    private JButton lancer, stop;

    public ControleAlgo(Principale principale) {
        //setLayout(new BorderLayout());
        this.lancer = new JButton("Run");
        this.lancer.addActionListener(e -> principale.getMonde().lancerIteration(principale.getBoutons().getChoix().getNbPop(), principale.getBoutons().getChoix().getNbIteration()));
        this.stop = new JButton("Stop");
        this.stop.addActionListener(e -> principale.getMonde().stop());
        add(this.lancer, BorderLayout.WEST);
        add(this.stop, BorderLayout.EAST);
    }

}
