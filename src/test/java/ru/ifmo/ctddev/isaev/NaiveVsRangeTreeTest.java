package ru.ifmo.ctddev.isaev;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.ifmo.ctddev.isaev.rangesearch.MyPoint;
import ru.ifmo.ctddev.isaev.rangesearch.NaiveRangeSearch;
import ru.ifmo.ctddev.isaev.rangesearch.RangeTreeSearch;
import ru.ifmo.ctddev.isaev.util.PointsGenerator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;


/**
 * @author iisaev
 */
@RunWith(Parameterized.class)
public class NaiveVsRangeTreeTest {

    private static final int SCREEN_WIDTH = 2000;

    private static final int SCREEN_HEIGHT = 2000;

    private static final PointsGenerator POINTS_GEN = new PointsGenerator(SCREEN_WIDTH, SCREEN_HEIGHT);

    private static final int NUMBER_OF_POINTS = 10000;

    private static final int NUMBER_OF_TESTS = 500;

    private static NaiveRangeSearch naiveRangeSearch;

    private static RangeTreeSearch rangeTreeSearch;

    public NaiveVsRangeTreeTest(MyPoint point1, MyPoint point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    @BeforeClass
    public static void generatePoints() throws IOException {
        try (FileReader fileReader = new FileReader("src/test/resources/dataset.txt");
             BufferedReader reader = new BufferedReader(fileReader)) {
            Set<MyPoint> points = reader.lines().map(line -> {
                String[] tokens = line.split("\\s+");
                return new MyPoint(Integer.valueOf(tokens[0]), Integer.valueOf(tokens[1]));
            }).collect(Collectors.toSet());
            assertEquals("Number of points is equal to expected", NUMBER_OF_POINTS, points.size());
            naiveRangeSearch = new NaiveRangeSearch(new ArrayList<>(points));
            rangeTreeSearch = new RangeTreeSearch(new ArrayList<>(points));
        }
    }

    @Parameterized.Parameters
    public static List<Object[]> getTestData() throws IOException {
        try (FileReader fileReader = new FileReader("src/test/resources/queries.txt");
             BufferedReader reader = new BufferedReader(fileReader)) {
            List<Object[]> testData = reader.lines().map(line -> {
                String[] tokens = line.split("\\s+");
                return new Object[] {
                        new MyPoint(Integer.valueOf(tokens[0]), Integer.valueOf(tokens[1])),
                        new MyPoint(Integer.valueOf(tokens[2]), Integer.valueOf(tokens[3]))
                };
            }).collect(Collectors.toList());
            assertEquals("Number of queries is equal to expected", NUMBER_OF_TESTS, testData.size());
            return testData;
        }
    }

    private final MyPoint point1;

    private final MyPoint point2;

    @Test
    public void test() {
        List<MyPoint> naiveResult = naiveRangeSearch.query(point1, point2, 0);
        List<MyPoint> rangeTreeResult = rangeTreeSearch.query(point1, point2, 0);
        System.out.format("Point 1: %s, Point 2: %s\n", point1, point2);
        System.out.format("Query: %s\n", getQuery());
        Set<MyPoint> naiveResultSet = new HashSet<>(naiveResult);
        Set<MyPoint> rangeTreeResultSet = new HashSet<>(rangeTreeResult);
        assertEquals("All naive algorithm results are unique", naiveResult.size(), naiveResultSet.size());
        assertEquals("All range tree results are unique", rangeTreeResult.size(), rangeTreeResultSet.size());
        Set<MyPoint> naiveCopy = new HashSet<>(naiveResultSet);
        naiveCopy.removeAll(rangeTreeResultSet);
        assertEquals("Not present in range result" + Arrays.toString(naiveCopy.toArray()), 0, naiveCopy.size());
        rangeTreeResultSet.removeAll(naiveResultSet);
        assertEquals("Naive and range tree results are equal" + Arrays.toString(rangeTreeResultSet.toArray()), 0, rangeTreeResultSet.size());
    }

    private String getQuery() {
        int xMin = Math.min(point1.x, point2.x);
        int xMax = Math.max(point1.x, point2.x);
        int yMin = Math.min(point1.y, point2.y);
        int yMax = Math.max(point1.y, point2.y);
        return String.format("(%s..%s,%s..%s)", xMin, xMax, yMin, yMax);
    }
}
