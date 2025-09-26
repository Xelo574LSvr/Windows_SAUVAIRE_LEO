import java.io.*;

import javax.swing.text.Utilities;

public class MemoryManager {
    public static final int BLOCK_SIZE = 512;
    public static final int TOTAL_MEMORY = 1024 * 1024; // 1 Mo
    public static final int NUM_BLOCKS = TOTAL_MEMORY / BLOCK_SIZE; // 2048

    // Offsets des différentes zones
    public static final int SUPERBLOCK_OFFSET = 0;
    public static final int BITMAP_OFFSET = BLOCK_SIZE;
    public static final int INODE_TABLE_OFFSET = 2 * BLOCK_SIZE;
    public static final int DATA_OFFSET = 129 * BLOCK_SIZE;

    public static final int INODE_SIZE = 128; // Taille d'un inode en octets
    public static final int INODE_TABLE_SIZE = DATA_OFFSET - INODE_TABLE_OFFSET;
    public static final int MAX_INODES = INODE_TABLE_SIZE / INODE_SIZE; // => 508

    // CONTRAINTE : Un seul tableau pour TOUT le système de fichiers
    private byte[] memory;

    public MemoryManager() {
        this.memory = new byte[TOTAL_MEMORY];
        initializeFilesystem();
    }

    private void initializeFilesystem() {
        // Initialiser le superbloc
        writeSuperblock();

        // Marquer les blocs système comme occupés
        setBlockUsed(0);  // superbloc
        setBlockUsed(1);  // bitmap

        for (int i = 2; i < 129; i++)
            setBlockUsed(i);  // table des inodes
    }

    private void writeSuperblock() {
        String signature = "Myfs1.0";
        System.arraycopy(signature, 0, memory, 0, Math.max(signature.length, 28));

        //  int [] parameters = new int[] {
        //      BLOCK_SIZE,
        //      TOTAL_MEMORY
        //  };

        //  System.arraycopy(parameters, 0, memory, 0, 4*parameters.length);

        // copier BLOCK_SIZE
        Utils.writeInt(memory, 28, BLOCK_SIZE);
        Utils.writeInt(memory, 34, TOTAL_MEMORY);
    }

   public boolean setBlockUsed(int blockNumber, boolean used) {
        if (blockNumber < 0 || blockNumber >= NUM_BLOCKS)
            return false;

        int byteIndex = blockNumber / 8;
        int bitPosition = blockNumber % 8;
        int offset = BITMAP_OFFSET + byteIndex;


        if (used) {
            memory[offset] = memory[offset] | (1 << bitPosition);
        } else {
            memory[offset] = memory[offset] & ~(1 << bitPosition);
        }

        return true;
    }


    public int isBlockUsed(int blockNumber) {
        if (blockNumber < 0 || blockNumber >= NUM_BLOCKS)
                    return -1;

        int byteIndex = blockNumber / 8;
        int bitPosition = blockNumber % 8;
        int offset = BITMAP_OFFSET + byteIndex;

        return (memory[offset] >> bitPosition) & 1;
    }

    public int allocateBlock() {
        // TODO: Complétez cette méthode étape par étape
        // ÉTAPE 1: Boucle de la page 129 à la fin (les pages de données)
        for (int i = 129; i < NUM_BLOCKS; i++) {
            if (isBlockUsed(i) == 0) { // Bloc libre trouvé !
                setBlockUsed(i, true); // le marquer comme occupé
                System.out.println("Le Bloc " + i + " a été alloué."); // L'annoncer à l'utilisateur
                return i; // Le retourner
            }
        }
        // ÉTAPE 2: Pour chaque page, vérifier si elle est libre
        // ÉTAPE 3: Si libre, la marquer comme occupée
        // ÉTAPE 4: Retourner son numéro

        // AIDE: Commencer par cette structure
        /*
        for (int i = 129; i < NUM_BLOCKS; i++) {
            if (isBlockUsed(i) == 0) {  // Bloc libre trouvé !
                // TODO: Le marquer comme occupé
                // TODO: L'annoncer à l'utilisateur
                // TODO: Le retourner
            }
        }
        */
        return -1; // Pas de bloc libre
    }

    public byte[] getmemory() {
        return memory;
    }

    public void saveToFile() throws IOException {
            FileOutputStream fos = new FileOutPutStream("memfs.raw");
            fos.write(memory);
            fos.close();
    }

    public void loadFromFile() throws IOException {
            FileInputStream fis = new FileInputStream("memfs.raw");
            fis.read(memory);
            fis.close();
    }
}
