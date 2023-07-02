package com.example.drttouristplanner;

public class PlaceCategory {
    private String placeCategoryID;
    private String placeCategoryName;

    public PlaceCategory(String placeCategoryID, String placeCategoryName) {
        this.placeCategoryID = placeCategoryID;
        this.placeCategoryName = placeCategoryName;
    }

    public String getPlaceCategoryID() {
        return placeCategoryID;
    }

    public void setPlaceCategoryID(String placeCategoryID) {
        this.placeCategoryID = placeCategoryID;
    }

    public String getPlaceCategoryName() {
        return placeCategoryName;
    }

    public void setPlaceCategoryName(String placeCategoryName) {
        this.placeCategoryName = placeCategoryName;
    }
}
