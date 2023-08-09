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

    private JButton avgCostMenuButton;
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

        avgCostMenuButton = new JButton("Average Cost of Each Category which have more than one menu item");
        avgCostMenuButton.addActionListener(this);
        divisionButton = new JButton("Show menus that are served in all branches");
        divisionButton.addActionListener(this);

        panel.add(avgCostMenuButton, g);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == avgCostMenuButton) {
            showAggWithHaving();
        }
        if (e.getSource() == divisionButton) {
            division();
        }
    }
}
