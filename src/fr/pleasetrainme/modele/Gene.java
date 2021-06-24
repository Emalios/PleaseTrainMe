package fr.pleasetrainme.modele;

import java.util.Random;

public enum Gene {

    NORD, SUD, EST, OUEST;

    public static Gene getRandomGene() {
        Random random = new Random();
        int geneRandom = random.nextInt(Gene.values().length);
        return Gene.values()[geneRandom];
    }

}
