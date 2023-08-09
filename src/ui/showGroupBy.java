package ui;

import delegates.RestaurantDelegate;
import model.MenuSorted;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class showGroupBy extends JFrame {
    private static final int WIDTH=800;
    private static final int HEIGHT=300;

    private JTable table;
    private DefaultTableModel model;
    private RestaurantDelegate delegate;
    public showGroupBy(RestaurantDelegate delegate) {
        this.delegate = delegate;
        
        JPanel tablePanel = createTable();

        this.add(tablePanel);

        this.setTitle("Find average cost of the menu group by category");
        this.setSize(WIDTH,HEIGHT);
        this.setBackground(Color.decode("#FE5F55"));
        this.setVisible(true);
    }

    private JPanel createTable() {
        table = new JTable();
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(800,600));
        panel.setLayout(new BorderLayout());

        model = new DefaultTableModel();
        GroupBy();
        table.setModel(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panel.add(scrollPane);
        return panel;
    }

    private void GroupBy() {
        MenuSorted groupMenu = delegate.showGroupBy();
        model.addColumn("Category");
        model.addColumn("Cost");
        for (Vector<Object> tuple : groupMenu.getTuples()) {
            model.addRow(tuple);
        }
    }
}
