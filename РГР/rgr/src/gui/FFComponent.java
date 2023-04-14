package gui;


import algorithms.MaxFlow;
import utils.GraphOperation;
import utils.GraphVertex;
import utils.MaxFlowAndPathData;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Path2D;
import java.util.*;
import java.util.List;

public class FFComponent extends JPanel {
    public final static float VERTEX_SIZE = 40;
    private final static Color VERTEX_BACKGROUND_COLOR = Color.black;
    private final static Color VERTEX_TEXT_COLOR = Color.WHITE;
    private final static Color VERTEX_SOURCE_COLOR = Color.GREEN;
    private final static Color VERTEX_SINK_COLOR = Color.RED;
    private final static Font VERTEX_TEXT_SIZE = new Font("TimesRoman", Font.PLAIN, 18);
    private final static Color ARC_TEXT_COLOR = Color.RED;
    private final static Color ARC_COLOR = Color.black;
    private final static float LINE_THICKNESS = 2;
    private Map<Integer, SortedSet<Integer>> adjacencyList;
    private List<GraphVertex> vertices;
    public GraphVertex lastSelectedVertex = null;
    private GraphVertex selectedVertex = null;
    public GraphOperation operation;
    public int source;
    public int sink;
    public int iterationStep = 0;

    public MaxFlow algorithm;

    public FFComponent(Map<Integer, SortedSet<Integer>> adjacencyList, List<GraphVertex> vertices, MaxFlow algorithm) {
        this.adjacencyList = adjacencyList;
        this.vertices = new ArrayList<>();
        this.algorithm = algorithm;

        this.source = algorithm.source;
        this.sink = algorithm.sink;

        for (int vertexId : adjacencyList.keySet()) {
            Point location = new Point((int) (100 * Math.cos((double) vertexId / adjacencyList.size() * Math.PI * 2)) + 600,
                    (int) (100 * Math.sin((double) vertexId / adjacencyList.size() * Math.PI * 2)) + 250);
            this.vertices.add(new GraphVertex(vertexId, location));
        }

        this.operation = GraphOperation.MOVE;
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Point mouseLocation = e.getPoint();
                // якщо операція виконується над вершинами
                selectedVertex = getVertexAtLocation(mouseLocation);
                lastSelectedVertex = getVertexAtLocation(mouseLocation);

                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                selectedVertex = null;
            }

        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedVertex != null && operation == GraphOperation.MOVE) {
                    Point mouseLocation = e.getPoint();
                    Point vertexLocation = selectedVertex.getPosition();
                    int deltaX = mouseLocation.x - vertexLocation.x;
                    int deltaY = mouseLocation.y - vertexLocation.y;
                    selectedVertex.setPosition(new Point(mouseLocation.x, mouseLocation.y));
                    repaint();
                }
            }
        });

    }

    private GraphVertex getVertexAtLocation(Point location) {
        for (GraphVertex vertex : vertices) {
            double dist = vertex.distance(location);
            if (dist <= VERTEX_SIZE / 2) {
                return vertex;
            }
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.clearRect(0, 0, getWidth(), getHeight());
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(LINE_THICKNESS));

        // Намалюємо кожен ребро між вершинами
        for (var fromVertex : adjacencyList.entrySet()) {
            var fromId = fromVertex.getKey();
            for (GraphVertex toVertex : vertices) {
                var toId = toVertex.getId();
                if (adjacencyList.get(fromId).contains(toId)) {
                    Point from = vertices.stream()
                            .filter(vert -> vert.getId() == fromId)
                            .findAny()
                            .orElse(null)
                            .getPosition();
                    Point to = toVertex.getPosition();
//


                    if (isEdgeInPath(fromId, toId)) {
                        g2d.setColor(Color.RED);
                    } else {
                        g2d.setColor(ARC_COLOR);

                    }
                    //малювання стрілок
                    g2d.setStroke(new BasicStroke(LINE_THICKNESS*2));
                    int x1 = (int) from.getX();
                    int y1 = (int)from.getY();
                    int x2 = (int)to.getX();
                    int y2 = (int)to.getY();
                    double theta = Math.atan2(y2 - y1, x2 - x1);
                    double phi = Math.toRadians(4);
                    int barb = 30;
                    Path2D.Double path = new Path2D.Double();
                    double x = x2 - barb * Math.cos(theta + phi);
                    double y = y2 - barb * Math.sin(theta + phi);
                    path.moveTo(x, y);
                    path.lineTo(x2, y2);
                    x = x2 - barb * Math.cos(theta - phi);
                    y = y2 - barb * Math.sin(theta - phi);
                    path.moveTo(x, y);
                    path.lineTo(x2, y2);
                    g2d.draw(path);
                    g2d.setStroke(new BasicStroke(LINE_THICKNESS));

                    //малювання ребер
                    g2d.drawLine(from.x, from.y, to.x, to.y);

                    //draw text in center line
                    Point location = new Point((from.x + to.x) / 2, (from.y + to.y) / 2);
                    String w = String.valueOf(algorithm.graph[fromId][toId]);

                    FontMetrics fm = g2d.getFontMetrics();
                    int stringWidth = fm.stringWidth(w + "");
                    int stringHeights = fm.getHeight();

                    Point backgroundRect = new Point(30, 30);

                    // backgroundRect color
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(location.x - backgroundRect.x / 2, location.y - backgroundRect.y / 2, backgroundRect.x, backgroundRect.y);
                    g2d.setColor(ARC_TEXT_COLOR);
                    g2d.setFont(VERTEX_TEXT_SIZE);

                    g2d.drawString(algorithm.pathData.get(iterationStep).currentMinFlow[toId][fromId] +
                            "/" + algorithm.graph[fromId][toId], location.x - stringWidth / 2, location.y + stringHeights / 4);


                    g2d.setColor(Color.BLACK);
                    String message = "Path " + (iterationStep + 1) + ": " + algorithm.pathData.get(iterationStep).pathList + ",  min flow on path " + (iterationStep + 1) + " = " + algorithm.pathData.get(iterationStep).maxFlowVal +
                            ", max flow = " + algorithm.pathData.stream()
                            .mapToInt(MaxFlowAndPathData::getMaxFlowVal)
                            .sum();
                    ;

                    g2d.setFont(VERTEX_TEXT_SIZE);
                    g2d.drawString(message, getWidth() - getWidth() / 2, 30);

                }
            }
        }

        // Намалюємо кожну вершину
        for (var vertex : vertices) {

            int vertexId = vertex.getId();
            Point location = vertex.getPosition();

            FontMetrics fm = g2d.getFontMetrics();
            int stringWidth = fm.stringWidth(vertexId + "");
            int stringHeights = fm.getHeight();

            if (algorithm.source == vertexId) {
                g2d.setColor(VERTEX_SOURCE_COLOR);
            } else if (algorithm.sink == vertexId) {
                g2d.setColor(VERTEX_SINK_COLOR);
            } else {
                g2d.setColor(VERTEX_BACKGROUND_COLOR);
            }

            g2d.fillOval((int) (location.x - VERTEX_SIZE / 2),
                    (int) (location.y - VERTEX_SIZE / 2),
                    (int) VERTEX_SIZE, (int) VERTEX_SIZE);
            g2d.setColor(VERTEX_TEXT_COLOR);

            g2d.drawString(vertexId + "", location.x - stringWidth / 2, location.y + stringHeights / 4);

        }

        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF);
    }

    public boolean isEdgeInPath(int vertexFrom, int vertexTo) {

        if (algorithm.pathData.size() == 0)
            return false;

        var pathList = algorithm.pathData.get(iterationStep).pathList;
        for (int i = 0; i < pathList.size() - 1; i++) {
            if ((int) pathList.get(i) == vertexTo &&
                    (int) pathList.get(i + 1) == vertexFrom) {
                return true;
            }
        }

        return false;
    }
}
