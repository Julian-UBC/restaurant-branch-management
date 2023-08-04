package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class CreateAndShowGUI implements ActionListener {
    private static final int WIDTH=1000;
    private static final int HEIGHT=700;


    private JTable table;
    private DefaultTableModel model;
    
    private JButton insertButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton showButton;
    private JButton selectButton;
    private JButton filterButton;
    private JButton selectOption1;
    private JButton selectOption2;

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
        table = new JTable();
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(800,600));
        panel.setLayout(new BorderLayout());

        model = new DefaultTableModel();

        table.setModel(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panel.add(scrollPane);
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
        selectOption1 = new JButton("Option 1");
        selectOption1.addActionListener(this);
        selectOption2 = new JButton("Option 2");
        selectOption2.addActionListener(this);

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
        panel.add(selectOption1, g);
        g.gridy++;
        panel.add(selectOption2,g);
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
        JTextField newValue = new JTextField(20);
        newValue.setToolTipText("Enter new value here");

        popUp.setLayout(new BoxLayout(popUp, BoxLayout.Y_AXIS));
        popUp.add(new JLabel("Select the attribute you would like to update and enter the new value."));
        popUp.add(dropDownPane);
        popUp.add(newValue);
        Object[] options = { "Update", "Cancel" };
        int n = JOptionPane.showOptionDialog(null, popUp, "update", JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, null);
        
        if (n==1) {//tba
        } 
        
    }
    
    private void delete() {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

        if (table.getSelectedRowCount() == 1) {
            tableModel.removeRow(table.getSelectedRow());
        } else {
            // do nothing or throw error
        }
    }

    private void show(JPanel popUp) {
        popUp.add(new JLabel("Show the Earliest Reservation Times Grouped by Location"));
        Object[] options = {"Show", "Cancel"};
        int n = JOptionPane.showOptionDialog(null, popUp, "Show", JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, null);

        if (n == 1) {//tba
        }
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

    private void selectOption(int option) {
        // Clear the existing columns and rows from the model
        model.setColumnCount(0);
        model.setRowCount(0);

        if (option == 1) {
            model.addColumn("Column 1");
            model.addColumn("Column 2");

            model.addRow(new Object[]{"Row 1 - Col 1", "Row 1 - Col 2"});
            Vector<Object> row2 = new Vector<>();
            row2.add("Row 2 - Col 1");
            row2.add("Row 2 - Col 2");
            model.addRow(row2);
        } else {
            model.addColumn("Column 3");
            model.addColumn("Column 4");

            model.addRow(new Object[]{"Row 1 - Col 3", "Row 1 - Col 4"});
            Vector<Object> row2 = new Vector<>();
            row2.add("Row 2 - Col 3");
            row2.add("Row 2 - Col 4");
            model.addRow(row2);
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
        if (e.getSource() == selectOption1) {
            selectOption(1);
        }
        if (e.getSource() == selectOption2) {
            selectOption(2);
        }
    }
}
