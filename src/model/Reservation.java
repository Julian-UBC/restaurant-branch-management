package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private final int rID;
    private final int cID;
    private final int locID;
    private final int wID;
    private final LocalDate rDate;
    private final LocalTime rTime;
    private final int numOfPeople;
    private final String reservationName;

    public Reservation(int rID, int cID, int locID, int wID, LocalDate rDate, LocalTime rTime, int numOfPeople,
                       String reservationName) {
        this.rID = rID;
        this.cID = cID;
        this.locID = locID;
        this.wID = wID;
        this.rDate = rDate;
        this.rTime = rTime;
        this.numOfPeople = numOfPeople;
        this.reservationName = reservationName;
    }

    public int getRID() {
        return rID;
    }

    public int getCID() {
        return cID;
    }

    public int getLocID() {
        return locID;
    }

    public int getWID() {
        return wID;
    }

    public LocalDate getRDate() {
        return rDate;
    }

    public LocalTime getRTime() {
        return rTime;
    }

    public int getNumOfPeople() {
        return numOfPeople;
    }

    public String getReservationName() {
        return reservationName;
    }
}
