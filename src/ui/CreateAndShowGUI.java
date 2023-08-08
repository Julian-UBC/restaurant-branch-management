package ui;

import Delegate.RestaurantDelegate;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Objects;
import java.util.Vector;

public class CreateAndShowGUI implements ActionListener, ItemListener {
    private static final int WIDTH=1000;
    private static final int HEIGHT=700;

    private JTable table;
    private DefaultTableModel model;

    private JButton selectButton;
    private JButton insertButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton showButton;
    private JButton filterButton;
    private JButton projectionButton;
    private JButton groupByButton;
    private JPanel insertPanel;
    private String tableShown;

    private Menus menus;
    private Reservations reservations;
    private Branches branches;

    private RestaurantDelegate delegate = null;

    public CreateAndShowGUI(RestaurantDelegate delegate) {
        this.delegate = delegate;
        initializeInstances();

        JPanel leftPane = createTablePanel();
        JPanel rightPane = createButtonPanel();

        JPanel pane = new JPanel();
        pane.add(leftPane, BorderLayout.LINE_START);
        pane.add(rightPane, BorderLayout.LINE_END);

        JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, leftPane, rightPane);
        sp.setOrientation(SwingConstants.VERTICAL);

        JFrame frame = new JFrame();
        frame.add(sp);
        frame.setTitle("Restaurant Management");
        frame.setSize(WIDTH,HEIGHT);
        frame.setBackground(Color.decode("#FE5F55"));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initializeInstances() {
        tableShown = "Menu";
        menus = new Menus();
        reservations = new Reservations();
        branches = new Branches();
    }

    // create a table on the left side
    private JPanel createTablePanel() {
        table = new JTable();
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(800,600));
        panel.setLayout(new BorderLayout());

        model = new DefaultTableModel();
        showTable("Menu");
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

        selectButton = new JButton("Select");
        selectButton.addActionListener(this);
        insertButton = new JButton("Insert");
        insertButton.addActionListener(this);
        updateButton = new JButton("Update");
        updateButton.addActionListener(this);
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);
        showButton = new JButton("Show");
        showButton.addActionListener(this);
        filterButton = new JButton("Filter");
        filterButton.addActionListener(this);
        projectionButton = new JButton("Projection");
        projectionButton.addActionListener(this);
        groupByButton = new JButton("Group By");
        groupByButton.addActionListener(this);

        panel.add(selectButton, g);
        g.gridy++;
        panel.add(insertButton,g);
        g.gridy++;
        panel.add(updateButton, g);
        g.gridy++;
        panel.add(deleteButton,g);
        g.gridy++;
        panel.add(showButton,g);
        g.gridy++;
        panel.add(filterButton,g);
        g.gridy++;
        panel.add(projectionButton, g);
        g.gridy++;
        return panel;
    }

    // Display the appropriate columns and rows in the table
    private void showTable(String instance) {
        tableShown = instance;

        switch (tableShown) {
            case "Menu" -> {
                for (String column : menus.getColumns()) {
                    model.addColumn(column);
                }
                for (Vector<Object> tuple : menus.getTuples()) {
                    model.addRow(tuple);
                }
            }
            case "Reservations" -> {
                for (String column : reservations.getColumns()) {
                    model.addColumn(column);
                }

                for (Vector<Object> tuple : reservations.getTuples()) {
                    model.addRow(tuple);
                }
            }
            case "Branch" -> {
                for (String column : branches.getColumns()) {
                    model.addColumn(column);
                }
                for (Vector<Object> tuple : branches.getTuples()) {
                    model.addRow(tuple);
                }
            }
            default -> {
            }
            //
        }
    }

    private void select(JPanel popUp) {
        JPanel dropDownPane = new JPanel();

        String[] attribute = {"Menu", "Reservations", "Branch"};
        JComboBox dropDown = new JComboBox(attribute);

        dropDown.setSelectedItem(tableShown);
        dropDown.setEditable(false);
        dropDown.addActionListener(this);

        dropDownPane.add(dropDown);
        popUp.add(dropDownPane);

        Object[] options = { "Show", "Cancel" };
        int n = JOptionPane.showOptionDialog(null, popUp, "Select", JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, null);

        if (n == 0) {
            model = new DefaultTableModel();
            showTable((String) dropDown.getSelectedItem());
            table.setModel(model);
        }
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
            int getLocId = Integer.parseInt(enterLocID.getText());
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
        String[] attributes = new String[0];

        switch (tableShown) {
            case "Menu" -> attributes = new String[]{"Name", "Cost", "Category"};
            case "Reservations" ->
                    attributes = new String[]{"Reservation ID", "Customer ID", "Worker ID", "Reservation Date", "Reservation Time", "Number of People", "Reservation Name"};
            case "Branch" -> attributes = new String[]{"LocID", "StreetAddress", "City", "Province"};
            default -> {
            }
        }

        JComboBox dropDown = new JComboBox(attributes);
        dropDown.setEditable(false);
        dropDown.addActionListener(this);
        dropDownPane.add(dropDown);

        JTextField newValue = new JTextField(5);
        newValue.addActionListener((this)); //not sure if this is needed
        newValue.setToolTipText("Enter new value here");

        popUp.setLayout(new BorderLayout());
        popUp.add(new JLabel("Select the table and attribute you would like to update and enter the new value."), BorderLayout.PAGE_START);
        popUp.add(dropDownPane, BorderLayout.LINE_START);
        popUp.add(newValue);

        Object[] options = {"Update", "Cancel"};
        int n = JOptionPane.showOptionDialog(null, popUp, "Update", JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, null);

        String updatedValue = newValue.getText();
        if (n == 0) {
            DefaultTableModel selectedTuple = (DefaultTableModel) table.getModel();
            if (table.getSelectedRowCount() == 1) {
                int columnNum = table.getColumn(Objects.requireNonNull(dropDown.getSelectedItem())).getModelIndex();
                //not working

                selectedTuple.setValueAt(updatedValue, table.getSelectedRow(), columnNum);

            }
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

    private void projectionButton(JPanel popUp) {
        // creates panel containing insert to branch, menu and reservation
        JPanel projectPanel = new JPanel(new CardLayout());
        JPanel projectBranch = projectBranch(new JPanel());
        projectPanel.add(projectBranch, "Project Branch");
        JPanel projectMenu = projectMenu(new JPanel());
        projectPanel.add(projectMenu, "Project Menu");
        JPanel projectReservation = projectReservation(new JPanel());
        projectPanel.add(projectReservation, "Project Reservation");
        
        if (Objects.equals(tableShown, "Branch")) {
            CardLayout cl = (CardLayout)(projectPanel.getLayout());
            cl.show(projectPanel, "Project Branch");
            
        } else if (Objects.equals(tableShown, "Menu")) {
            CardLayout cl = (CardLayout)(projectPanel.getLayout());
            cl.show(projectPanel, "Project Menu");
        } else {
            CardLayout cl = (CardLayout)(projectPanel.getLayout());
            cl.show(projectPanel, "Project Reservation");
        }
        popUp.add(projectPanel, BorderLayout.CENTER);
        JOptionPane.showOptionDialog(null, popUp,"Projection", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
    }
    
    private JPanel projectBranch(JPanel panel) {
        //create the checkboxes for each attribute
        JLabel label = new JLabel("Branch: ");
        JCheckBox locIdBox = new JCheckBox("location Id");
        JCheckBox streetAddressBox = new JCheckBox("street Address");
        JCheckBox cityBox = new JCheckBox("city");
        JCheckBox provinceBox = new JCheckBox("province");

        JButton insertButton = new JButton("Insert");
        insertButton.addActionListener(e -> {
            // if locIdBox.getState() { // add column locIdBox to table
            // if streetAddressBox.getState() { // add column locIdBox to table
            // if cityBox.getState() { // add column locIdBox to table
            // if provinceBox.getState() { // add column locIdBox to table
            JOptionPane.showMessageDialog(null,"Added");
        });
        
        //add to popUp
        panel.add(label);
        panel.add(locIdBox);
        panel.add(streetAddressBox);
        panel.add(cityBox);
        panel.add(provinceBox);
        panel.add(insertButton);
        
        return panel;
    }

    private JPanel projectMenu(JPanel panel) {
        //create the checkboxes for each attribute
        JLabel label = new JLabel("Menu: ");
        JCheckBox nameBox = new JCheckBox("Name");
        JCheckBox categoryBox = new JCheckBox("Category");
        JCheckBox costBox = new JCheckBox("Cost");

        JButton insertButton = new JButton("Insert");
        insertButton.addActionListener(e -> {
            // if locIdBox.getState() { // add column locIdBox to table
            // if streetAddressBox.getState() { // add column locIdBox to table
            // if cityBox.getState() { // add column locIdBox to table
            // if provinceBox.getState() { // add column locIdBox to table
            JOptionPane.showMessageDialog(null,"Added");
        });

        //add to popUp
        panel.add(label);
        panel.add(nameBox);
        panel.add(categoryBox);
        panel.add(costBox);
        panel.add(insertButton);
        
        return panel;
    }

    private JPanel projectReservation(JPanel panel) {
        //create the checkboxes for each attribute
        JLabel label = new JLabel("Reservation: ");
        JCheckBox rIdBox = new JCheckBox("reservation id");
        JCheckBox cIDBox = new JCheckBox("customer id");
        JCheckBox widBox = new JCheckBox("employee id");
        JCheckBox dateBox = new JCheckBox("date");
        JCheckBox timeBox = new JCheckBox("date");
        JCheckBox numOfPeopleBox = new JCheckBox("number of people");

        JButton insertButton = new JButton("Insert");
        insertButton.addActionListener(e -> {
            // if locIdBox.getState() { // add column locIdBox to table
            // if streetAddressBox.getState() { // add column locIdBox to table
            // if cityBox.getState() { // add column locIdBox to table
            // if provinceBox.getState() { // add column locIdBox to table
            JOptionPane.showMessageDialog(null,"Added");
        });

        //add to popUp
        panel.add(label);
        panel.add(rIdBox);
        panel.add(cIDBox);
        panel.add(widBox);
        panel.add(dateBox);
        panel.add(timeBox);
        panel.add(numOfPeopleBox);
        panel.add(insertButton);
        
        return panel;
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
    
    private void groupByButton() {
        // show to table 
    }
    
    private void having() {
        
    }
    
    private void nestedAggregation() {
        
    }
    
    private void division() {
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == selectButton) {
            select(new JPanel());
        }
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
        if (e.getSource() == filterButton) {
            filter(new JPanel());
        }
        if (e.getSource() == projectionButton) {
            projectionButton(new JPanel());
        }
        if(e.getSource() == groupByButton) {
            groupByButton();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        CardLayout cl = (CardLayout)(insertPanel.getLayout());
        cl.show(insertPanel, (String)e.getItem());
    }
}
