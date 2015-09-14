package com.james.minigame.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.james.minigame.database.DBModel;
import com.james.minigame.adapter.LevelAdapter;
import com.james.minigame.R;
import com.raizlabs.android.dbflow.sql.language.Select;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by James on 14.09.2015.
 */
public class LevelFragment extends Fragment {

    @Bind(R.id.lv)
    ListView mLv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_level, container, false);
        ButterKnife.bind(this, v);
        LevelAdapter adapter = new LevelAdapter(getActivity());
        mLv.setAdapter(adapter);
        Observable.create(new Observable.OnSubscribe<List<DBModel>>() {
            @Override
            public void call(Subscriber<? super List<DBModel>> subscriber) {
                subscriber.onNext(new Select().from(DBModel.class).queryList());
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter::setItems);
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
