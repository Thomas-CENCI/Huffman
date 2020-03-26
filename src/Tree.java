import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

public class Tree{
    ArrayList<Node> nodeList;

    public void setNodeList(ArrayList<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public void createNodeList(@NotNull HashMap<Character, Integer> hashMap) {
        /**
         * Creates a list of all nodes corresponding to all the leaves of the tree
         */
        ArrayList<Character> keys = new ArrayList<Character>(hashMap.keySet());
        ArrayList<Node> nodeList = new ArrayList<Node>();
        for(char key : keys){
            Node node = new Node(key, (int) hashMap.get(key));
            nodeList.add(node);
            this.setNodeList(nodeList);
        }
    }

    public Node createTree(@NotNull ArrayList<Node> nodeList){
        /**
         * Creates the binary tree corresponding to the Huffman encoding
         */
        while(nodeList.size() > 1){
            Node node1 = nodeList.get(0);
            Node node2 = nodeList.get(1);

            // The parent node's occurence value is given the sum of its children's occurence value
            Node node = new Node((node1.occurence + node2.occurence), node1, node2);

            // The children nodes now recognize the new node as their parent
            node1.setParent(node);
            node2.setParent(node);

            // The two nodes are removed from the list
            nodeList.remove(node1);
            nodeList.remove(node2);

            // And the new one is added at the right place
            nodeList.add(node);
            Collections.sort(nodeList, Node.nodeComparator);

            createTree(nodeList);
        }
        return(nodeList.get(0));
    }
}