package com.example.drttouristplanner;

import android.content.Context;

public class ContextActivityClass {
    Context mContext;

    public ContextActivityClass(Context mContext) {
        this.mContext = mContext;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }
}
