package com.james.minigame.connection;

import com.james.minigame.pojo.Level;

import java.util.List;

import retrofit.http.GET;
import rx.Observable;

public interface LevelService {

    @GET("/bins/yjyc")
    Observable<List<Level>> getLevel();
}