package huffman;

import binarytree.BinaryLeaf;
import binarytree.BinaryNode;
import binarytree.BinaryTree;
import com.google.common.base.Preconditions;

import java.util.*;

/**
 * Created by david on 3/1/16.
 */
public class HuffmanEncoder {
    public HuffmanEntity encode(String input) {
        HuffmanEntity entity = new HuffmanEntity();
        entity.setDecoded(input);

        CalculateOccurences(entity);

        CreatePriorityQueue(entity);

        CreateBinaryTree(entity);

        Compress(entity);

        return entity;
    }

    public HuffmanEntity decode(HuffmanEntity entity) {
        CreatePriorityQueue(entity);

        CreateBinaryTree(entity);

        Decompress(entity);
        return entity;
    }

    private void Decompress(HuffmanEntity entity) {
        Queue<Character> charsToDecode = new LinkedList<>();
        StringBuilder builder = new StringBuilder();
        BinaryNode local = entity.getBinaryTree().getBaseNode();

        for (char s : entity.getEncoded().toCharArray()) {
            charsToDecode.add(s);
        }

        while (!charsToDecode.isEmpty()) {
            char c = charsToDecode.remove();

            // 8 bits in a char
            for (int i = 7; i >= 0; i--) {
                // Get the bit
                boolean set = (c & (1 << i)) > 0;

                if (set) {
                    local = local.getNodeRight();
                } else {
                    local = local.getNodeLeft();
                }

                if (local instanceof BinaryLeaf) {
                    // Get the leaf value
                    BinaryLeaf leaf = (BinaryLeaf) local;
                    builder.append(((BinaryLeaf) local).getPayload());

                    // reset the tree
                    local = entity.getBinaryTree().getBaseNode();
                }
            }
        }

        entity.setDecoded(builder.toString());

        return;
    }

    /**
     * Creates binary tree based on priority queue
     *
     * @param entity
     */
    private void CreateBinaryTree(HuffmanEntity entity) {
        Preconditions.checkArgument(entity.getCharacterPriorityQueue() != null);

        // get pqueue
        PriorityQueue<BinaryNode> priorityQueue = entity.getCharacterPriorityQueue();

        // Construct tree
        while (priorityQueue.size() > 1) {
            BinaryNode nodeLeft = priorityQueue.remove();
            BinaryNode nodeRight = priorityQueue.remove();

            BinaryNode parent = new BinaryNode(nodeLeft.getWeight() + nodeRight.getWeight(), nodeLeft, nodeRight, null);

            nodeLeft.setParent(parent);
            nodeRight.setParent(parent);

            priorityQueue.add(parent);
        }

        BinaryTree tree = new BinaryTree(priorityQueue.remove());

        entity.setBinaryTree(tree);
    }


    /**
     * Finishes compression on a string
     *
     * @param entity
     */
    private void Compress(HuffmanEntity entity) {
        Preconditions.checkArgument(entity.getOccurenceMap() != null, "Occurence map was not set");
        Preconditions.checkArgument(entity.getCharacterPriorityQueue() != null, "Priority Queue was not set");

        List<Boolean> totalDecisions = new LinkedList<>();

        for (Character character : entity.getDecoded().toCharArray()) {
            totalDecisions.addAll(entity.getBinaryTree().findLeaf(character));


        }

        entity.setEncoded(encodeDecisionsToString(totalDecisions));
    }

    private String encodeDecisionsToString(List<Boolean> totalDecisions) {
        StringBuilder builder = new StringBuilder();

        Queue<Boolean> boolsToWrite = new LinkedList<>();

        totalDecisions.stream().forEach((bool) -> boolsToWrite.add(bool));

        while (!boolsToWrite.isEmpty()) {
            // Init empty character
            char s = 0;

            // Bit write
            for (int i = 7; i != -1; i--) {
                if (boolsToWrite.isEmpty()) break;
                boolean write = boolsToWrite.remove();

                if (write) {
                    s = (char) (s | (1 << i));
                } else {
                    s = (char) (s & ~(1 << i));
                }
            }

            builder.append(s);
        }

        return builder.toString();
    }

    /**
     * Adds a pqueue to a huffman entity
     *
     * @param entity
     */
    private void CreatePriorityQueue(final HuffmanEntity entity) {
        Preconditions.checkArgument(entity.getOccurenceMap() != null, "Occurence Map was not set yet!");

        Comparator<BinaryNode> binaryLeafComparator = new Comparator<BinaryNode>() {
            @Override
            public int compare(BinaryNode o1, BinaryNode o2) {
                if (o1.getWeight() < o2.getWeight())
                    return -1;
                else if (o1.getWeight() == o2.getWeight())
                    return 0;
                else
                    return 1;
            }
        };

        final PriorityQueue<BinaryNode> priorityQueue = new PriorityQueue<>(binaryLeafComparator);

        entity.getOccurenceMap().keySet().stream().forEach((key) -> priorityQueue.add(new BinaryLeaf(key, entity.getOccurenceMap().get(key))));

        entity.setCharacterPriorityQueue(priorityQueue);
    }

    /**
     * Generate an occurence map
     *
     * @param entity
     */
    private void CalculateOccurences(HuffmanEntity entity) {
        Preconditions.checkArgument(entity.getDecoded() != null, "No input message");
        String input = new String(entity.getDecoded());

        Map<Character, Integer> ret = new HashMap<>();

        for (Character s : input.toCharArray()) {
            ret.put(s, ret.containsKey(s) ? ret.get(s) + 1 : 1);
        }

        entity.setOccurenceMap(ret);
    }
}
