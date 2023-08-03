package ui;

import javax.swing.*;
import java.awt.*;


public class CreateAndShowGUI {
    private static final int WIDTH=1000;
    private static final int HEIGHT=700;
    
    public CreateAndShowGUI() {
        JFrame frame = new JFrame();

        JPanel pane = new JPanel();
        JPanel leftPane = createTablePanel();
        JPanel rightPane = createButtonPanel();
        
        pane.add(leftPane, BorderLayout.LINE_START);
        pane.add(rightPane, BorderLayout.LINE_END);
        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, rightPane);

        sp.setOrientation(SwingConstants.VERTICAL);
        frame.add(sp);
        frame.setTitle("Restaurant Management");
        frame.setSize(WIDTH,HEIGHT);
        frame.setBackground(Color.decode("#FE5F55"));
        frame.setVisible(true);
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(700,600));
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        JButton insertButton = new JButton("Insert");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton showButton = new JButton("Show");
        JButton selectButton = new JButton("Select");
        JButton filterButton = new JButton("Filter");
        panel.add(insertButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(showButton);
        panel.add(selectButton);
        panel.add(filterButton);
        panel.setLayout(new GridLayout(6,1));
        return panel;
    }
}
