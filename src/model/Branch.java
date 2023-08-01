package model;

public class Branch {
    private final int locId;
    private final String streetAddress;
    private final String city;
    private final String province;

    public Branch(int locId, String streetAddress, String city, String province) {
        this.locId = locId;
        this.streetAddress = streetAddress;
        this.city = city;
        this.province = province;
    }

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
}
