package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class Reservations {
    private List<String> columns;
    private List<Vector<Object>> tuples;

    public Reservations() {
        columns = new ArrayList<>();
        columns.add("Reservation ID");
        columns.add("Customer ID");
        columns.add("Branch ID");
        columns.add("Host");
        columns.add("Date");
        columns.add("Time");
        columns.add("Number of People");
        columns.add("Reservation Name");

        tuples = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            Vector<Object> tuple = new Vector<>();
            tuple.add(0, "rid " + i);
            tuple.add(1, "cid " + i);
            tuple.add(2, "locid " + i);
            tuple.add(3, "wid " + i);
            tuple.add(4, "date " + i);
            tuple.add(5, "time " + i);
            tuple.add(6, "numOfPeople " + i);
            tuple.add(7, "reservationName " + i);
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
