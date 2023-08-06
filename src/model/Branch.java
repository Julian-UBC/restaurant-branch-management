package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class Branch {
    private List<String> columns;
    private List<Vector<Object>> tuples;


    public Branch() {
        columns = new ArrayList<>();
        columns.add("locId");
        columns.add("streetAddress");
        columns.add("city");
        columns.add("province");

        tuples = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            Vector<Object> tuple = new Vector<>();
            tuple.add(0, "locId " + i);
            tuple.add(1, "streetAddress " + i);
            tuple.add(2, "city " + i);
            tuple.add(3, "province" + i);
            tuples.add(tuple);
        }
    }

    public List<String> getColumns() {
        return Collections.unmodifiableList(columns);
    }

    public List<Vector<Object>> getTuples() {
        return Collections.unmodifiableList(tuples);
    }
    /*
    public int getLocId(){
        return locId;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }
     */
}
