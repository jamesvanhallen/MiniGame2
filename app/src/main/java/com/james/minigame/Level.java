package com.james.minigame;

import com.google.gson.annotations.SerializedName;


public class Level{

    @SerializedName("level")
    private int mLv;

    @SerializedName("score")
    private int mScore;

    @SerializedName("onclick")
    private int mClick;

    public Level() {
    }


    public int getLv() {
        return mLv;
    }

    public int getScore() {
        return mScore;
    }

    public int getClick() {
        return mClick;
    }

}
