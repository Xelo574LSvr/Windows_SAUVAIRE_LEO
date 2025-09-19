import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Image {
    private int width;
    private int height;
    // pixels[y][x][0=R,1=G,2=B]
    private int[][][] pixels; // pixels[y][x][0=R,1=G,2=B]

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    /**
     * Constructeur : initialise une image vide.
     */
    public Image(int width, int hauteur) {
        this.width = width;
        this.height = height;
        pixels = new int[hauteur][largeur][3];
    }

    /**
     * Définit la couleur d'un pixel à la position (x, y)
     */
    public void setPixel(int x, int y, int r, int g, int b) {
        if (x >= 0 && x < largeur && y >= 0 && y < hauteur) {
            pixels[y][x][0] = r;
            pixels[y][x][1] = g;
            pixels[y][x][2] = b;
        }
    }

    /**
     * Sauvegarde l'image au format texte PPM (P3)
     */
    public void save_txt(String filename) throws IOException {
        // TODO : écrire le fichier PPM avec FileWriter
    }


    static public read_txt(String filename) throws IOException { 
        FileInputStream fs = new FileInputStream("FirstPPM.ppm");
        Scanner sc = new scanner(fs);

        if (sc.nextLine() != "P3") {
            throw new IOException(erreurFormat);  
        }

        int width = sc.nextInt();
        int height = sc.nextInt();
        int max = sc.nextInt();

        Image img = new Image(w, h);

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int r = sc.nextInt();
                int g = sc.nextInt();
                int b = sc.nextInt();

                img.setPixel(x, y, r, g, b);
            }
        }

    }
}
