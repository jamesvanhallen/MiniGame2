package com.james.minigame;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.raizlabs.android.dbflow.config.BaseDatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.ArrayList;
import java.util.List;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GameActivity extends AppCompatActivity {

    private LevelService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        if(new Select().from(DBModel.class).queryList().size()==0){
            query();
        } else {
            GameActivityFragment frag = new GameActivityFragment();
            changeFragment(frag, false, this, R.id.container);
        }
    }

    private void query() {
        service = ApiFactory.getLevelService();
        service.getLevel()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::createGameFragment);

    }

    private void createGameFragment(List<Level> list){
        for (Level level : list) {
            DBModel model = new DBModel();
            model.setLevel(level.getLv());
            model.setScore(level.getScore());
            model.setOnClick(level.getClick());
            model.save();
        }

        GameActivityFragment frag = new GameActivityFragment();
        changeFragment(frag, false, this, R.id.container);
    }


    public static void changeFragment(Fragment f, boolean addToBackStack, Activity activity, Integer fragContainer) {
        FragmentManager mFm = ((AppCompatActivity)activity).getSupportFragmentManager();
        FragmentTransaction ft = mFm.beginTransaction();

        if (addToBackStack) {
            ft.addToBackStack(null);
        }

        Fragment oldFragment = mFm.findFragmentById(fragContainer);
        if (oldFragment != null) {
            ft.remove(oldFragment);
        }
        ft.add(fragContainer, f);
        ft.commit();
    }

    @Override
    protected void onDestroy() {
        if(service!=null){
            service.getLevel().unsubscribeOn(Schedulers.io());
        }
        super.onDestroy();
    }
}
