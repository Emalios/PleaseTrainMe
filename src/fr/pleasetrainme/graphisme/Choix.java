package fr.pleasetrainme.graphisme;

import javax.swing.*;
import java.awt.*;

public class Choix extends JPanel {

    private JTextField popField, iterField;

    public Choix() {
        setLayout(new GridLayout(2, 2));
        JLabel popLabel = new JLabel("Nombre d'individus");
        this.popField = new JTextField();
        JLabel iterLabel = new JLabel("Nombre d'itération");
        this.iterField = new JTextField();
        add(popLabel);
        add(popField);
        add(iterLabel);
        add(iterField);
    }

    public int getNbPop() {
        String numberStr = this.popField.getText();
        int nb = 100;
        try {
            nb = Integer.parseInt(numberStr);
            return nb;
        } catch (NumberFormatException ignored) {
            System.out.println("Erreur lors de la lecture du nombre de population => population par défaut: 100");
            return nb;
        }
    }

    public int getNbIteration() {
        String numberStr = this.iterField.getText();
        int nb = 100;
        try {
            nb = Integer.parseInt(numberStr);
            return nb;
        } catch (NumberFormatException ignored) {
            System.out.println("Erreur lors de la lecture du nombre d'itération => nombre d'itérations par défaut: 100");
            return nb;
        }
    }

}
