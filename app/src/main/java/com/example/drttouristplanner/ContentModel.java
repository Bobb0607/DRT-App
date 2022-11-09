package com.example.drttouristplanner;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContentModel implements Serializable {
    private String mTitle;
    private String mDetails;
    private List<SlideModel> mImages = new ArrayList<>();
    private ArrayList<Integer> mImagesInt = new ArrayList<>();


    public ContentModel(String title, String details, int image) {
        mTitle = title;
        mDetails = details;

        mImages.add(new SlideModel(image, ScaleTypes.FIT));
    }

    public ContentModel(String title, String details, int[] images) {
        mTitle = title;
        mDetails = details;

        for(int i=0; i<images.length; i++){
            mImages.add(new SlideModel(images[i], ScaleTypes.FIT));
        }

        for(int i=0; i<images.length; i++){
            mImagesInt.add(images[i]);
        }
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDetails() {
        return mDetails;
    }

    public List<SlideModel> getImage() {
        return mImages;
    }

    public ArrayList<Integer> getImageAsInt(){
        return mImagesInt;

    }

    private static int lastContentId = 0;
}
