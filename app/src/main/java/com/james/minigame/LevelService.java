package com.james.minigame;

import java.util.List;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface LevelService {

    @GET("/bins/yjyc")
    Observable<List<Level>> getLevel();
}