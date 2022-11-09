package com.example.drttouristplanner;

public class iteniraryModel {

    private String mname;
    private String mstart;
    private String mend;
    private int mimage;

    public iteniraryModel(String name, String start, String end,int image) {
        mname = name;
        mstart = start;
        mend = end;
        mimage = image;
    }

    public String getName() {
        return mname;
    }

    public String getStart() {
        return mstart;
    }

    public String getEnd() {
        return mend;
    }

    public int getImage() {
        return mimage;
    }

    private static int lastContentId = 0;
}
