public class TestCompteur {
    private static final int NB_THREADS = 20;
    private static final int NB_INCREMENT = 100000;
    private static Thread[] threads = new Thread[NB_THREADS];

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Test du Compteur Dangereux");

        CompteurDangereux.resetCompteur();

        
        for (int i = 0; i < NOMBRE_THREADS; i++) {
            threads[i] = new CompteurDangereux.Incrementeur("Thread" + i, NB_INCREMENT);
            threads[i].start();
        }

        for (int i = 0; i < NOMBRE_THREADS; i++) {
            threads[i].join();
        }

        System.out.println("Compteur : " + CompteurDangereux.getCompteur());
    }
}