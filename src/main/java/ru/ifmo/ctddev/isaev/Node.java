package ru.ifmo.ctddev.isaev;

import java.util.ArrayList;
import java.util.List;


/**
 * @author iisaev
 */
public class Node {
    private final Node left;

    private final Node right;

    private final int xCoord;

    private List<AssocPoint> assoc = new ArrayList<>();

    public Node(Node left, Node right, int xCoord, List<AssocPoint> assoc) {
        this.left = left;
        this.right = right;
        this.xCoord = xCoord;
        this.assoc = assoc;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    public int getxCoord() {
        return xCoord;
    }

    public List<AssocPoint> getAssoc() {
        return assoc;
    }
}
