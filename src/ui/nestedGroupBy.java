package ui;

import delegates.RestaurantDelegate;
import model.MenuSorted;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class nestedGroupBy extends JFrame{
    private static final int WIDTH=800;
    private static final int HEIGHT=300;

    private JTable table;
    private DefaultTableModel model;
    private RestaurantDelegate delegate;
    public nestedGroupBy(RestaurantDelegate delegate) {
        this.delegate = delegate;

        JPanel tablePanel = createTable();

        this.add(tablePanel);

        this.setTitle("nested group by");
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
        nestedGroup();
        table.setModel(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panel.add(scrollPane);
        return panel;
    }

    private void nestedGroup() {
        MenuSorted groupMenu = delegate.showNestedAggregation();
        model.addColumn("Category");
        model.addColumn("Cost");
        for (Vector<Object> tuple : groupMenu.getTuples()) {
            model.addRow(tuple);
        }
    }
}
