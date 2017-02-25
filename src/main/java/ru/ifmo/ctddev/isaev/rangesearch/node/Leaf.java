package ru.ifmo.ctddev.isaev.rangesearch.node;

import ru.ifmo.ctddev.isaev.rangesearch.MyPoint;

import java.util.Collections;


/**
 * @author iisaev
 */
public class Leaf extends Node {
    public Leaf(MyPoint point) {
        super(null, null, point.x, Collections.singletonList(new AssocPoint(point, -1, -1)));
    }
}
