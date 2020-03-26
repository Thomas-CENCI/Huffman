import org.jetbrains.annotations.NotNull;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TextHandling {
    int numberOfCharacters;

    public void setNumberOfCharacters(int numberOfCharacters) {
        this.numberOfCharacters = numberOfCharacters;
    }

    public HashMap<Character, Integer> createAlphabet(String pathName) throws IOException {
        /**
         * This function will read a given [.txt] file and analyses the frequency of each character and stores all that
         * data in a hashMap.
         */

        HashMap<Character, Integer> dict = new HashMap<>();

        BufferedReader reader = new BufferedReader(new FileReader(pathName));

        String line;
        int lineCounter = 0;
        while ((line = reader.readLine()) != null) {
            for (int i = 0; i < line.length(); i++) {
                char letter = line.charAt(i);
                int occurence = dict.getOrDefault((char) letter, 0);
                dict.put((char) letter, occurence + 1);
            }
            lineCounter++;
        }

        lineCounter--;
        dict.put((char) 8, lineCounter); // Allows me to add the backspaces

        reader.close();

        int numberOfCharacter = 0;
        for (int i : dict.values()) {
            numberOfCharacter += i;
        }
        this.setNumberOfCharacters(numberOfCharacter);

        return dict;
    }

    @NotNull
    public static HashMap<Character, Integer> sortByFrequency(@NotNull HashMap dict) {
        /**
         * This function sorts a hashMap according to the ASCII value of each key following the ascending order of all
         * frequencies.
         */
        List linkedlist = new LinkedList(dict.entrySet());
        Collections.sort(linkedlist, new Comparator() {
            public int compare(Object o1, Object o2) {
                /**
                 * This comparator allows me to compare two objects as it is not a given possible comparaison
                 * I created it by telling my program that comparing two objects means comparing their value.
                 */
                return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
            }
        });
        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = linkedlist.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }
}