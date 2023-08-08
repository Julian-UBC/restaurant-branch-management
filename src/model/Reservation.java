package model;

public class Reservation {
    private int rId;
    private int cId;
    private int locId;
    private int wId;
    private String date;
    private String time;
    private int numOfPeople;
    private String reservationName;

    public Reservation(int rId, int cId, int locId, int wId) {
        this.rId = rId;
        this.cId = cId;
        this.locId = locId;
        this.wId = wId;
        this.date = "";
        this.time = "";
        this.numOfPeople = 0;
        this.reservationName = "";
    }

    public Reservation(int rId, int cId, int locId, int wId, String date, String time, int numOfPeople, String reservationName) {
        this.rId = rId;
        this.cId = cId;
        this.locId = locId;
        this.wId = wId;
        this.date = date;
        this.time = time;
        this.numOfPeople = numOfPeople;
        this.reservationName = reservationName;
    }

    public int getrId() {
        return rId;
    }

    public int getcId() {
        return cId;
    }

    public int getLocId() {
        return locId;
    }

    public int getwId() {
        return wId;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getNumOfPeople() {
        return numOfPeople;
    }

    public String getReservationName() {
        return reservationName;
    }
}
