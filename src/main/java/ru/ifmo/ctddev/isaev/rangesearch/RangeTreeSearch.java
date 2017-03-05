package ru.ifmo.ctddev.isaev.rangesearch;

import ru.ifmo.ctddev.isaev.rangesearch.node.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static ru.ifmo.ctddev.isaev.util.PointComparators.*;


/**
 * @author iisaev
 */
public class RangeTreeSearch extends RangeSearch {
    private Node tree;

    public RangeTreeSearch(List<MyPoint> points) {
        super(points);
        points.sort(BY_X);
        tree = buildNode(points);
        boolean f = false;
    }

    private Node buildNode(List<MyPoint> points) {
        if (points.size() > 1) {
            return buildComplexNode(points);
        } else {
            return new Leaf(points.get(0));
        }
    }

    private Node buildComplexNode(List<MyPoint> points) {
        List<MyPoint> sortedByY = new ArrayList<>(points);
        sortedByY.sort(BY_Y);

        int medianIndex = points.size() / 2;
        int leftSize = medianIndex + points.size() % 2;
        MyPoint median = points.get(medianIndex);
        List<MyPoint> leftList = points.subList(0, leftSize);
        List<MyPoint> leftSortedByY = new ArrayList<>(leftList);
        leftSortedByY.sort(BY_Y);
        Node left = buildNode(leftList);

        List<MyPoint> rightList = points.subList(leftSize, points.size());
        List<MyPoint> rightSortedByY = new ArrayList<>(rightList);
        rightSortedByY.sort(BY_Y);
        Node right = buildNode(rightList);

        List<AssocPoint> assoc = new ArrayList<>();
        sortedByY.forEach(point -> {
            Integer leftIndex = getAssocIndex(leftSortedByY, point);
            Integer rightIndex = getAssocIndex(rightSortedByY, point);
            assoc.add(new AssocPoint(point, leftIndex, rightIndex));
        });

        assoc.sort(ASSOC_BY_Y);
        if (left.getAssoc().size() + right.getAssoc().size() != assoc.size()) {
            boolean f = true;
            throw new IllegalStateException();
        }

        return new Node(left, right, median.x, assoc);
    }

    private Integer getAssocIndex(List<MyPoint> leftSortedByY, MyPoint point) {
        int pos = Collections.binarySearch(leftSortedByY, point, BY_Y);
        int res = pos >= 0 ? pos : -pos - 1;
        return res == leftSortedByY.size() ? null : res;
    }

    private Integer getAssocIndex(List<AssocPoint> leftSortedByY, AssocPoint point) {
        int pos = Collections.binarySearch(leftSortedByY, point, ASSOC_BY_Y);
        int res = pos >= 0 ? pos : -pos - 1;
        return res == leftSortedByY.size() ? null : res;
    }

    @Override
    public List<MyPoint> query(MyPoint point1, MyPoint point2, int topBias) {
        long from = System.currentTimeMillis();
        Rect rect = new Rect(point1, point2);
        int fromX = rect.getFromX() - topBias;
        int toX = rect.getToX();
        int fromY = rect.getFromY() - topBias;
        int toY = rect.getToY();
        Node vSplit = findSplitNode(fromX, toX, tree);
        if (vSplit == null) {
            return emptyList();
        }
        Integer yStartIndex = getAssocIndex(vSplit.getAssoc(), new AssocPoint(new MyPoint(-1, fromY), null, null));
        if (vSplit.isLeaf() && yStartIndex != null) {
            return singletonList(vSplit.getAssoc().get(0).getPoint());
        }
        if (yStartIndex == null) {
            return emptyList();
        }

        AssocPoint parentPoint = vSplit.getAssoc().get(yStartIndex);
        List<Pair<List<AssocPoint>, Integer>> subtrees = new ArrayList<>();
        traverseToLeft(vSplit.getLeft(), fromX, parentPoint.getLeftIndex(), subtrees);
        traverseToRight(vSplit.getRight(), toX, parentPoint.getRightIndex(), subtrees);
        List<MyPoint> result = new ArrayList<>();
        subtrees.forEach(pair -> {
            List<AssocPoint> list = pair.getFirst();
            Integer index = pair.getSecond();
            if (index != null) {
                while (index < list.size() && list.get(index).getPoint().y <= toY) {
                    MyPoint pointToAdd = list.get(index).getPoint();
                    result.add(pointToAdd);
                    ++index;
                }
            }
        });
        long to = System.currentTimeMillis();
        System.out.println(String.format("Range tree: spent %s milliseconds", to - from));
        return result;
    }

    private void traverseToRight(Node node, int toX, Integer assocIndex, List<Pair<List<AssocPoint>, Integer>> subtrees) {
        if (node == null) {
            return;
        }
        if (assocIndex == null) {
            return;
        }
        if (node.isLeaf() && toX >= node.getxCoord()) {
            subtrees.add(new Pair<>(node.getAssoc(), assocIndex));
            return;
        }
        AssocPoint currentPoint = node.getAssoc().get(assocIndex);
        if (toX >= node.getxCoord()) {
            if (node.getLeft() != null) { // almost never happen
                Integer leftIndex = currentPoint.getLeftIndex();
                if (leftIndex != null && leftIndex > 0) {
                    leftIndex -= 1;
                }
                subtrees.add(new Pair<>(node.getLeft().getAssoc(), leftIndex));
            }
            traverseToRight(node.getRight(), toX, currentPoint.getRightIndex(), subtrees);
        } else {
            traverseToRight(node.getLeft(), toX, currentPoint.getLeftIndex(), subtrees);
        }
    }

    private void traverseToLeft(Node node, int fromX, Integer assocIndex, List<Pair<List<AssocPoint>, Integer>> subtrees) {
        if (node == null) {
            return;
        }
        if (assocIndex == null) {
            return;
        }
        if (node.isLeaf()) {
            if (fromX <= node.getxCoord()) {
                subtrees.add(new Pair<>(node.getAssoc(), assocIndex));
            }
            return;
        }
        AssocPoint currentPoint = node.getAssoc().get(assocIndex);
        if (fromX <= node.getxCoord()) {
            if (node.getRight() != null) {
                Integer rightIndex = currentPoint.getRightIndex();
                if (rightIndex != null && rightIndex > 0) {
                    rightIndex -= 1;
                }
                subtrees.add(new Pair<>(node.getRight().getAssoc(), rightIndex));
            }
            traverseToLeft(node.getLeft(), fromX, currentPoint.getLeftIndex(), subtrees);
        } else {
            traverseToLeft(node.getRight(), fromX, currentPoint.getRightIndex(), subtrees);
        }
    }

    private Node findSplitNode(int fromX, int toX, Node tree) {
        if (tree == null) {
            return null;
        }
        if (fromX <= tree.getxCoord() && toX > tree.getxCoord()) {
            return tree;
        }
        if (toX <= tree.getxCoord()) {
            return findSplitNode(fromX, toX, tree.getLeft());
        } else {
            return findSplitNode(fromX, toX, tree.getRight());
        }
    }
}
