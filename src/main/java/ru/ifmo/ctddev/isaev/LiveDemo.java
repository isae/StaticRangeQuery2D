package ru.ifmo.ctddev.isaev;

import ru.ifmo.ctddev.isaev.rangesearch.NaiveRangeSearch;
import ru.ifmo.ctddev.isaev.rangesearch.RangeSearch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class LiveDemo extends JFrame {

    private static final int POINT_SIZE = 5;

    private static final int POINTS_NUMBER = 30;

    private static final Random random = new Random();

    private static final int screenWidth;

    private static final int screenHeight;

    private final Point[] startDrag = new Point[1];

    private final Point[] endDrag = new Point[1];

    private final List<Point> redPoints = new ArrayList<>();

    private final List<Point> bluePoints = new ArrayList<>();

    private RangeSearch rangeSearch;

    static {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int) screenSize.getWidth();
        screenHeight = (int) screenSize.getHeight();
    }

    private void generatePoints() {
        List<Point> points = IntStream.range(0, POINTS_NUMBER)
                .mapToObj(i -> new Point(random.nextInt(screenWidth), random.nextInt(screenHeight)))
                .collect(Collectors.toList());
        Set<Point> xSet = new TreeSet<>(Comparator.comparingInt(point -> point.x));
        xSet.addAll(points);
        Set<Point> ySet = new TreeSet<>(Comparator.comparingInt(point -> point.y));
        ySet.addAll(xSet);
        redPoints.clear();
        redPoints.addAll(ySet);
        rangeSearch = new NaiveRangeSearch(redPoints);
    }

    public LiveDemo() {
        initComponents();
        generatePoints();
    }

    void drawPoint(Graphics g, Point p, Color color) {
        Graphics pointEnclosure = g.create(
                p.x - 3 * POINT_SIZE,
                p.y - 3 * POINT_SIZE,
                POINT_SIZE * 7,
                POINT_SIZE * 7);
        pointEnclosure.setColor(Color.BLACK);
        pointEnclosure.setFont(new Font("Arial", Font.PLAIN, 8));
        Graphics point = g.create(p.x, p.y, POINT_SIZE, POINT_SIZE);
        point.drawOval(0, 0, POINT_SIZE, POINT_SIZE);
        point.setColor(color);
        point.fillOval(0, 0, POINT_SIZE, POINT_SIZE);
        pointEnclosure.drawString(String.format("%s/%s", p.x, p.y), 0, POINT_SIZE * 5 / 2);
    }

    private Rectangle2D.Float makeRectangle(int x1, int y1, int x2, int y2) {
        return new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                super.paint(g2d);
                redPoints.forEach(p -> drawPoint(g, p, Color.RED));
                bluePoints.forEach(p -> drawPoint(g, p, Color.BLUE));
                if (startDrag[0] != null && endDrag[0] != null) {
                    g2d.setPaint(Color.GREEN);
                    Shape r = makeRectangle(startDrag[0].x, startDrag[0].y, endDrag[0].x, endDrag[0].y);
                    g2d.draw(r);
                }
            }
        };
        JButton generateButton = new JButton("Refresh");
        generateButton.addActionListener(e -> {
            generatePoints();
            repaint();
        });
        mainPanel.add(generateButton);
        this.add(mainPanel);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mainPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                startDrag[0] = null;
                endDrag[0] = null;
                bluePoints.clear();
                repaint();
                startDrag[0] = new Point(evt.getX(), evt.getY());
                endDrag[0] = startDrag[0];
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                bluePoints.clear();
                bluePoints.addAll(rangeSearch.query(startDrag[0], endDrag[0]));
                repaint();
            }
        });
        mainPanel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent evt) {
                endDrag[0] = new Point(evt.getX(), evt.getY());
                repaint();
            }
        });
    }

    //set ui visible//
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {

            LiveDemo liveDemo = new LiveDemo();
            liveDemo.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            liveDemo.setSize(screenWidth, screenHeight);
            liveDemo.setVisible(true);
        });
    }

}