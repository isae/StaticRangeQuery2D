package ru.ifmo.ctddev.isaev.util;

import ru.ifmo.ctddev.isaev.rangesearch.MyPoint;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;


/**
 * @author iisaev
 */
public class PointsGenerator {

    private final Random random = new Random();

    private final int screenWidth;

    private final int screenHeight;

    public PointsGenerator(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public Set<MyPoint> take(int number) {
        Set<MyPoint> points = new HashSet<>();
        while (points.size() != number) {
            points.add(new MyPoint(random.nextInt(screenWidth), random.nextInt(screenHeight)));
        }
        return points;
    }

    public MyPoint take() {
        return new MyPoint(random.nextInt(screenWidth), random.nextInt(screenHeight));
    }

}
