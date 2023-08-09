package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class JoinedBranchReservation {
    private final List<String> columns = new ArrayList();
    private List<Vector<Object>> tuples;

    public JoinedBranchReservation() {
        this.columns.add("Branch ID");
        this.columns.add("City");
        this.columns.add("Reservation Name");
        this.columns.add("Date");
        this.tuples = new ArrayList();
    }

    public List<String> getColumns() {
        return Collections.unmodifiableList(this.columns);
    }

    public List<Vector<Object>> getTuples() {
        return Collections.unmodifiableList(this.tuples);
    }

    public void addJoinedBranchReservation(Branch branch, Reservation reservation) {
        Vector<Object> tuple = new Vector();
        tuple.add(0, branch.getLocId());
        tuple.add(1, branch.getCity());
        tuple.add(2, reservation.getReservationName());
        tuple.add(3, reservation.getDate());
        this.tuples.add(tuple);
    }
}
