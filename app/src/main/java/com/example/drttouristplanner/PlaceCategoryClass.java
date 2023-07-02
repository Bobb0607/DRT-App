package com.example.drttouristplanner;

public class PlaceCategoryClass {
    String place_category_id, place_category_name, barangay_name;

    public PlaceCategoryClass(String place_category_id, String place_category_name, String barangay_name) {
        this.place_category_id = place_category_id;
        this.place_category_name = place_category_name;
        this.barangay_name = barangay_name;
    }

    public String getPlace_category_id() {
        return place_category_id;
    }

    public void setPlace_category_id(String place_category_id) {
        this.place_category_id = place_category_id;
    }

    public String getPlace_category_name() {
        return place_category_name;
    }

    public void setPlace_category_name(String place_category_name) {
        this.place_category_name = place_category_name;
    }

    public String getBarangay_name() {
        return barangay_name;
    }

    public void setBarangay_name(String barangay_name) {
        this.barangay_name = barangay_name;
    }
}
