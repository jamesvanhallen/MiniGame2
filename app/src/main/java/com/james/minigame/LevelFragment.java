package com.james.minigame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.raizlabs.android.dbflow.sql.language.Select;

import butterknife.Bind;
import butterknife.ButterKnife;

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
        adapter.setItems(new Select().from(DBModel.class).queryList());
        mLv.setAdapter(adapter);
        return v;
    }
}
