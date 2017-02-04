package ru.ifmo.ctddev.isaev;

import java.awt.*;
import java.util.Collections;


/**
 * @author iisaev
 */
public class Leaf extends Node {
    public Leaf(Point point) {
        super(null, null, point.x, Collections.singletonList(new AssocPoint(point, -1, -1)));
    }
}
