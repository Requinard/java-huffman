package binarytree;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by david on 3/1/16.
 */
public class BinaryTree {
    private BinaryNode baseNode;

    public BinaryNode getBaseNode() {
        return baseNode;
    }

    public BinaryTree(BinaryNode baseNode) {
        this.baseNode = baseNode;
    }

    public List<Boolean> findLeaf(Character character) {
        List<Boolean> decision = new LinkedList<>();

        Stack<BinaryNode> nodes = new Stack<>();

        nodes.add(baseNode);

        while (!nodes.empty()) {
            BinaryNode node = nodes.pop();

            if (node instanceof BinaryLeaf) {
                BinaryNode leaf = node;
                BinaryLeaf comp = (BinaryLeaf) node;
                if (comp.getPayload() == character) {
                    while (leaf != null) {
                        BinaryNode parent = leaf.getParent();

                        if (parent != null)
                            // if the parent node has us as left, we say false
                            decision.add(parent.getNodeRight() == leaf);

                        leaf = parent;
                    }

                    break;
                }
            }

            if (node.getNodeLeft() != null)
                nodes.add(node.getNodeLeft());
            if (node.getNodeRight() != null)
                nodes.add(node.getNodeRight());
        }

        Collections.reverse(decision);

        return decision;
    }
}
