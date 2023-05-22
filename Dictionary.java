import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Dictionary {
    Trie trie;
    public Dictionary() {
        trie = new Trie();
        try {
            addFileToDictionary();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addFileToDictionary() throws FileNotFoundException {
        File file = new File("/Users/phoebeswaine/Projects/Scrabble_v2/File.txt");
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String word = sc.nextLine();
                word = word.toUpperCase();
                trie.insertWord(word);
            }
        }
    }

    public boolean verifyWord(String word) {
        word = word.toUpperCase();
        return trie.searchWord(word);
    }
}
