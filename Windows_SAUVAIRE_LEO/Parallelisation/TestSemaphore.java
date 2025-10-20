public class TestSemaphore {

    public static void main(String[] args) {
        ProducteurConsommateur prodConso = new ProducteurConsommateur();

        Thread prod1 = new Thread(new Producteur(ProdConso));
        Thread prod2 = new Thread(new Producteur(ProdConso));

        Thread conso1 = new Thread(new Consommateur(ProdConso));
        Thread conso2 = new Thread(new Consommateur(ProdConso));
        Thread conso3 = new Thread(new Consommateur(ProdConso));

        System.out.println("Scénario équilibré");
        prod1.start();
        prod2.start();

        conso1.start();
        conso2.start();
        conso3.start();
    }
}