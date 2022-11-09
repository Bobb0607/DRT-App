package com.example.drttouristplanner;

public class TripModel {
    private String spotname;
    private String name;
    private String imageURL;
    private String key;
    private String startDate;
    private String endDate;
    private String description;
    private int position;


    public TripModel () {
        //empty constructor needed
    }
    public TripModel (int position) {
        this.position = position;
    }

    public String getSpotname() {
        return spotname;
    }

    public void setSpotname(String spotname) {
        this.spotname = spotname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public TripModel (String spotname, String name, String imageURL, String startDate, String endDate, String des) {

        if (name.trim().equals("")){
            name = "no name";
        }
        this.spotname = spotname;
        this.name = name;
        this.imageURL = imageURL;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = des;


    }

}
