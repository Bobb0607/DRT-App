package com.example.drttouristplanner;

public class MainModel {

    String place_id, trip_id, trip_spot, trip_start, trip_name, trip_end, trip_desc;

    MainModel()
    {

    }

//constructor
    public MainModel(String place_id, String trip_id, String trip_spot, String trip_start, String trip_name, String trip_end, String trip_desc) {
        this.trip_id = trip_id;
        this.place_id = place_id;
        this.trip_spot = trip_spot;
        this.trip_start = trip_start;
        this.trip_name = trip_name;
        this.trip_end = trip_end;
        this.trip_desc = trip_desc;


//getter and setter
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }

    public String getTrip_spot() {
        return trip_spot;
    }

    public void setTrip_spot(String trip_spot) {
        this.trip_spot = trip_spot;
    }

    public String getTrip_start() {
        return trip_start;
    }

    public void setTrip_start(String trip_start) {
        this.trip_start = trip_start;
    }

    public String getTrip_name() {
        return trip_name;
    }

    public void setTrip_name(String trip_name) {
        this.trip_name = trip_name;
    }

    public String getTrip_end() {
        return trip_end;
    }

    public void setTrip_end(String trip_end) {
        this.trip_end = trip_end;
    }

    public String getTrip_desc() {
        return trip_desc;
    }

    public void setTrip_desc(String trip_desc) {
        this.trip_desc = trip_desc;
    }
}
