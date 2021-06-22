package fr.pleasetrainme.graphisme;

import javax.swing.*;
import java.awt.*;

public class ControleAlgo extends JPanel {

    private JButton lancer, stop;

    public ControleAlgo(Principale principale) {
        //setLayout(new BorderLayout());
        this.lancer = new JButton("Run");
        this.lancer.addActionListener(e -> {
                principale.getMonde().lancerIteration();

        });
        this.stop = new JButton("Stop");
        add(this.lancer, BorderLayout.WEST);
        add(this.stop, BorderLayout.EAST);
    }

}
