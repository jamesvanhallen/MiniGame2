package com.james.minigame.pojo;

import com.google.gson.annotations.SerializedName;


public class Level{

    public Level() {
    }

    @SerializedName("level")
    private int mLv;

    @SerializedName("score")
    private int mScore;

    @SerializedName("onclick")
    private int mClick;

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
