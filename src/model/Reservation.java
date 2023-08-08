package model;

import java.time.LocalDate;
import java.time.LocalTime;


public class Reservation {
    int rId;
    int cId;
    int locId;
    int wId;
    LocalDate date;
    LocalTime time;
    int NumOfPeople;
    String reservationName;

    public Reservation(int rId, int cId, int locId, int wId, LocalDate date, LocalTime time, 
                       int NumOfPeople, String reservationName) {
        this.rId = rId;
        this.cId = cId;
        this.locId = locId;
        this.wId = wId;
        this.date = date;
        this.time = time;
        this.NumOfPeople = NumOfPeople;
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

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public int getNumOfPeople() {
        return NumOfPeople;
    }

    public String getReservationName() {
        return reservationName;
    }
}
