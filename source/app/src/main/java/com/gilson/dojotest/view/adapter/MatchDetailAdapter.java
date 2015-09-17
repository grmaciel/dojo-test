package com.gilson.dojotest.view.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gilson.dojotest.R;
import com.gilson.dojotest.view.activity.MatchDetailActivity;
import com.gilson.dojotest.ws.dto.MatchDetailDto;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Gilson Maciel on 17/09/2015.
 */
public class MatchDetailAdapter extends RecyclerView.Adapter<MatchDetailAdapter.DetailViewHolder> {
    private final Context context;
    private final List<MatchDetailDto> data;

    public MatchDetailAdapter(Context context, List<MatchDetailDto> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.match_detail_item, parent, false);

        DetailViewHolder holder = new DetailViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(DetailViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class DetailViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imgBadgeDetail)
        ImageView imgBadge;
        @Bind(R.id.txtMatchDetail)
        TextView txtMatch;
        @Bind(R.id.txtPerformanceDetail)
        TextView txtPerformance;

        public DetailViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
