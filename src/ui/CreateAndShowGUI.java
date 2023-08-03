package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CreateAndShowGUI implements ActionListener {
    private static final int WIDTH=1000;
    private static final int HEIGHT=700;
    
    private JButton insertButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton showButton;
    private JButton selectButton;
    private JButton filterButton;
    
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
    
    // create a table on the left side
    private JPanel createTablePanel() {
        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(800,600));
        panel.setLayout(new BorderLayout());
        
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(table);
        return panel;
    }
    
    // create buttons on the right side
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.gridy = 0;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(5,5,5,5);

        insertButton = new JButton("Insert");
        insertButton.addActionListener(this);
        updateButton = new JButton("Update");
        updateButton.addActionListener(this);
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);
        showButton = new JButton("Show");
        showButton.addActionListener(this);
        selectButton = new JButton("Select");
        selectButton.addActionListener(this);
        filterButton = new JButton("Filter");
        filterButton.addActionListener(this);

        panel.add(insertButton,g);
        g.gridy++;
        panel.add(updateButton, g);
        g.gridy++;
        panel.add(deleteButton,g);
        g.gridy++;
        panel.add(showButton,g);
        g.gridy++;
        panel.add(selectButton, g);
        g.gridy++;
        panel.add(filterButton,g);
        g.gridy++;
        return panel;
    }
    
    private void insert(JPanel popUp) {
        popUp.add(new JLabel("attribute"));
        JTextField name = new JTextField(6);
        
        popUp.add(name);
        JOptionPane.showConfirmDialog(null,popUp);
    }
    
    private void update(JPanel popUp) {
        JPanel dropDownPane = new JPanel();
        String[] attribute = {"atttribute1", "attribute2"};
        JComboBox dropDown = new JComboBox(attribute);
        dropDown.setEditable(false);
        dropDown.addActionListener(this);
        dropDownPane.add(dropDown);
        popUp.add(dropDownPane);
        Object[] options = { "Update", "Cancel" };
        int n = JOptionPane.showOptionDialog(null, popUp, "update", JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, null);
        
        if (n==1) {//tba
        } 
        
    }
    
    private void delete() {
        //tba
    }

    private void show(JPanel popUp) {
        popUp.add(new JLabel("show"));
    }

    private void select(JPanel popUp) {
        JPanel dropDownPane = new JPanel();
        String[] attribute = {"menu", "reservation", "branch"};
        JComboBox dropDown = new JComboBox(attribute);
        dropDown.setEditable(false);
        dropDown.addActionListener(this);
        dropDownPane.add(dropDown);
        popUp.add(dropDownPane);
        Object[] options = { "Show", "Cancel" };
        int n = JOptionPane.showOptionDialog(null, popUp, "Select", JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, null);

        if (n==1) {//tba
        }
    }

    private void filter(JPanel popUp) {
        JPanel dropDownPane = new JPanel();
        String[] attribute = {"atttribute1", "attribute2"};
        JComboBox dropDown = new JComboBox(attribute);
        dropDown.setEditable(false);
        dropDown.addActionListener(this);
        dropDownPane.add(dropDown);
        popUp.add(dropDownPane);
        Object[] options = { "Show", "Cancel" };
        int n = JOptionPane.showOptionDialog(null, popUp, "Join", JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, null);

        if (n==1) {//tba
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insertButton) {
            insert(new JPanel());
        }
        if (e.getSource() == updateButton) {
            update(new JPanel());
        }
        if (e.getSource() == deleteButton) {
            delete();
        }
        if (e.getSource() == showButton) {
            show(new JPanel());
        }
        if (e.getSource() == selectButton) {
            select(new JPanel());
        }
        if (e.getSource() == filterButton) {
            filter(new JPanel());
        }
    }
}
