import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Comparator;

public class Node {
    char key;
    int occurence;
    Node leftChild = null;
    Node rightChild = null;
    Node parent = null;

    public Node(char key, int occurence){
        this.key = key;
        this.occurence = occurence;
    }

    public Node(int occurence, Node leftChild, Node rightChild){
        this.occurence = occurence;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public void setParent(Node parent) {
        /**
         * This function will allow us to run through the tree in order to encode our message.
         */
        this.parent = parent;
    }

    public static Comparator<Node> nodeComparator = new Comparator<Node>() {

        public int compare(@NotNull Node n1, @NotNull Node n2) {
            /**
             * This comparator allows me to compare two nodes. As comparing two nodes doesn't mean anything normally,
             * I made it compare their occurence value.
             */
            int occurence1 = n1.occurence;
            int occurence2 = n2.occurence;
            return (occurence1 - occurence2);
        }
    };

    public String encode(String path, char target, ArrayList<Node> visitedNodes){
        /**
         * This is the heart of the encoding. This function will run through the tree trying to find the given character
         */
        if(this.key == target){ // Checks to see if it is the right character
            return(path);
        }

        if(this.leftChild != null && !(visitedNodes.contains(this.leftChild))){ // Calls itself for the
            // node's left child
            visitedNodes.add(this.leftChild);
            return(this.leftChild.encode(path + "0", target, visitedNodes));
        }

        else if(this.rightChild != null && !(visitedNodes.contains(this.rightChild))){ // Calls itself for
            // the node's right child
            visitedNodes.add(this.rightChild);
            return(this.rightChild.encode(path + "1", target, visitedNodes));
        }

        else if(this.parent != null){ // If it's at a dead end, it will go back to the parent node in order to
            // run through the entire tree if necessary
            path = path.substring(0, path.length() - 1);
            return(this.parent.encode(path, target, visitedNodes));
        }

        else{ // Self explanatory
            return("========================================\n" +
                    "| ENCODING ERROR : Character not found |\n" +
                    "========================================");
        }
    }
}