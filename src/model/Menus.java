package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class Menus {
    final private List<String> columns;
    private List<Vector<Object>> tuples;

    public Menus() {
        columns = new ArrayList<>();
        columns.add("Name");
        columns.add("Cost");
        columns.add("Category");

        tuples = new ArrayList<>();
    }

    public List<Menu> getMenus() {
        List<Menu> a = new ArrayList<Menu>();
        for (Vector<Object> b: tuples) {
            String name = (String) b.get(0);
            Float cost = (Float) b.get(1);
            String category = (String) b.get(2);
            Menu m = new Menu(name, cost, category);
            a.add(m);
        }
        return a;
    }
    
    public List<String> getColumns() {
        return Collections.unmodifiableList(columns);
    }

    public List<Vector<Object>> getTuples() {
        return Collections.unmodifiableList(tuples);
    }

    public void addMenu(Menu menu) {
        Vector<Object> tuple = new Vector<>();
        tuple.add(0, menu.getName());
        tuple.add(1, menu.getCost());
        tuple.add(2, menu.getCategory());

        tuples.add(tuple);
    }
}
