package com.example.drttouristplanner;

public class ItineraryClass {
    String itinerary_id, itinerary_name, itinerary_spot, category_name, place_id, plan_start_date, plan_end_date, plan_start_time, plan_end_time, trip_id, trip_name, user_id;

    public ItineraryClass(String itinerary_id, String itinerary_name, String category_name, String place_id, String plan_start_date, String plan_end_date, String plan_start_time, String plan_end_time, String trip_id, String trip_name, String user_id) {
        this.itinerary_id = itinerary_id;
        this.itinerary_name = itinerary_name;
        this.itinerary_spot = itinerary_spot;
        this.category_name = category_name;
        this.place_id = place_id;
        this.plan_start_date = plan_start_date;
        this.plan_end_date = plan_end_date;
        this.plan_start_time = plan_start_time;
        this.plan_end_time = plan_end_time;
        this.trip_id = trip_id;
        this.trip_name = trip_name;
        this.user_id = user_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getItinerary_id() {
        return itinerary_id;
    }

    public void setItinerary_id(String itinerary_id) {
        this.itinerary_id = itinerary_id;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getPlan_start_date() {
        return plan_start_date;
    }

    public void setPlan_start_date(String plan_start_date) {
        this.plan_start_date = plan_start_date;
    }

    public String getPlan_end_date() {
        return plan_end_date;
    }

    public void setPlan_end_date(String plan_end_date) {
        this.plan_end_date = plan_end_date;
    }

    public String getPlan_start_time() {
        return plan_start_time;
    }

    public void setPlan_start_time(String plan_start_time) {
        this.plan_start_time = plan_start_time;
    }

    public String getItinerary_name() {
        return itinerary_name;
    }

    public  String  getItinerary_spot(){
        return  itinerary_spot;
    }

    public void setItinerary_name(String itinerary_name) {
        this.itinerary_name = itinerary_name;
    }

    public void setItinerary_spot(String itinerary_spot) {this.itinerary_spot = itinerary_spot; }

    public String getPlan_end_time() {
        return plan_end_time;
    }

    public void setPlan_end_time(String plan_end_time) {
        this.plan_end_time = plan_end_time;
    }

    public String getTrip_name() {
        return trip_name;
    }

    public void setTrip_name(String trip_name) {
        this.trip_name = trip_name;
    }

    public String getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
