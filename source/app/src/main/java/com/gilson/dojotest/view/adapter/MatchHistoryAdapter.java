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
import com.gilson.dojotest.view.ViewUtil;
import com.gilson.dojotest.ws.dto.MatchDto;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
public class MatchHistoryAdapter extends RecyclerView.Adapter {
    private static final int DATE_DIVIDER = 0;
    private static final int REGULAR_LAYOUT = 1;

    private final List<MatchDto> items;
    private final Context context;
    private final IOnItemClickListener<MatchDto> listener;

    public MatchHistoryAdapter(Context context, List<MatchDto> items, IOnItemClickListener<MatchDto> listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder;

        if (viewType == REGULAR_LAYOUT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.match_history_item, parent, false);
            holder = new PlayerViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.match_date_item, parent, false);
            holder = new DateViewHolder(view);
        }

        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        MatchDto match = items.get(position);
        return match.dateOnly ?
                DATE_DIVIDER : REGULAR_LAYOUT;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MatchDto item = items.get(position);

        if (getItemViewType(position) == REGULAR_LAYOUT) {
            PlayerViewHolder customHolder = (PlayerViewHolder) holder;
            setupViewPager(customHolder, position, item);

            customHolder.txtStatus.setText(ViewUtil.getMatchPlayerSpannable(context,
                    ViewUtil.getMatchStatus(context, item.win),
                    item.champion));
            customHolder.txtRank.setText(item.gameMode + " • " + item.lane + " " +
                    context.getResources().getString(R.string.lane));

            customHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v, item);
                }
            });
        } else {
            DateViewHolder customHolder = (DateViewHolder) holder;
            customHolder.txtPeriod.setText(item.dateLabel);
        }
    }

    private void setupViewPager(final PlayerViewHolder holder, int position, MatchDto item) {
        RankPagerDto dto = new RankPagerDto();
        dto.parentPosition = position;
        dto.performance = item.totalPerformance;

        /**
         * Just adding an extra item to the first position so you can see the side scroll
         * between badges, the json wasn't very clear on how this was suppose to work
         */
        if (position == 0) {
            RankPagerDto extra = new RankPagerDto();
            extra.parentPosition = position;
            extra.performance = "gold";

            RankPagerDto[] pages = {dto, extra};

            holder.playerRankPager.setAdapter(new RankPagerAdapter(context,
                    pages,
                    getOnPagerItemClick()));
            holder.playerRankPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    configureScrollArrow(holder);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        } else {
            holder.playerRankPager.setAdapter(new RankPagerAdapter(context,
                    new RankPagerDto[]{dto},
                    getOnPagerItemClick()));
        }

        configureScrollArrow(holder);
    }

    private void configureScrollArrow(final PlayerViewHolder holder) {
        int childCount = holder.playerRankPager.getAdapter().getCount();

        final int currentItem = holder.playerRankPager.getCurrentItem();

        if (currentItem > 0) {
            holder.arrowLeft.setVisibility(View.VISIBLE);
            holder.arrowLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.playerRankPager.setCurrentItem(currentItem-1);
                }
            });
        } else {
            holder.arrowLeft.setVisibility(View.INVISIBLE);
        }

        if (currentItem < childCount - 1) {
            holder.arrowRight.setVisibility(View.VISIBLE);
            holder.arrowRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.playerRankPager.setCurrentItem(currentItem+1);
                }
            });
        } else {
            holder.arrowRight.setVisibility(View.INVISIBLE);
        }
    }

    private IOnItemClickListener<RankPagerDto> getOnPagerItemClick() {
        return new IOnItemClickListener<RankPagerDto>() {
            @Override
            public void onClick(View view, RankPagerDto data) {
                listener.onClick(view, items.get(data.parentPosition));
            }
        };
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class PlayerViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imgPlayerAvatar)
        ImageView avatar;
        @Bind(R.id.matchRankPager)
        ViewPager playerRankPager;
        @Bind(R.id.txtMatchStatus)
        TextView txtStatus;
        @Bind(R.id.txtMatchRank)
        TextView txtRank;
        @Bind(R.id.imgArrowLeft)
        ImageView arrowLeft;
        @Bind(R.id.imgArrowRight)
        ImageView arrowRight;

        public PlayerViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    static class DateViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtMatchPeriod)
        TextView txtPeriod;

        public DateViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
