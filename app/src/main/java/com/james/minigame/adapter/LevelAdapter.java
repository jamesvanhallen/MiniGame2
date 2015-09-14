package com.james.minigame.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.james.minigame.R;
import com.james.minigame.database.DBModel;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by James on 14.09.2015.
 */
public class LevelAdapter extends BaseAdapter {
    private List<DBModel> mHotels;
    private Context mContext;

    public LevelAdapter(Context context) {
        super();
        mContext = context;
    }

    @Override
    public int getCount() {
        return mHotels == null ? 0 : mHotels.size();
    }

    @Override
    public DBModel getItem(int position) {
        return mHotels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder h;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_level, parent, false);

            h = new ViewHolder(convertView);
            convertView.setTag(h);
        } else {
            h = (ViewHolder) convertView.getTag();
        }

        h.bind(getItem(position), mContext);

        return convertView;
    }

    public void setItems(List<DBModel> myItems) {
        mHotels = myItems;
        notifyDataSetChanged();
    }

    public static class ViewHolder {

        @Bind(R.id.level)
        TextView mLevel;
        @Bind(R.id.score)
        TextView mScore;
        @Bind(R.id.onclick)
        TextView mOnClick;

        public ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }

        public void bind(DBModel item, Context context) {

            mLevel.setText(String.format(context.getResources().getString(R.string.level_rus), item.getLevel()));
            mScore.setText(String.format(context.getResources().getString(R.string.next_lvl_rus), item.getScore()));
            mOnClick.setText(String.format(context.getResources().getString(R.string.on_click_rus), item.getOnClick()));
        }
    }
}

