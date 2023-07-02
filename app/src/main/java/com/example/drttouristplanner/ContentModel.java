package com.example.drttouristplanner;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContentModel implements Serializable {


    private String placeID;
    private String placeCategoryName;
    private String BarangayName;
    private String mTitle;
    private String mDetails;
    private String mInfo;
    private String mAverageRating;
    private String latlong_value;
    private List<SlideModel> mImages = new ArrayList<>();
    private ArrayList<Integer> mImagesInt = new ArrayList<>();

    public ContentModel(String place_id,String LatLongValue, String barangayName,String place_name, String title,String details, String info, String averageRating,int image) {
        placeCategoryName = place_name;
       latlong_value = LatLongValue;
        placeID = place_id;
        BarangayName = barangayName;
        mTitle = title;
        mDetails = details;
        mInfo = info;
        mAverageRating = averageRating;

        mImages.add(new SlideModel(image, ScaleTypes.FIT));
    }

    public ContentModel(String place_id,String LatLongValue,String barangay_name, String place_category_name, String title, String details,String info, String averageRating, int[] images) {
        placeID = place_id;
        latlong_value = LatLongValue;
        BarangayName = barangay_name;
        placeCategoryName = place_category_name;
        mTitle = title;
        mDetails = details;
        mInfo = info;
        mAverageRating = averageRating;

        for(int i=0; i<images.length; i++){
            mImages.add(new SlideModel(images[i], ScaleTypes.FIT));
        }

        for(int i=0; i<images.length; i++){
            mImagesInt.add(images[i]);
        }
    }

    public String getLatlong_value() {
        return latlong_value;
    }

    public void setLatlong_value(String latlong_value) {
        this.latlong_value = latlong_value;
    }

    public String getPlaceCategoryName() {
        return placeCategoryName;
    }

    public void setPlaceCategoryName(String placeCategoryName) {
        this.placeCategoryName = placeCategoryName;
    }



    public String getBarangayName() {
        return BarangayName;
    }

    public void setBarangayName(String barangayName) {
        BarangayName = barangayName;
    }

    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

    public String getmAverageRating() {
        return mAverageRating;
    }

    public void setmAverageRating(String mAverageRating) {
        this.mAverageRating = mAverageRating;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDetails() {
        return mDetails;
    }

    public String getInfo() {
        return mInfo;
    }

    public List<SlideModel> getImage() {
        return mImages;
    }

    public ArrayList<Integer> getImageAsInt(){
        return mImagesInt;

    }

    private static int lastContentId = 0;
}
