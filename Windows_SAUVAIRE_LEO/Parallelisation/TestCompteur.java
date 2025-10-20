public class TestCompteur {
    private static final int NB_THREADS = 9;
    private static final int NB_INCREMENT = 1111111;
    private static Thread[] threads = new Thread[NB_THREADS];

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Test du Compteur Dangereux");

        CompteurDangereux.resetCompteur();

        
        for (int i = 0; i < NB_THREADS; i++) {
            threads[i] = new Thread(new CompteurDangereux.Incrementeur("Thread" + i, NB_INCREMENT));
            threads[i].start();
        }

        for (int i = 0; i < NB_THREADS; i++) {
            threads[i].join();
        }

        System.out.println("Compteur : " + CompteurDangereux.getCompteur());

        // Affichage des résultats
        long attendu = (long)NB_THREADS * NB_INCREMENT;
        int obtenu = CompteurDangereux.getCompteur();
        
        System.out.println("\nRésultat attendu : " + attendu);
        System.out.println("Résultat obtenu : " + obtenu);
        System.out.println("Différence : " + (attendu - obtenu));
    }
}