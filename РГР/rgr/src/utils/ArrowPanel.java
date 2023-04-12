package utils;

import java.awt.*;
import java.awt.geom.Point2D;

public class ArrowPanel {

    public Point start;
    public Point end;

    public ArrowPanel(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public void drawArrow(Point from, Point to, int ARR_SIZE, float VERTEX_SIZE, Graphics2D g2d) {
//        double arrowAngle = Math.PI / 12;
//        double dx = end.x - start.x, dy = end.y - start.y;
//        double angle = Math.atan2(dy, dx);
//
//        Point2D dir = difference(from, to);
//        dir = normalize(dir);
//        dir.setLocation(dir.getX() * VERTEX_SIZE , dir.getY() * VERTEX_SIZE );
//
//
//        int x1 = (int) (end.x - ARR_SIZE * Math.cos(angle - Math.PI - arrowAngle));
//        int y1 = (int) (end.y - ARR_SIZE * Math.sin(angle - Math.PI - arrowAngle));
//        int x2 = (int) (end.x - ARR_SIZE * Math.cos(angle - Math.PI + arrowAngle));
//        int y2 = (int) (end.y - ARR_SIZE * Math.sin(angle - Math.PI + arrowAngle));

        // визначаємо координати трикутника на кінці ребра
        int[] xPoints = {end.x - 40, end.x, end.x + 40};
        int[] yPoints = {end.y + 40, end.y, end.y + 40};

        // малюємо трикутник
        g2d.fillPolygon(xPoints, yPoints, 3);


//        g2d.fillPolygon(new int[]{x1, x2, (int) (end.x + dir.getX()), x1},
//                new int[]{y1, y2, (int) (end.y + dir.getY()), y1},
//                3);
//        g2d.drawLine(end.x + (int) dir.getX(), end.y + (int) dir.getY(), x1, y1);
//        g2d.drawLine(end.x + (int) dir.getX(), end.y + (int) dir.getY(), x2, y2);
    }

    public Point difference(Point p1, Point p2) {
        int x = p1.x - p2.x;
        int y = p1.y - p2.y;
        return new Point(x, y);
    }

    public Point2D normalize(Point2D point) {
        double length = Math.sqrt(point.getX() * point.getX() + point.getY() * point.getY());
        if (length != 0.0) {
            point.setLocation(point.getX() / length, point.getY() / length);
        }
        return point;
    }

}
