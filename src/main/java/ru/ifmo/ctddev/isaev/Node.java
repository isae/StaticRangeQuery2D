package ru.ifmo.ctddev.isaev;

import java.util.ArrayList;
import java.util.List;


/**
 * @author iisaev
 */
public class Node {
    private Node left;

    private Node right;

    private final int xCoord;

    private List<AssocPoint> assoc = new ArrayList<>();

    public Node(Node left, Node right, int xCoord) {
        this.left = left;
        this.right = right;
        this.xCoord = xCoord;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
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
