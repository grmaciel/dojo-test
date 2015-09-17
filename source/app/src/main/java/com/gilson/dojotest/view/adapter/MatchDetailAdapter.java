package com.gilson.dojotest.view.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gilson.dojotest.R;
import com.gilson.dojotest.view.ViewUtil;
import com.gilson.dojotest.view.activity.MatchDetailActivity;
import com.gilson.dojotest.ws.dto.MatchDetailDto;
import com.gilson.dojotest.ws.dto.PerformanceDto;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Gilson Maciel on 17/09/2015.
 */
public class MatchDetailAdapter extends RecyclerView.Adapter<MatchDetailAdapter.DetailViewHolder> {
    private final Context context;
    private final List<PerformanceDto> data;

    public MatchDetailAdapter(Context context, List<PerformanceDto> data) {
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
        PerformanceDto dto = data.get(position);
        holder.txtMatch.setText(dto.label);
        holder.txtPerformance.setText(dto.performance + " " + context.getResources().getString(R.string.performance));
        holder.imgBadge.setImageResource(ViewUtil.getBadgeResource(dto.performance));
        holder.ratingMatchDetail.setRating(ViewUtil.getBadLevel(dto.performance));
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
        @Bind(R.id.ratingMatchDetail)
        RatingBar ratingMatchDetail;

        public DetailViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
