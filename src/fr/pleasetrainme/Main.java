package fr.pleasetrainme;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("Please Train Me");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Principale principale = new Principale();
        jFrame.setContentPane(principale);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
