package ui;

import delegates.RestaurantDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MoreWindow extends JFrame implements ActionListener {
    private static final int WIDTH=800;
    private static final int HEIGHT=300;

    private RestaurantDelegate delegate;
    private JButton groupByButton;
    private JButton avgCostMenuButton;
    private JButton nestedGroupButton;
    private JButton divisionButton;

    public MoreWindow(RestaurantDelegate delegate) {
        this.delegate = delegate;
        createPanel();

        this.setTitle("More");
        this.setSize(WIDTH,HEIGHT);
        this.setBackground(Color.decode("#FE5F55"));
        this.setVisible(true);
    }

    private void createPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.gridy = 0;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(5,5,5,5);

        groupByButton = new JButton("Average cost for each menu category");
        groupByButton.addActionListener(this);
        avgCostMenuButton = new JButton("Average Cost of Each Category which have more than one menu item");
        avgCostMenuButton.addActionListener(this);
        nestedGroupButton = new JButton("Average cost of each category which is less than or equal to " +
                "all average cost for each category");
        nestedGroupButton.addActionListener(this);
        divisionButton = new JButton("Show menus that are served in all branches");
        divisionButton.addActionListener(this);
    
        panel.add(groupByButton,  g);
        g.gridy++;
        panel.add(avgCostMenuButton, g);
        g.gridy++;
        panel.add(nestedGroupButton, g);
        g.gridy++;
        panel.add(divisionButton, g);
        g.gridy++;

        this.add(panel);
    }

    private void showAggWithHaving() {
        new ShowAggWithHaving(delegate);
    }

    private void division() {
        new ShowDivision(delegate);
    }
    
    private void groupBy() { new showGroupBy(delegate);}

    private void nestedGroupBy() { new nestedGroupBy(delegate);}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == groupByButton) {
            groupBy();
        }
        if (e.getSource() == avgCostMenuButton) {
            showAggWithHaving();
        }
        if (e.getSource() == nestedGroupButton) {
            nestedGroupBy();
        }
        if (e.getSource() == divisionButton) {
            division();
        }
    }
}
