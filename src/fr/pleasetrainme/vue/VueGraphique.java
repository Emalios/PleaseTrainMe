package fr.pleasetrainme.vue;

import fr.pleasetrainme.modele.Monde;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class VueGraphique extends JPanel implements Observer {

    private Monde monde;

    public VueGraphique(Monde monde) {
        this.monde = monde;
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
}
