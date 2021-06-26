package fr.pleasetrainme.controleur;

import fr.pleasetrainme.modele.Monde;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MondeListener extends MouseAdapter {

    private final Monde monde;

    public MondeListener(Monde monde) {
        this.monde = monde;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        int x = e.getX();
        int y = e.getY();
        monde.creerMur(x, y);
    }
}
