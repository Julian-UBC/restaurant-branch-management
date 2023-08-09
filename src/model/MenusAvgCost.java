package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class MenusAvgCost {
    final private List<String> columns;
    private List<Vector<Object>> tuples;

    public MenusAvgCost() {
        columns = new ArrayList<>();
        columns.add("Category");
        columns.add("Average Cost");

        tuples = new ArrayList<>();
    }

    public List<String> getColumns() {
        return Collections.unmodifiableList(columns);
    }

    public List<Vector<Object>> getTuples() {
        return Collections.unmodifiableList(tuples);
    }

    public void addAvgCostHaving(AvgCostHaving menu) {
        Vector<Object> tuple = new Vector<>();
        tuple.add(0, menu.getCategory());
        tuple.add(1, menu.getAvgCost());

        tuples.add(tuple);
    }
}
