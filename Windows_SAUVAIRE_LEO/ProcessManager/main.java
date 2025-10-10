import java.io.*;

public class Main {

    public static void main (String[] args) {
        System.out.println("Test étape 1");
        ProcessController p1 = new ProcessController();

        String[] commandes = {"ls", "echo", "Hello World!"};

        try {
            p1.executeSimple("cmd", commandes);
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Test étape 2");
        ProcessController p2 = new ProcessController();
        File outputFile = new File("outputFile.txt");
        File error = new File("error.txt");


        try {
            p2.executeWithRedirection("cmd", outputFile, error, commandes);
        } catch (IOException e) {
            System.out.println("Erreur lors de l'exécution avec redirection : " + e.getMessage());
            e.printStackTrace();
        }
    }
}