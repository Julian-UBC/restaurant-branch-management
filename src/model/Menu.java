package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class Menu {
    private List<String> columns;
    private List<Vector<Object>> tuples;

    public Menu() {
        columns = new ArrayList<>();
        columns.add("Name");
        columns.add("Cost");
        columns.add("Category");

        tuples = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            Vector<Object> tuple = new Vector<>();
            tuple.add(0, "name " + i);
            tuple.add(1, "cost " + i);
            tuple.add(2, "category " + i);
            tuples.add(tuple);
        }
    }

    public List<String> getColumns() {
        return Collections.unmodifiableList(columns);
    }

    public List<Vector<Object>> getTuples() {


        return Collections.unmodifiableList(tuples);
    }
}
