package com.james.minigame;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by James on 14.09.2015.
 */
@Table(databaseName = "app_db")
public class DBModel extends BaseModel {

    @Column
    @PrimaryKey
    int level;

    @Column
    int score;

    @Column
    int onClick;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getOnClick() {
        return onClick;
    }

    public void setOnClick(int onClick) {
        this.onClick = onClick;
    }
}
