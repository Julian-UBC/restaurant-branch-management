package ui;

import delegates.RestaurantDelegate;
import model.Menu;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class CreateAndShowGUI implements ActionListener, ItemListener {
    private static final int WIDTH=1200;
    private static final int HEIGHT=700;

    private JTable table;
    private DefaultTableModel model;
    private JPanel insertPanel;
    private String tableShown = "Menu";

    private JButton selectButton;
    private JButton insertButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton filterButton;
    private JButton projectionButton;
    private JButton groupByButton;
    private JButton nestedButton;
    private JButton joinButton;
    private JButton moreButton;

    private RestaurantDelegate delegate;

    public CreateAndShowGUI(RestaurantDelegate delegate) {
        this.delegate = delegate;

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

    // create a table on the left side
    private JPanel createTablePanel() {
        table = new JTable();
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(800,600));
        panel.setLayout(new BorderLayout());

        model = new DefaultTableModel();
        showTable(tableShown);
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
        filterButton = new JButton("Filter");
        filterButton.addActionListener(this);
        projectionButton = new JButton("Projection");
        projectionButton.addActionListener(this);
        groupByButton = new JButton("Group By");
        groupByButton.addActionListener(this);
        nestedButton = new JButton("Nested");
        nestedButton.addActionListener(this);
        joinButton = new JButton("Join");
        joinButton.addActionListener(this);
        moreButton = new JButton("More");
        moreButton.addActionListener(this);

        panel.add(selectButton, g);
        g.gridy++;
        panel.add(insertButton,g);
        g.gridy++;
        panel.add(updateButton, g);
        g.gridy++;
        panel.add(deleteButton,g);
        g.gridy++;
        panel.add(filterButton,g);
        g.gridy++;
        panel.add(projectionButton, g);
        g.gridy++;
        panel.add(groupByButton, g);
        g.gridy++;
        panel.add(nestedButton, g);
        g.gridy++;
        panel.add(joinButton, g);
        g.gridy++;
        panel.add(moreButton, g);
        g.gridy++;
        return panel;
    }

    // Display the appropriate columns and rows in the table
    private void showTable(String instance) {
        tableShown = instance;

        switch (tableShown) {
            case "Menu" -> {
                Menus menus = delegate.showMenus();
                for (String column : menus.getColumns()) {
                    model.addColumn(column);
                }
                for (Vector<Object> tuple : menus.getTuples()) {
                    model.addRow(tuple);
                }
            }
            case "Reservations" -> {
                Reservations reservations = delegate.showReservations();
                for (String column : reservations.getColumns()) {
                    model.addColumn(column);
                }

                for (Vector<Object> tuple : reservations.getTuples()) {
                    model.addRow(tuple);
                }
            }
            case "Branch" -> {
                Branches branches = delegate.showBranches();
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

    private void refreshTable() {
        model = new DefaultTableModel();
        showTable(tableShown);
        table.setModel(model);
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
        String[] attributeItems = {"Insert to Branch", "Insert to Menu", "Insert to Reservation"};
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
            Branch newBranch = new Branch(getLocId, getStreetAddress, getCity, getProvince);
            delegate.insertBranch(newBranch);

            // Remove inputs
            enterLocID.setText("");
            enterStreetAddress.setText("");
            enterCity.setText("");
            enterProvince.setText("");

            // Display the new tuple
            refreshTable();
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
            float getCost = Float.parseFloat(enterCost.getText());
            String getCategory = enterCategory.getText();
            Menu newMenu = new Menu(getName, getCost, getCategory);
            delegate.insertMenu(newMenu);

            // Remove inputs
            enterName.setText("");
            enterCost.setText("");
            enterCategory.setText("");

            // Display the new tuple
            refreshTable();
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        JFormattedTextField enterDate = new JFormattedTextField (dateFormat);
        JLabel time = new JLabel("Time:");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        JFormattedTextField  enterTime = new JFormattedTextField (timeFormat);
        JLabel numOfPeople = new JLabel("Number of People:");
        JTextField enterNumOfPeople = new JTextField();
        JLabel reservationName = new JLabel("Reservation Name:");
        JTextField enterReservationName = new JTextField();

        // get all the user input
        JButton insertButton = new JButton("Insert");
        insertButton.addActionListener(e -> {
            int getRId = Integer.parseInt(enterRId.getText());
            int getCId = Integer.parseInt(enterCId.getText());
            int getLocId = Integer.parseInt(enterLocID.getText());
            int getWId = Integer.parseInt(enterWId.getText());
            String getDate = enterDate.getText();
            String getTime = enterTime.getText();
            int getNum = Integer.parseInt(enterNumOfPeople.getText());
            String getName = enterReservationName.getText();
            
            // turn string getDate to LocalDate
            
            // turn string getTime to LocalTime
            
            Reservation newReservation = new Reservation(getRId, getCId, getLocId, getWId, 
                   LocalDate.parse(getDate), getTime, getNum, getName);
            delegate.insertReservation(newReservation);

            // Remove inputs
            enterRId.setText("");
            enterCId.setText("");
            enterLocID.setText("");
            enterWId.setText("");
            enterDate.setText("");
            enterTime.setText("");
            enterNumOfPeople.setText("");
            enterReservationName.setText("");

            // Display the new tuple
            refreshTable();
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
        panel.add(reservationName);
        panel.add(enterReservationName);
        panel.add(insertButton);
        return panel;
    }
    
    private void update(JPanel popUp) {
        JPanel dropDownPane = new JPanel();
        String[] attributes = new String[0];

        switch (tableShown) {
            case "Menu" -> attributes = new String[]{"Name", "Cost", "Category"};
            case "Reservations" ->
                    attributes = new String[]{"Reservation ID", "Customer ID", "BranchID", "Host", "Date", "Time", "Number of People", "Reservation Name"};
            case "Branch" -> attributes = new String[]{"Branch ID", "Address", "City", "Province"};
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
                switch (tableShown) {
                    case "Menu" -> {
                        String currentName = (String) selectedTuple.getValueAt(table.getSelectedRow(), 0);
                        float currentCost = Float.parseFloat(String.valueOf(selectedTuple.getValueAt(table.getSelectedRow(), 1)));
                        String currentCategory = (String) selectedTuple.getValueAt(table.getSelectedRow(), 2);
                        if (columnNum == 0) {
                            delegate.updateMenu(currentName, updatedValue, currentCost, currentCategory);
                        }
                        if (columnNum == 1) {
                            delegate.updateMenu(currentName, currentName, Float.parseFloat(updatedValue), currentCategory);
                        }
                        if (columnNum == 2) {
                            delegate.updateMenu(currentName, currentName, currentCost, updatedValue);
                        }

                    }
                    case "Reservations" -> {
                        int currentRID = Integer.parseInt(String.valueOf(selectedTuple.getValueAt(table.getSelectedRow(), 0)));
                        int currentCID = Integer.parseInt(String.valueOf(selectedTuple.getValueAt(table.getSelectedRow(), 1)));
                        int currentLocID = Integer.parseInt(String.valueOf(selectedTuple.getValueAt(table.getSelectedRow(), 2)));
                        int currentWID = Integer.parseInt(String.valueOf(selectedTuple.getValueAt(table.getSelectedRow(), 3)));
                        LocalDate currentRDate = LocalDate.parse(String.valueOf(selectedTuple.getValueAt(table.getSelectedRow(), 4)));
                        LocalTime currentRTime = LocalTime.parse(String.valueOf(selectedTuple.getValueAt(table.getSelectedRow(), 5)));
                        int currentNumOfPeople = Integer.parseInt(String.valueOf(selectedTuple.getValueAt(table.getSelectedRow(), 6)));
                        String currentReservationName = (String) selectedTuple.getValueAt(table.getSelectedRow(), 7);

                        if (columnNum == 0) {
                            delegate.updateReservation(currentRID, Integer.parseInt(updatedValue), currentCID, currentLocID, currentWID, currentRDate, currentRTime, currentNumOfPeople, currentReservationName);
                        }
                        if (columnNum == 1) {
                            delegate.updateReservation(currentRID, currentRID, Integer.parseInt(updatedValue), currentLocID, currentWID, currentRDate, currentRTime, currentNumOfPeople, currentReservationName);
                        }
                        if (columnNum == 2) {
                            delegate.updateReservation(currentRID, currentRID, currentCID, Integer.parseInt(updatedValue), currentWID, currentRDate, currentRTime, currentNumOfPeople, currentReservationName);
                        }
                        if (columnNum == 3) {
                            delegate.updateReservation(currentRID, currentRID, currentCID, currentLocID, Integer.parseInt(updatedValue), currentRDate, currentRTime, currentNumOfPeople, currentReservationName);
                        }
                        if (columnNum == 4) {
                            delegate.updateReservation(currentRID, currentRID, currentCID, currentLocID, currentWID, LocalDate.parse(updatedValue), currentRTime, currentNumOfPeople, currentReservationName);
                        }
                        if (columnNum == 5) {
                            delegate.updateReservation(currentRID, currentRID, currentCID, currentLocID, currentWID, currentRDate, LocalTime.parse(updatedValue), currentNumOfPeople, currentReservationName);
                        }
                        if (columnNum == 6) {
                            delegate.updateReservation(currentRID, currentRID, currentCID, currentLocID, currentWID, currentRDate, currentRTime, Integer.parseInt(updatedValue), currentReservationName);
                        }
                        if (columnNum == 7) {
                            delegate.updateReservation(currentRID, currentRID, currentCID, currentLocID, currentWID, currentRDate, currentRTime, currentNumOfPeople, updatedValue);
                        }
                    }
                    case "Branch" -> {
                        int currentLocID = Integer.parseInt(String.valueOf(selectedTuple.getValueAt(table.getSelectedRow(), 0)));
                        String currentAddress = (String) selectedTuple.getValueAt(table.getSelectedRow(), 1);
                        String currentCity = (String) selectedTuple.getValueAt(table.getSelectedRow(), 2);
                        String currentProvince = (String) selectedTuple.getValueAt(table.getSelectedRow(), 3);

                        if (columnNum == 0) {
                            delegate.updateBranch(currentLocID, Integer.parseInt(updatedValue), currentAddress, currentCity, currentProvince);
                        }
                        if (columnNum == 1) {
                            delegate.updateBranch(currentLocID, currentLocID, updatedValue, currentCity, currentProvince);
                        }
                        if (columnNum == 2) {
                            delegate.updateBranch(currentLocID, currentLocID, currentAddress, updatedValue, currentProvince);
                        }
                        if (columnNum == 3) {
                            delegate.updateBranch(currentLocID, currentLocID, currentAddress, currentCity, updatedValue);
                        }
                    }
                    default -> {
                    }
                }
                selectedTuple.setValueAt(updatedValue, table.getSelectedRow(), columnNum);
            }

        }

    }

    private void delete() {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

        switch (tableShown) {
            case "Menu" -> {
                String tblName = tableModel.getValueAt(table.getSelectedRow(), 0).toString();
                delegate.deleteMenu(tblName);
            }
            case "Reservations" -> {
                int tblRId = Integer.parseInt(tableModel.getValueAt(table.getSelectedRow(), 0).toString());
                delegate.deleteReservation(tblRId);
            }
            case "Branch" -> {
                int tblLocId = Integer.parseInt(tableModel.getValueAt(table.getSelectedRow(), 0).toString());
                delegate.deleteBranch(tblLocId);
            }
            default -> {
            }
            //
        }

        refreshTable();
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
        CheckboxGroup branchGroup = new CheckboxGroup();
        Checkbox locIdBox = new Checkbox("location id", false, branchGroup);
        Checkbox streetAddressBox = new Checkbox("street address", false, branchGroup);
        Checkbox cityBox = new Checkbox("C", false, branchGroup);
        Checkbox provinceBox = new Checkbox("province", false, branchGroup);
        
        JButton insertButton = new JButton("Insert");
        insertButton.addActionListener(e -> {
            if (locIdBox.getState()) 
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
        // creates panel containing insert to branch, menu and reservation
        JPanel filterPanel = new JPanel(new CardLayout());
        JPanel filterBranches = filterBranches(new JPanel());
        filterPanel.add(filterBranches, "Filter Branch");
        JPanel filterMenu = filterMenus(new JPanel());
        filterPanel.add(filterMenu, "Filter Menu");
        JPanel filterReservations = filterReservations(new JPanel());
        filterPanel.add(filterReservations, "Filter Reservation");

        if (Objects.equals(tableShown, "Branch")) {
            CardLayout cl = (CardLayout)(filterPanel.getLayout());
            cl.show(filterPanel, "Filter Branch");

        } else if (Objects.equals(tableShown, "Menu")) {
            CardLayout cl = (CardLayout)(filterPanel.getLayout());
            cl.show(filterPanel, "Filter Menu");
        } else {
            CardLayout cl = (CardLayout)(filterPanel.getLayout());
            cl.show(filterPanel, "Filter Reservation");
        }
        popUp.add(filterPanel, BorderLayout.CENTER);
        JOptionPane.showOptionDialog(null, popUp,"Filter", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
    }

    private JPanel filterMenus(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel();
        JLabel label = new JLabel("Menu: ");
        topPanel.add(label);

        JPanel middlePanel = new JPanel();
        JCheckBox nameBox = new JCheckBox("Name", true);
        JCheckBox costBox = new JCheckBox("Cost", true);
        JCheckBox categoryBox = new JCheckBox("Category", true);

        middlePanel.add(nameBox);
        middlePanel.add(costBox);
        middlePanel.add(categoryBox);

        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));

        JLabel name = new JLabel("name:");
        JTextField enterName = new JTextField();
        JLabel cost = new JLabel("cost:");
        JTextField enterCost = new JTextField();
        JLabel category = new JLabel("category:");
        JTextField enterCategory = new JTextField();

        filterPanel.add(name);
        filterPanel.add(enterName);
        filterPanel.add(cost);
        filterPanel.add(enterCost);
        filterPanel.add(category);
        filterPanel.add(enterCategory);

        JPanel bottomPanel = new JPanel();
        JButton filterButton = new JButton("Filter");
        filterButton.addActionListener(e -> {
            List<String> columnsSelected = new ArrayList<>();
            List<String> columnsDomain = new ArrayList<>();
            List<String> filterConditions = new ArrayList<>();

            if (nameBox.isSelected()) {
                columnsSelected.add("Name");
                columnsDomain.add("String");
            }
            if (costBox.isSelected()) {
                columnsSelected.add("Cost");
                columnsDomain.add("float");
            }
            if (categoryBox.isSelected()) {
                columnsSelected.add("Category");
                columnsDomain.add("String");
            }

            if (!Objects.equals(enterName.getText(), "")) {
                filterConditions.add("name = '" + enterName.getText() + "'");
            }
            if (!Objects.equals(enterCost.getText(), "")) {
                filterConditions.add("cost = " + enterCost.getText());
            }
            if (!Objects.equals(enterCategory.getText(), "")) {
                filterConditions.add("category = '" + enterCategory.getText() + "'");
            }

            handleFilter(columnsSelected, columnsDomain, filterConditions, "Menu");
        });
        bottomPanel.add(filterButton);

        //add to popUp
        panel.add(topPanel);
        panel.add(middlePanel);
        panel.add(filterPanel);
        panel.add(bottomPanel);

        return panel;
    }

    private JPanel filterBranches(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel();
        JLabel label = new JLabel("Branch: ");
        topPanel.add(label);

        JPanel middlePanel = new JPanel();
        JCheckBox locIDBox = new JCheckBox("Location ID", true);
        JCheckBox streetAddressBox = new JCheckBox("Street Address", true);
        JCheckBox cityBox = new JCheckBox("City", true);
        JCheckBox provinceBox = new JCheckBox("Province", true);

        middlePanel.add(locIDBox);
        middlePanel.add(streetAddressBox);
        middlePanel.add(cityBox);
        middlePanel.add(provinceBox);

        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));

        JLabel locId = new JLabel("Location ID:");
        JTextField enterLocID = new JTextField();
        JLabel streetAddress = new JLabel("Street Address:");
        JTextField enterStreetAddress = new JTextField();
        JLabel city = new JLabel("City:");
        JTextField enterCity = new JTextField();
        JLabel province = new JLabel("Province:");
        JTextField enterProvince = new JTextField();

        filterPanel.add(locId);
        filterPanel.add(enterLocID);
        filterPanel.add(streetAddress);
        filterPanel.add(enterStreetAddress);
        filterPanel.add(city);
        filterPanel.add(enterCity);
        filterPanel.add(province);
        filterPanel.add(enterProvince);

        JPanel bottomPanel = new JPanel();
        JButton filterButton = new JButton("Filter");
        filterButton.addActionListener(e -> {
            List<String> columnsSelected = new ArrayList<>();
            List<String> columnsDomain = new ArrayList<>();
            List<String> filterConditions = new ArrayList<>();

            if (locIDBox.isSelected()) {
                columnsSelected.add("LocID");
                columnsDomain.add("int");
            }
            if (streetAddressBox.isSelected()) {
                columnsSelected.add("StreetAddress");
                columnsDomain.add("String");
            }
            if (cityBox.isSelected()) {
                columnsSelected.add("City");
                columnsDomain.add("String");
            }
            if (provinceBox.isSelected()) {
                columnsSelected.add("Province");
                columnsDomain.add("String");
            }

            if (!Objects.equals(enterLocID.getText(), "")) {
                filterConditions.add("LocID = '" + enterLocID.getText() + "'");
            }
            if (!Objects.equals(enterStreetAddress.getText(), "")) {
                filterConditions.add("StreetAddress = '" + enterStreetAddress.getText() + "'");
            }
            if (!Objects.equals(enterCity.getText(), "")) {
                filterConditions.add("City = '" + enterCity.getText() + "'");
            }
            if (!Objects.equals(enterProvince.getText(), "")) {
                filterConditions.add("Province = '" + enterProvince.getText() + "'");
            }

            handleFilter(columnsSelected, columnsDomain, filterConditions, "Branches");
        });

        bottomPanel.add(filterButton);

        //add to popUp
        panel.add(topPanel);
        panel.add(middlePanel);
        panel.add(filterPanel);
        panel.add(bottomPanel);

        return panel;
    }

    private JPanel filterReservations(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel();
        JLabel label = new JLabel("Reservation: ");
        topPanel.add(label);

        JPanel middlePanel = new JPanel();
        JCheckBox rIdBox = new JCheckBox("Reservation ID", true);
        JCheckBox cIDBox = new JCheckBox("Customer ID", true);
        JCheckBox locIDBox = new JCheckBox("Location ID", true);
        JCheckBox widBox = new JCheckBox("Employee ID", true);
        JCheckBox dateBox = new JCheckBox("Date", true);
        JCheckBox timeBox = new JCheckBox("Time", true);
        JCheckBox numOfPeopleBox = new JCheckBox("Number of People", true);
        JCheckBox resNameBox = new JCheckBox("Reservation Name", true);

        middlePanel.add(rIdBox);
        middlePanel.add(cIDBox);
        middlePanel.add(locIDBox);
        middlePanel.add(widBox);
        middlePanel.add(dateBox);
        middlePanel.add(timeBox);
        middlePanel.add(numOfPeopleBox);
        middlePanel.add(resNameBox);

        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));

        JLabel rId = new JLabel("Reservation ID:");
        JTextField enterRId = new JTextField();
        JLabel cId = new JLabel("Customer ID:");
        JTextField enterCId = new JTextField();
        JLabel locId = new JLabel("Location ID:");
        JTextField enterLocID = new JTextField();
        JLabel wId = new JLabel("Employee ID:");
        JTextField enterWId = new JTextField();
        JLabel date = new JLabel("Date:");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        JFormattedTextField enterDate = new JFormattedTextField (dateFormat);
        JLabel time = new JLabel("Time:");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        JFormattedTextField  enterTime = new JFormattedTextField (timeFormat);
        JLabel numOfPeople = new JLabel("Number of People:");
        JTextField enterNumOfPeople = new JTextField();
        JLabel reservationName = new JLabel("Reservation Name:");
        JTextField enterReservationName = new JTextField();

        filterPanel.add(rId);
        filterPanel.add(enterRId);
        filterPanel.add(cId);
        filterPanel.add(enterCId);
        filterPanel.add(locId);
        filterPanel.add(enterLocID);
        filterPanel.add(wId);
        filterPanel.add(enterWId);
        filterPanel.add(date);
        filterPanel.add(enterDate);
        filterPanel.add(time);
        filterPanel.add(enterTime);
        filterPanel.add(numOfPeople);
        filterPanel.add(enterNumOfPeople);
        filterPanel.add(reservationName);
        filterPanel.add(enterReservationName);

        JPanel bottomPanel = new JPanel();
        JButton filterButton = new JButton("Filter");
        filterButton.addActionListener(e -> {
            List<String> columnsSelected = new ArrayList<>();
            List<String> columnsDomain = new ArrayList<>();
            List<String> filterConditions = new ArrayList<>();

            if (rIdBox.isSelected()) {
                columnsSelected.add("rID");
                columnsDomain.add("int");
            }
            if (cIDBox.isSelected()) {
                columnsSelected.add("cID");
                columnsDomain.add("int");
            }
            if (locIDBox.isSelected()) {
                columnsSelected.add("LocID");
                columnsDomain.add("int");
            }
            if (widBox.isSelected()) {
                columnsSelected.add("wID");
                columnsDomain.add("int");
            }
            if (dateBox.isSelected()) {
                columnsSelected.add("rDate");
                columnsDomain.add("date");
            }
            if (timeBox.isSelected()) {
                columnsSelected.add("rTime");
                columnsDomain.add("time");
            }
            if (numOfPeopleBox.isSelected()) {
                columnsSelected.add("NumOfPeople");
                columnsDomain.add("int");
            }
            if (resNameBox.isSelected()) {
                columnsSelected.add("ReservationName");
                columnsDomain.add("String");
            }

            if (!Objects.equals(enterRId.getText(), "")) {
                filterConditions.add("rID = " + enterRId.getText());
            }

            if (!Objects.equals(enterCId.getText(), "")) {
                filterConditions.add("cID = " + enterCId.getText());
            }

            if (!Objects.equals(enterLocID.getText(), "")) {
                filterConditions.add("locID = " + enterLocID.getText());
            }

            if (!Objects.equals(enterWId.getText(), "")) {
                filterConditions.add("wID = " + enterWId.getText());
            }

            if (!Objects.equals(enterDate.getText(), "")) {
                filterConditions.add("rDate = '" + enterDate.getText() + "'");
            }

//            if (!Objects.equals(enterTime.getText(), "")) {
//                filterConditions.add("rTime = '" + enterTime.getText() + "'");
//            }

            if (!Objects.equals(enterNumOfPeople.getText(), "")) {
                filterConditions.add("numOfPeopl = " + enterNumOfPeople.getText());
            }

            if (!Objects.equals(enterReservationName.getText(), "")) {
                filterConditions.add("reservationName = '" + enterReservationName.getText() + "'");
            }

            handleFilter(columnsSelected, columnsDomain, filterConditions, "Reservations");
        });

        bottomPanel.add(filterButton);

        //add to popUp
        panel.add(topPanel);
        panel.add(middlePanel);
        panel.add(filterPanel);
        panel.add(bottomPanel);

        return panel;
    }

    private void handleFilter(List<String> columnsSelected, List<String> columnsDomain, List<String> filterConditions, String tableSelected) {
        List<List<String>> tuples = delegate.filter(columnsSelected, columnsDomain, filterConditions, tableSelected);

        // clear table
        model = new DefaultTableModel();

        for (String column: columnsSelected) {
            model.addColumn(column);
        }

        for (List<String> tuple : tuples) {
            Vector<Object> newTuple = new Vector<>();

            for (int i = 0; i < columnsDomain.size(); i++) {
                switch (columnsDomain.get(i)) {
                    case "float" -> {
                        newTuple.add(Float.valueOf(tuple.get(i)));
                    }
                    case "int" -> {
                        newTuple.add(Integer.valueOf(tuple.get(i)));
                    }
                    default -> {
                        newTuple.add(tuple.get(i));
                    }
                }
            }

            model.addRow(newTuple);
        }

        table.setModel(model);

    }

    private void groupByButton() {
        //clear table
        model = new DefaultTableModel();
        MenuSorted groupMenu = delegate.showGroupBy();
        model.addColumn("Category");
        model.addColumn("Cost");
        for (Vector<Object> tuple : groupMenu.getTuples()) {
            model.addRow(tuple);
        }

        table.setModel(model);
    }
    
    private void nestedGroupButton() {
        // clear table
        model = new DefaultTableModel();
        MenuSorted groupMenu = delegate.showNestedAggregation();
        model.addColumn("Category");
        model.addColumn("Cost");
        for (Vector<Object> tuple : groupMenu.getTuples()) {
            model.addRow(tuple);
        }

        table.setModel(model);
    }
    private void join(JPanel popUp) {

        JLabel info = new JLabel("View all reservations during given time for every branch.");
        JLabel instruction = new JLabel("Insert the number of days from current day:");
        JTextField numOfDays = new JTextField();
        popUp.setLayout(new BorderLayout());
        popUp.add(info, BorderLayout.PAGE_START);
        popUp.add(instruction, BorderLayout.LINE_START);
        popUp.add(numOfDays, BorderLayout.CENTER);
        Object[] options = {"Confirm", "Cancel"};
        int n = JOptionPane.showOptionDialog(null, popUp, "Join", JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, null);

        if (n == 0) {

            LocalDate currentDate = LocalDate.now();
            JoinedBranchReservation resultOfJoin = delegate.joinBranchReservation(currentDate, currentDate.plusDays(Integer.parseInt(numOfDays.getText())));

            model = new DefaultTableModel();

            for (String column : resultOfJoin.getColumns()) {
                model.addColumn(column);
            }
            for (Vector<Object> tuple : resultOfJoin.getTuples()) {
                model.addRow(tuple);
            }
            table.setModel(model);
        }
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
        if (e.getSource() == filterButton) {
            filter(new JPanel());
        }
        if (e.getSource() == projectionButton) {
            projectionButton(new JPanel());
        }
        if(e.getSource() == groupByButton) {
            groupByButton();
        }
        if (e.getSource() == joinButton) {
            join(new JPanel());
        }
        if(e.getSource() == nestedButton) {
            nestedGroupButton();
        }
        if(e.getSource() == moreButton) {
            new MoreWindow(delegate);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        CardLayout cl = (CardLayout)(insertPanel.getLayout());
        cl.show(insertPanel, (String)e.getItem());
    }
}
