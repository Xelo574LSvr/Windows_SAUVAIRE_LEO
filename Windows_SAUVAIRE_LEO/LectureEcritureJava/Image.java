import java.io.FileInputStream;
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
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("P3\n");
            writer.write(width + " " + height + "\n");
            writer.write("255\n");

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                int r = pixels[y][x][0];
                int g = pixels[y][x][1];
                int b = pixels[y][x][2];
                writer.write(r + " " + g + " " + b + " ");
                }
                writer.write("\n");
            }
        }
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

    



    public static Image read_txt_token(String filename) throws IOException {
        // Lire tout le fichier
        FileInputStream fis = new FileInputStream(filename);
        byte[] data = fis.readAllBytes();
        fis.close();

        // Question : faut-il gérer l'encodage ici ?
        String content = new String(data);

        // --- Première passe : compter le nombre de tokens ---
        
        int tokenCount = 0;
        int lastPos = 0;
        
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if (Character.isWhitespace(c)) {
            tokenCount++;
            lastPos = i + 1;
            }
        }
        
        if (lastPos < content.length()) { // dernier token
            tokenCount++;
        }

        // --- Deuxième passe : extraire les tokens ---
        
        String[] tokens = new String[tokenCount];
        int index = 0;
        lastPos = 0;
        
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if (Character.isWhitespace(c)) {
            if (i > lastPos) {
                // Question : i ou i-1 ? vérifier la doc ;)
                tokens[index++] = content.substring(lastPos, i);
            }
            lastPos = i + 1;
            }
        }
        
        if (lastPos < content.length()) {
            tokens[index++] = content.substring(lastPos);
        }

        // --- Extraction des informations ---
        
        int pos = 0;
        String magic = tokens[pos++];
        
        // Pourrais être fait au tout début
        if (!magic.equals("P3")) {
            throw new IOException("Format PPM non supporté : " + magic);
        }

        int width = Integer.parseInt(tokens[pos++]);
        int height = Integer.parseInt(tokens[pos++]);
        int maxVal = Integer.parseInt(tokens[pos++]); // ex: 255

        Image img = new Image(width, height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
            int r = Integer.parseInt(tokens[pos++]);
            int g = Integer.parseInt(tokens[pos++]);
            int b = Integer.parseInt(tokens[pos++]);
            img.setPixel(x, y, r, g, b);
            }
        }

        return img;
    }




    /**
     * Sauvegarde l'image au format PPM binaire (P6)
     * @param filename
     * @throws IOException
     */
    public void save_bin (String filename) throws IOException {
        FileOutPutStream fs = new FileOutPutStream(filename);
        // En-tête
        String header = "P6\n" + width + " " + height + "\n255\n";
        fos.write(header.getBytes());

        // Pixels (R, G, B en octets)
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                fos.write((byte) pixels[y][x][0]); // R
                fos.write((byte) pixels[y][x][1]); // G
                fos.write((byte) pixels[y][x][2]); // B
            }
        }
    }





    /**
     * Lecture d'une image au format PPM binaire (P6)
     */
    public static Image read_bin(String filename) throws IOException {
        try (FileInputStream fis = new FileInputStream(filename)) {
            // Lire l'en-tête en texte
            StringBuilder header = new StringBuilder();
            int c;
            int newlines = 0;
            while (newlines < 3 && (c = fis.read()) != -1) {
                header.append((char) c);
                if (c == '\n') {
                    newlines++;
                }
            }

            String[] parts = header.toString().trim().split("\\s+");
            if (!parts[0].equals("P6")) {
                throw new IOException("Format PPM non supporté : " + parts[0]);
            }

            int width = Integer.parseInt(parts[1]);
            int height = Integer.parseInt(parts[2]);
            int maxVal = Integer.parseInt(parts[3]); // normalement 255

            // Lire les pixels bruts
            byte[] raw = fis.readAllBytes();
            if (raw.length < width * height * 3) {
                throw new IOException("Fichier Incomplet !");
            }

            Image img = new Image(width, height);
            int idx = 0;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int r = raw[idx++] & 0xFF; // convertir byte signé -> 0..255
                    int g = raw[idx++] & 0xFF;
                    int b = raw[idx++] & 0xFF;
                }
            }
        }
        return img;
    }
}
