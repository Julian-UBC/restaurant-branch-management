package ui;

import delegates.RestaurantDelegate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ShowAggWithHaving extends JFrame {
    private static final int WIDTH=800;
    private static final int HEIGHT=300;

    private JTable table;
    private DefaultTableModel model;
    private RestaurantDelegate delegate;

    public ShowAggWithHaving(RestaurantDelegate delegate) {
        this.delegate = delegate;

        JPanel tablePanel = createTable();

        this.add(tablePanel);

        this.setTitle("Menus that are served in all branches");
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
        //showAggWithHaving();
        table.setModel(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panel.add(scrollPane);
        return panel;
    }

//    private void showAggWithHaving() {
//        MenusAvgCost menus = delegate.showAvgCostMenuHaving();
//        for (String column : menus.getColumns()) {
//            model.addColumn(column);
//        }
//        for (Vector<Object> tuple : menus.getTuples()) {
//            model.addRow(tuple);
//        }
//    }
}
