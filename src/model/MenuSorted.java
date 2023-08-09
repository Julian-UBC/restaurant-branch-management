package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class MenuSorted {
    final private List<String> columns;
    private List<Vector<Object>> tuples;

    public MenuSorted() {
        columns = new ArrayList<>();
        columns.add("Category");
        columns.add("Cost");

        tuples = new ArrayList<>();
    }

    public List<String> getColumns() {
        return Collections.unmodifiableList(columns);
    }

    public List<Vector<Object>> getTuples() {
        return Collections.unmodifiableList(tuples);
    }


    public void addSortedMenu(Menu menu) {
        Vector<Object> tuple = new Vector<>();
        tuple.add(0, menu.getCategory());
        tuple.add(1, menu.getCost());

        tuples.add(tuple);
    }
}
