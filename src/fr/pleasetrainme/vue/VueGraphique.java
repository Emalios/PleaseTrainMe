package fr.pleasetrainme.vue;

import fr.pleasetrainme.Principale;
import fr.pleasetrainme.modele.Monde;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class VueGraphique extends JPanel implements Observer {

    private static final int END_Y = 20;

    private static final int CELLULE_SIZE = 5, ARRIVE_SIZE = 40;
    private static final Color END_COLOR = Color.GREEN;
    private static final Color CELLULE_COLOR = Color.BLACK;

    private Monde monde;

    public VueGraphique() {
        setPreferredSize(new Dimension(Principale.WIDTH_MONDE, Principale.HEIGHT_MONDE));
        setBackground(Color.GRAY);

    }

    @Override
    public void update(Observable o, Object arg) {
        this.monde = (Monde) o;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //on dessine l'arrivé
        g.setColor(END_COLOR);
        g.fillRect(getWidth()/2-ARRIVE_SIZE/2, END_Y, ARRIVE_SIZE, ARRIVE_SIZE);
        //on dessine les cellules
        g.setColor(CELLULE_COLOR);
        if(this.monde == null) return;
        this.monde.getCellules().forEach(cellule -> g.fillOval(cellule.getX(), cellule.getY(), CELLULE_SIZE, CELLULE_SIZE));
    }
}
