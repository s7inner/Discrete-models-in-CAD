package utils;

import java.awt.*;
import java.util.Objects;

public class GraphVertex {
    private int id;
    Point position;
    Color color;

    public GraphVertex(int id, Point position) {
        this.id = id;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphVertex that = (GraphVertex) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public double distance(Point location) {
        return position.distance(location.x,location.y);
    }
}


