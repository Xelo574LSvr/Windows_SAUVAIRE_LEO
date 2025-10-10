public class Calcule extends Thread {
    // TODO: Déclarer les attributs nécessaires
    private int valeur;
    private int multiplicateur;
    private int index;
    private long resultat;

    public Calcule(int valeur, int multiplicateur, int index) {
        this.valeur = valeur;
        this.multiplicateur = multiplicateur;
        this.index = index;
    }

    @Override
    public void run() {
        long tmp = 0;
        for (int i = 0; i < multiplicateur; i++) {
            tmp += valeur * valeur + valeur;
        }
        resultat = tmp;
    }

    public long getResultat() {
        return resultat;
    }
}