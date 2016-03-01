package huffman;

import binarytree.BinaryLeaf;
import binarytree.BinaryNode;
import binarytree.BinaryTree;

import java.util.Map;
import java.util.PriorityQueue;

/**
 * Created by david on 3/1/16.
 */
public class HuffmanEntity {
    private Map<Character,Integer> occurenceMap;

    public PriorityQueue<BinaryNode> getCharacterPriorityQueue() {
        return characterPriorityQueue;
    }

    public BinaryTree getBinaryTree() {
        return binaryTree;
    }

    public void setBinaryTree(BinaryTree binaryTree) {
        this.binaryTree = binaryTree;
    }

    public BinaryTree binaryTree;

    public void setCharacterPriorityQueue(PriorityQueue<BinaryNode> characterPriorityQueue) {
        this.characterPriorityQueue = characterPriorityQueue;
    }

    private PriorityQueue<BinaryNode> characterPriorityQueue;
    private String decoded;
    private String encoded;

    public Map<Character, Integer> getOccurenceMap() {
        return occurenceMap;
    }

    public void setOccurenceMap(Map<Character, Integer> occurenceMap) {
        this.occurenceMap = occurenceMap;
    }

    public String getDecoded() {
        return decoded;
    }

    public void setDecoded(String decoded) {
        this.decoded = decoded;
    }

    public String getEncoded() {
        return encoded;
    }

    public void setEncoded(String encoded) {
        this.encoded = encoded;
    }
}
