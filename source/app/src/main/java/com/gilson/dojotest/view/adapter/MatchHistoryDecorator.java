package com.gilson.dojotest.view.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gilson.dojotest.R;

import com.gilson.dojotest.ws.dto.MatchDto;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
public class MatchHistoryDecorator extends RecyclerView.ItemDecoration {
    private final Context context;
    private final Drawable separator;
    private int dividerHeight = 41;
    private float dividerWidth = 5;

    public MatchHistoryDecorator(Context context) {
        this.context = context;
        this.separator = context.getResources()
                .getDrawable(R.drawable.match_separator_bg);

        float density = context.getResources().getDisplayMetrics().density;
        dividerHeight = (int) (dividerHeight * density);
        dividerWidth = dividerWidth * density;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = dividerHeight;
        int pos = parent.getChildAdapterPosition(view);
        MatchHistoryAdapter adapter = (MatchHistoryAdapter) parent.getAdapter();
        MatchDto item = adapter.getItem(pos);
        if (item.dateOnly) {
            outRect.bottom = dividerHeight;
        } else {
            outRect.bottom = dividerHeight / 2;
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent) {
        int center = parent.getLeft() + parent.getWidth() / 2;

        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int pos = parent.getChildAdapterPosition(child);
            MatchHistoryAdapter adapter = (MatchHistoryAdapter) parent.getAdapter();
            MatchDto item = adapter.getItem(pos);
            int bottom = child.getBottom() + params.bottomMargin;
            if (!item.dateOnly) {
                bottom -= dividerHeight / 2;
            }

            separator.setBounds((int) (center - convertPixelsToDp(dividerWidth)),
                    bottom,
                    (int) (center + convertPixelsToDp(dividerWidth)),
                    bottom + dividerHeight);

            separator.draw(c);
        }
    }

    private float convertPixelsToDp(float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }
}
