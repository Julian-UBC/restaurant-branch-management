package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

public class CreateAndShowGUI implements ActionListener, ItemListener {
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
    private JPanel insertPanel;

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
        // creating drop down option for users to input the new row
        JPanel dropDownPane = new JPanel();
        String attributeItems[] = {"Insert to Branch", "Insert to Menu", "Insert to Reservation"};
        JComboBox dropDown = new JComboBox(attributeItems);
        dropDown.setEditable(false);
        dropDown.addItemListener(this);
        dropDownPane.add(dropDown);
        
        // creates panel containing insert to branch, menu and reservation
        insertPanel = new JPanel(new CardLayout());
        JPanel insertBranch = insertToBranch(new JPanel());
        insertPanel.add(insertBranch, "Insert to Branch");
        JPanel insertMenu = insertToMenu(new JPanel());
        insertPanel.add(insertMenu, "Insert to Menu");
        JPanel insertReservation = insertToReservations(new JPanel());
        insertPanel.add(insertReservation, "Insert to Reservation");
        popUp.add(dropDownPane, BorderLayout.PAGE_START);
        popUp.add(insertPanel, BorderLayout.CENTER);

        JOptionPane.showOptionDialog(null, popUp,"Insert", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
    }
    
    private JPanel insertToBranch(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel locId = new JLabel("Location ID:");
        JTextField enterLocID = new JTextField();
        JLabel streetAddress = new JLabel("Street Address:");
        JTextField enterStreetAddress = new JTextField();
        JLabel city = new JLabel("City:");
        JTextField enterCity = new JTextField();
        JLabel province = new JLabel("Province:");
        JTextField enterProvince = new JTextField();
        
        // get all the user input
        JButton insertButton = new JButton("Insert");
        insertButton.addActionListener(e -> {
            String getLocId = enterLocID.getText();
            String getStreetAddress = enterStreetAddress.getText();
            String getCity = enterCity.getText();
            String getProvince = enterProvince.getText();
            JOptionPane.showMessageDialog(null,"Added");
            model.addRow(new Object[]{getLocId, getStreetAddress, getCity, getProvince});
        });
        
        panel.add(locId);
        panel.add(enterLocID);
        panel.add(streetAddress);
        panel.add(enterStreetAddress);
        panel.add(city);
        panel.add(enterCity);
        panel.add(province);
        panel.add(enterProvince);
        panel.add(insertButton);
        return panel;
    }
    
    private JPanel insertToMenu (JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel name = new JLabel("name:");
        JTextField enterName = new JTextField();
        JLabel cost = new JLabel("cost:");
        JTextField enterCost = new JTextField();
        JLabel category = new JLabel("category:");
        JTextField enterCategory = new JTextField();

        // get all the user input
        JButton insertButton = new JButton("Insert");
        insertButton.addActionListener(e -> {
            String getName = enterName.getText();
            String getCost = enterCost.getText();
            String getCategory = enterCategory.getText();
            JOptionPane.showMessageDialog(null,"Added");
            model.addRow(new Object[]{getName, getCost, getCategory});
        });

        panel.add(name);
        panel.add(enterName);
        panel.add(cost);
        panel.add(enterCost);
        panel.add(category);
        panel.add(enterCategory);
        panel.add(insertButton);
        return panel;
    }
    
    private JPanel insertToReservations(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel rId = new JLabel("Reservation ID:");
        JTextField enterRId = new JTextField();
        JLabel cId = new JLabel("Customer ID:");
        JTextField enterCId = new JTextField();
        JLabel locId = new JLabel("Location ID:");
        JTextField enterLocID = new JTextField();
        JLabel wId = new JLabel("Employee ID:");
        JTextField enterWId = new JTextField();
        JLabel date = new JLabel("Date:");
        JTextField enterDate = new JTextField();
        JLabel time = new JLabel("Time:");
        JTextField enterTime = new JTextField();
        JLabel numOfPeople = new JLabel("Number of People:");
        JTextField enterNumOfPeople = new JTextField();

        // get all the user input
        JButton insertButton = new JButton("Insert");
        insertButton.addActionListener(e -> {
            String getRId = enterRId.getText();
            String getCId = enterCId.getText();
            String getLocId = enterLocID.getText();
            String getWId = enterWId.getText();
            String getDate = enterDate.getText();
            String getTime = enterTime.getText();
            String getNum = enterNumOfPeople.getText();
            JOptionPane.showMessageDialog(null,"Added");
            model.addRow(new Object[]{getRId, getCId, getLocId, getWId, getDate, getTime, getNum});
        });

        panel.add(rId);
        panel.add(enterRId);
        panel.add(cId);
        panel.add(enterCId);
        panel.add(locId);
        panel.add(enterLocID);
        panel.add(wId);
        panel.add(enterWId);
        panel.add(date);
        panel.add(enterDate);
        panel.add(time);
        panel.add(enterTime);
        panel.add(numOfPeople);
        panel.add(enterNumOfPeople);
        panel.add(insertButton);
        return panel;
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
            insert(new JPanel(new BorderLayout()));
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

    @Override
    public void itemStateChanged(ItemEvent e) {
        CardLayout cl = (CardLayout)(insertPanel.getLayout());
        cl.show(insertPanel, (String)e.getItem());
    }
}
