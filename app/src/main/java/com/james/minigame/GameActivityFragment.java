package com.james.minigame;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivityFragment extends Fragment {

    @Bind(R.id.score)
    TextView mScore;
    @Bind(R.id.level)
    TextView mLevel;
    @Bind(R.id.click_btn)
    Button mClickBtn;
    @Bind(R.id.floating_view)
    TextView mFloatingTv;

    private int level;
    private int onClick;
    private int score;
    private int currentScore;
    private List<DBModel> list;
    private int width;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game, container, false);
        ButterKnife.bind(this, v);
        list = new Select().from(DBModel.class).queryList();
        initView();
        getWindowWidth();
        return v;
    }

    private void initView() {
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        level = sharedPreferences.getInt(getResources().getString(R.string.level), 1);
        score = sharedPreferences.getInt(getResources().getString(R.string.score), 0);
        currentScore = sharedPreferences.getInt(getResources().getString(R.string.currentScore), 0);
        onClick = sharedPreferences.getInt(getResources().getString(R.string.onClick), 1);
        mScore.setText(getResources().getString(R.string.score_tv) + currentScore);
        mLevel.setText(getResources().getString(R.string.level_tv) + level);
        mClickBtn.setText(getResources().getString(R.string.plus) + onClick);

    }

    private void setAnimation(TextView tv) {
       tv.animate()
                .alpha(0.0f)
                .setDuration(1000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        tv.setVisibility(View.GONE);
                        tv.setAlpha(1.0f);
                    }
                });
    }

    private void getWindowWidth() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
    }

    @Override
    public void onStop() {
        super.onStop();
        if(level>12){
            gameOver(1, 0, 0, 1);
        } else {
            gameOver(level, score, currentScore, onClick);
        }
    }

    @OnClick(R.id.click_btn)
    void setOnClick(){
        currentScore += onClick;
        if(currentScore >= score){
            level++;
            if(level==13){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getResources().getString(R.string.Ura))
                        .setMessage(getResources().getString(R.string.game_over))
                        .setCancelable(false)
                        .setNegativeButton(getResources().getString(R.string.new_game),
                                (dialog, id) -> {
                                    level = 1;
                                    score = 0;
                                    currentScore = 0;
                                    onClick = 1;
                                    gameOver(level, score, currentScore, onClick);
                                    dialog.cancel();
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
            score = list.get(level-1).getScore();
            onClick = list.get(level-1).getOnClick();
        }
        mScore.setText(getResources().getString(R.string.score_tv) + currentScore);
        mLevel.setText(getResources().getString(R.string.level_tv) + level);
        mClickBtn.setText(getResources().getString(R.string.plus) + onClick);
        mFloatingTv.setText(getResources().getString(R.string.plus) + onClick);
        mFloatingTv.setVisibility(View.VISIBLE);
        Random r = new Random();
        mFloatingTv.setX(r.nextInt(width-16)+16);
        setAnimation(mFloatingTv);
    }

    private void gameOver(int level, int score, int currentScore, int onClick) {
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putInt(getResources().getString(R.string.level), level);
        ed.putInt(getResources().getString(R.string.currentScore), currentScore);
        ed.putInt(getResources().getString(R.string.score), score);
        ed.putInt(getResources().getString(R.string.onClick), onClick);
        ed.apply();
    }

    @OnClick(R.id.help_btn)
    void onHelpBtnClick(){
        GameActivity.changeFragment(new LevelFragment(), true, getActivity(), R.id.container);
    }
}
