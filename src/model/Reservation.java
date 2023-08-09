package model;

import java.time.LocalDate;

public class Reservation {
    private int rId;
    private int cId;
    private int locId;
    private int wId;
    private LocalDate date;
    private String time;
    private int numOfPeople;
    private String reservationName;

    public Reservation(int rId, int cId, int locId, int wId) {
        this.rId = rId;
        this.cId = cId;
        this.locId = locId;
        this.wId = wId;
        this.date = LocalDate.now();
        this.time = "";
        this.numOfPeople = 0;
        this.reservationName = "";
    }

    public Reservation(int rId, int cId, int locId, int wId, LocalDate date, String time, int numOfPeople, String reservationName) {
        this.rId = rId;
        this.cId = cId;
        this.locId = locId;
        this.wId = wId;
        this.date = date;
        this.time = time;
        this.numOfPeople = numOfPeople;
        this.reservationName = reservationName;
    }

    public int getRId() {
        return rId;
    }

    public int getCId() {
        return cId;
    }

    public int getLocId() {
        return locId;
    }

    public int getWId() {
        return wId;
    }

    public LocalDate getDate() {
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
