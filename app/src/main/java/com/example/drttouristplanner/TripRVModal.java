package com.example.drttouristplanner;

import android.os.Parcel;
import android.os.Parcelable;

public class TripRVModal implements Parcelable {
    private String tripTitle;
    private String tripName;
    private String tripStartDate;
    private String tripEndDate;
    private String tripDescription;
    private String tripID;


    public TripRVModal(){

    }

    //getter and setter

    protected TripRVModal(Parcel in) {
        tripTitle = in.readString();
        tripName = in.readString();
        tripStartDate = in.readString();
        tripEndDate = in.readString();
        tripDescription = in.readString();
        tripID = in.readString();
    }

    public static final Creator<TripRVModal> CREATOR = new Creator<TripRVModal>() {
        @Override
        public TripRVModal createFromParcel(Parcel in) {
            return new TripRVModal(in);
        }

        @Override
        public TripRVModal[] newArray(int size) {
            return new TripRVModal[size];
        }
    };

    public String getTripTitle() {
        return tripTitle;
    }

    public void setTripTitle(String tripTitle) {
        this.tripTitle = tripTitle;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getTripStartDate() {
        return tripStartDate;
    }

    public void setTripStartDate(String tripStartDate) {
        this.tripStartDate = tripStartDate;
    }

    public String getTripEndDate() {
        return tripEndDate;
    }

    public void setTripEndDate(String tripEndDate) {
        this.tripEndDate = tripEndDate;
    }

    public String getTripDescription() {
        return tripDescription;
    }

    public void setTripDescription(String tripDescription) {
        this.tripDescription = tripDescription;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    //constructor
    public TripRVModal(String tripTitle, String tripName, String tripStartDate, String tripEndDate, String tripDescription, String tripID) {
        this.tripTitle = tripTitle;
        this.tripName = tripName;
        this.tripStartDate = tripStartDate;
        this.tripEndDate = tripEndDate;
        this.tripDescription = tripDescription;
        this.tripID = tripID;
    }

    //parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(tripTitle);
        parcel.writeString(tripName);
        parcel.writeString(tripStartDate);
        parcel.writeString(tripEndDate);
        parcel.writeString(tripDescription);
        parcel.writeString(tripID);
    }
}
