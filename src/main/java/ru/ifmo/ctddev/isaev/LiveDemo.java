package ru.ifmo.ctddev.isaev;

import ru.ifmo.ctddev.isaev.rangesearch.MyPoint;
import ru.ifmo.ctddev.isaev.rangesearch.NaiveRangeSearch;
import ru.ifmo.ctddev.isaev.rangesearch.RangeSearch;
import ru.ifmo.ctddev.isaev.rangesearch.RangeTreeSearch;
import ru.ifmo.ctddev.isaev.rangesearch.node.Rect;
import ru.ifmo.ctddev.isaev.util.PointsGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class LiveDemo extends JFrame {

    private static final int POINT_SIZE = 5;

    private static int numberOfPoints = 500;

    private static final int screenWidth;

    private static final int screenHeight;

    private final MyPoint[] startDrag = new MyPoint[1];

    private final MyPoint[] endDrag = new MyPoint[1];

    private final List<MyPoint> redPoints = new ArrayList<>();

    private final List<MyPoint> bluePoints = new ArrayList<>();

    private RangeSearch rangeSearch;

    private NaiveRangeSearch naiveSearch;

    static {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int) screenSize.getWidth();
        screenHeight = (int) screenSize.getHeight();
    }

    private void generatePoints() {

        redPoints.clear();
        bluePoints.clear();
        redPoints.addAll(
                new PointsGenerator(screenWidth, screenHeight)
                        .take(numberOfPoints)
                        .stream()
                        .collect(Collectors.toList())
        );
        rangeSearch = new RangeTreeSearch(redPoints);
        naiveSearch = new NaiveRangeSearch(redPoints);
    }

    private LiveDemo() {
        initComponents();
        generatePoints();
    }

    private void drawPoint(Graphics g, MyPoint p, Color color) {
       /* Graphics pointEnclosure = g.create(
                p.x - 3 * POINT_SIZE,
                p.y - 3 * POINT_SIZE,
                POINT_SIZE * 7,
                POINT_SIZE * 7);*/
       /* pointEnclosure.setColor(Color.BLACK);
        pointEnclosure.setFont(new Font("Arial", Font.PLAIN, 8));*/
        Graphics point = g.create(p.x, p.y, POINT_SIZE, POINT_SIZE);
        point.drawOval(0, 0, POINT_SIZE, POINT_SIZE);
        point.setColor(color);
        point.fillOval(0, 0, POINT_SIZE, POINT_SIZE);
        //pointEnclosure.drawString(String.format("%s/%s", p.x, p.y), 0, POINT_SIZE * 5 / 2);
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
                    Rect rect = new Rect(startDrag[0], endDrag[0]);
                    g2d.drawRect(rect.getFromX(), rect.getFromY(), rect.getWidth(), rect.getHeight());
                }
            }

        };
        JButton generateButton = new JButton("Refresh");
        generateButton.addActionListener(e -> {
            generatePoints();
            repaint();
        });
        //mainPanel.add(generateButton);
        this.add(mainPanel);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mainPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                bluePoints.clear();
                repaint();
                startDrag[0] = null;
                endDrag[0] = null;
                startDrag[0] = new MyPoint(evt.getX(), evt.getY());
                endDrag[0] = startDrag[0];
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                bluePoints.clear();
                try {
                    bluePoints.addAll(rangeSearch.query(startDrag[0], endDrag[0], POINT_SIZE));
                    naiveSearch.query(startDrag[0], endDrag[0], POINT_SIZE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                repaint();
            }
        });
        mainPanel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent evt) {
                endDrag[0] = new MyPoint(evt.getX(), evt.getY());
                repaint();
            }
        });
    }

    public static void main(String args[]) {
        if (args.length > 0) {
            numberOfPoints = Integer.parseInt(args[0]);
        }
        java.awt.EventQueue.invokeLater(() -> {

            LiveDemo liveDemo = new LiveDemo();
            liveDemo.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            liveDemo.setSize(screenWidth, screenHeight);
            liveDemo.setVisible(true);
        });
    }

}