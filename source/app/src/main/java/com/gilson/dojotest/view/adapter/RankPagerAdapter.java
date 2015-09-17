package com.gilson.dojotest.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gilson.dojotest.R;
import com.gilson.dojotest.view.ViewUtil;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
public class RankPagerAdapter extends PagerAdapter {
    private final Context context;
    private final RankPagerDto[] data;
    private final IOnItemClickListener<RankPagerDto> clickListener;

    public RankPagerAdapter(Context context, RankPagerDto[] performances, IOnItemClickListener<RankPagerDto> clickListener) {
        this.data = performances;
        this.context = context;
        this.clickListener = clickListener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.match_history_item_pager, container, false);
        ImageView img = (ImageView) view.findViewById(R.id.imgMatchRank);
        TextView txt = (TextView) view.findViewById(R.id.txtMatchRank);

        final RankPagerDto dto = data[position];

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(v, dto);
            }
        });

        String labelPerformance = dto.performance;

        img.setImageResource(ViewUtil.getBadgeResource(labelPerformance));

        String label = labelPerformance.substring(0, 1).toUpperCase() +
                labelPerformance.substring(1) + " " +
                context.getResources().getString(R.string.performance);
        txt.setText(label);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
