public class TestMutex {
    private static final int NB_THREADS = 9;
    private static final int NB_INCREMENT = 1111111;
    private static Thread[] threads = new Thread[NB_THREADS];

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Test du Compteur Securisé");

        long attendu;
        int obtenu;
        CompteurSecurise.resetCompteur();

        
        for (int i = 0; i < NB_THREADS; i++) {
            threads[i] = new Thread(new CompteurSecurise.Incrementeur("Thread" + i, NB_INCREMENT));
            threads[i].start();
        }

        for (int i = 0; i < NB_THREADS; i++) {
            threads[i].join();
        }

        System.out.println("Compteur : " + CompteurSecurise.getCompteur());

        // Affichage des résultats
        attendu = (long)NB_THREADS * NB_INCREMENT;
        obtenu = CompteurSecurise.getCompteur();
        
        System.out.println("Résultat attendu : " + attendu);
        System.out.println("Résultat obtenu  : " + obtenu);
        System.out.println("Différence       : " + (attendu - obtenu));
    }
}