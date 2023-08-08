package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class Branches {
    final private List<String> columns;
    private List<Vector<Object>> tuples;

    public Branches() {
        columns = new ArrayList<>();
        columns.add("Branch ID");
        columns.add("Address");
        columns.add("City");
        columns.add("Province");

        tuples = new ArrayList<>();
    }

    public List<String> getColumns() {
        return Collections.unmodifiableList(columns);
    }

    public List<Vector<Object>> getTuples() {
        return Collections.unmodifiableList(tuples);
    }

    public void addBranches(Branch branch) {
        Vector<Object> tuple = new Vector<>();
        tuple.add(0, branch.getLocId());
        tuple.add(1, branch.getStreetAddress());
        tuple.add(2, branch.getCity());
        tuple.add(3, branch.getProvince());

        tuples.add(tuple);
    }
}
