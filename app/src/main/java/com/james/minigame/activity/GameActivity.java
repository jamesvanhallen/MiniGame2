package com.james.minigame.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.james.minigame.connection.ApiFactory;
import com.james.minigame.database.DBModel;
import com.james.minigame.fragment.GameActivityFragment;
import com.james.minigame.pojo.Level;
import com.james.minigame.connection.LevelService;
import com.james.minigame.R;
import com.raizlabs.android.dbflow.sql.language.Select;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GameActivity extends AppCompatActivity {

    private Observable<List<Level>> mSubscription;

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
        LevelService service = ApiFactory.getLevelService();
        mSubscription = service.getLevel();
        mSubscription.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::from)
                .subscribe(
                        this::saveLevel,
                        Throwable::printStackTrace,
                        this::onCompleted);

    }

    private void saveLevel(Level level) {
        DBModel model = new DBModel();
        model.setLevel(level.getLv());
        model.setScore(level.getScore());
        model.setOnClick(level.getClick());
        model.save();
    }

    private void onCompleted() {
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
    protected void onStop() {
        if(mSubscription !=null){
            mSubscription.unsubscribeOn(Schedulers.io());
        }
        super.onStop();
    }
}
