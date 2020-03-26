import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main{

    public static void main(String[] args) throws IOException {
        TextHandling analysis = new TextHandling();
        String fileName = "textesimple";
        String pathName = "Data/" + fileName + ".txt";
        HashMap<Character, Integer> sortedHashMap = new HashMap<Character, Integer>();
        sortedHashMap = analysis.sortByFrequency(analysis.createAlphabet(pathName));

        FileWriter alphabetFile = new FileWriter("Data/" + fileName + "_freq.txt");
        BufferedWriter alphabetContent = new BufferedWriter(alphabetFile);

        String numChar = (String) Integer.toString(analysis.numberOfCharacters);
        alphabetFile.write(numChar);
        alphabetContent.newLine();
        for(char key : sortedHashMap.keySet()){
            alphabetContent.write(key + " = " + sortedHashMap.get(key));
            alphabetContent.newLine();
        }
        alphabetContent.flush();
        alphabetContent.close();

        System.out.println("\nOccurences (sorted by frequency and ASCII):\n" + sortedHashMap);
        System.out.println("\nNumber of characters :\n" + analysis.numberOfCharacters);

        Tree tree = new Tree();
        tree.createNodeList(sortedHashMap);
        ArrayList<Node> nodeList = new ArrayList(tree.nodeList);
        Node root = tree.createTree(nodeList);
        System.out.println("\nHuffman tree's root's value :\n" + root.occurence);

        HashMap<Character, String> encodedAlphabet = new HashMap<Character, String>();
        for(Character key : sortedHashMap.keySet()){
            encodedAlphabet.put((char) key, root.encode("", key, new ArrayList<Node>()));
        }
        System.out.println("\nEncoding of the alphabet :\n" + encodedAlphabet);

        String code = "";
        BufferedReader reader = new BufferedReader(new FileReader(pathName));
        String line;
        int size = 0;
        while ((line = reader.readLine()) != null) {
            for (int i = 0; i < line.length(); i++) {
                char letter = line.charAt(i);
                code += encodedAlphabet.get(letter);
                size += encodedAlphabet.get(letter).length();
            }
        }

        reader.close();
        System.out.println("\nString that contains the code :\n" + code);

        String binaryCode = "";

        FileOutputStream binaryFile = new FileOutputStream("Data/" + fileName + "_comp.bin");
        BufferedOutputStream content = new BufferedOutputStream(binaryFile);

        while(code.length() > 1){
            if(code.length() >= 7){
                binaryCode = code.substring(0, 7);
            }
            else{
                while(code.length() < 7){
                    code += "0";
                }
                binaryCode = code;
            }
            code = code.substring(7, code.length());

            // It is important to notice that the byte created should not result in a signed int as it would ruin the
            // decompression.
            int pByte = Byte.toUnsignedInt((byte) ((byte) Integer.parseInt(binaryCode.toString(), 2) & 0xFF));

            binaryFile.write(pByte);
        }
        content.flush();
        binaryFile.close();
        System.out.println("\nThe binary file '" + "\u001B[33m" + fileName + "_comp.bin'" + "\u001B[0m corresponding to the '" + "\u001B[33m" + fileName + ".txt'" + "\u001B[0m text file has successfully been created.");

        File binFile = new File("Data/" + fileName + "_comp.bin");
        int binFileSize = (int) binFile.length();

        File txtFile = new File("Data/" + fileName + ".txt");
        int txtFileSize = (int) txtFile.length();

        float compressionRate = 1 - ((float) binFileSize / (float) txtFileSize);
        System.out.println("\nCompression rate :\n" + "\u001B[33m" + (int) (compressionRate * 100) + "%" + "\u001B[0m");
    }
}