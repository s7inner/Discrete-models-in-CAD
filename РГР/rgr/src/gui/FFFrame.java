package gui;


import algorithms.GraphLoader;
import algorithms.MaxFlow;
import utils.GraphOperation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class FFFrame extends JFrame {
    Font font = new Font("TimesRoman", Font.PLAIN, 18);
    JButton moveVertexOperation =  new JButton("Move vertex");;
    JButton algorithmNextIteration = new JButton("The next iteration");
    JButton setSourceSink = new JButton("Set Source/Sink");
    JLabel sourceLabel = new JLabel("Source:");
    JLabel sinkLabel = new JLabel("Sink:");
    JTextField sourceField = new JTextField();
    JTextField sinkField = new JTextField();
    JPanel toolPanel;

    public File file;
    FFComponent comp;
    MaxFlow algorithm;
    public FFFrame(int width, int height, File file, MaxFlow algorithm, GraphLoader graphLoader) throws FileNotFoundException {
        this.algorithm = algorithm;
        setSize(width,height);
        setResizable(false);

        moveVertexOperation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comp.operation = GraphOperation.MOVE;
            }
        });

        setSourceSink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!sourceField.getText().isEmpty() && !sinkField.getText().isEmpty()) {
                    comp.algorithm.source = Integer.parseInt(sourceField.getText());
                    comp.algorithm.sink = Integer.parseInt(sinkField.getText());
                    try {
                        comp.algorithm = new MaxFlow(graphLoader.getAdjacencyMatrix(),graphLoader.getAdjacencyMatrix(), comp.algorithm.source, comp.algorithm.sink);
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    comp.algorithm.findMaxFlow();
                    comp.repaint();
                }
            }
        });

        algorithmNextIteration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comp.iterationStep++;
                comp.iterationStep=comp.iterationStep%comp.algorithm.pathData.size();
                comp.repaint();


            }
        });

        // Створюємо вікно та панель для візуалізації графу
        GraphLoader gl = new GraphLoader(file, true);
        var adjacencyList = gl.getAdjacencyList();
        setTitle("Graph Visualization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        comp = new FFComponent(adjacencyList, new ArrayList<>(), algorithm);
        comp.setSize(new Dimension(250, 250));
        comp.setMinimumSize(new Dimension(200,200));
        add(comp);

        toolPanel = new JPanel();

        toolPanel.add(moveVertexOperation);
        toolPanel.add(algorithmNextIteration);

        toolPanel.add(sourceLabel);
        sourceField.setPreferredSize(new Dimension(35, 30));
        sourceField.setFont(font);
        toolPanel.add(sourceField);
        toolPanel.add(sinkLabel);
        sinkField.setPreferredSize(new Dimension(35, 30));
        sinkField.setFont(font);
        toolPanel.add(sinkField);

        toolPanel.add(setSourceSink);
        add(toolPanel,BorderLayout.NORTH);

        setVisible(true);

    }
}
