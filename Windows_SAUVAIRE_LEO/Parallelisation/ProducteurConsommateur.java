import java.util.concurrent.Semaphore;
import java.util.LinkedList;
import java.util.Queue;

public class ProducteurConsommateur {
    private static final int TAILLE_BUFFER = 5;
    private final Integer[] buffer = new Integer[TAILLE_BUFFER];

    // TODO: Déclarer 3 sémaphores avec les bonnes valeurs initiales
    private final Semaphore placesLibres = new Semaphore(TAILLE_BUFFER);
    private final Semaphore elementsDisponibles = new Semaphore(0);  
    private final Semaphore mutexBuffer = new Semaphore(1);
    
    class Producteur implements Runnable {
        public void run() {
            for (int i = 0; i < 10; i++) {
                int produit = genererProduit();
                
                // Attendre une place libre
                placesLibres.acquire(); 

                // TODO: Acquérir l'accès exclusif au buffer 
                mutexBuffer.acquire();

                // TODO: Ajouter l'élément au buffer
                buffer.add(produit);

                // TODO: Libérer l'accès au buffer
                mutexBuffer.release();

                // TODO: Signaler qu'un élément est disponible
                elementsDisponibles.release();
            }
        }
    }

    class Consommateur implements Runnable {
        public void run() {
            for (int i = 0; i < 7; i++) {

                // TODO: Attendre qu'un élément soit disponible
                elementsDisponibles.acquire();

                // TODO: Acquérir l'accès exclusif au buffer
                mutexBuffer.acquire();

                // TODO: Retirer un élément du buffer  
                buffer.remove(produit);
                
                // TODO: Libérer l'accès au buffer
                mutexBuffer.release();
                
                // TODO: Signaler qu'une place est libre
                placesLibres.release();
            }
        }
    }
}