package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;


public class Reservation {
    private List<String> columns;
    private List<Vector<Object>> tuples;

    public Reservation() {
        columns = new ArrayList<>();
        columns.add("Reservation ID");
        columns.add("Customer ID");
        columns.add("Location ID");
        columns.add("Worker ID");
        columns.add("Reservation Date");
        columns.add("Reservation Time");
        columns.add("Number of People");
        columns.add("Reservation Name");

        tuples = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            Vector<Object> tuple = new Vector<>();
            tuple.add(0, "rID " + i);
            tuple.add(1, "cID " + i);
            tuple.add(2, "locID " + i);
            tuple.add(3, "wID " + i);
            tuple.add(4, "rDate " + i);
            tuple.add(5, "rTime" + i);
            tuple.add(6, "numOfPeople" + i);
            tuple.add(7, "reservationName" + i);

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
