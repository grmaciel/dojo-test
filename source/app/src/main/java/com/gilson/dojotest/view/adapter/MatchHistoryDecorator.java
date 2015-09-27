package com.gilson.dojotest.view.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
    private int dividerHeight = 82;
    private float dividerWidth = 10;

    public MatchHistoryDecorator(Context context) {
        this.context = context;
        this.separator = context.getResources()
                .getDrawable(R.drawable.match_separator_bg);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {


        int pos = parent.getChildAdapterPosition(view);
        MatchHistoryAdapter adapter = (MatchHistoryAdapter) parent.getAdapter();
        MatchDto item = adapter.getItem(pos);
        if (item.dateOnly) {
            outRect.bottom = dividerHeight;
        } else {
            outRect.bottom = dividerHeight/2;
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent) {
        int center = parent.getLeft() + parent.getWidth() / 2;

        for (int i = 1; i < parent.getChildCount(); i++) {
//            parent.getada
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getTop() + params.topMargin;
            separator.setBounds((int) (center - convertPixelsToDp(dividerWidth)),
                    top - dividerHeight,
                    (int) (center + convertPixelsToDp(dividerWidth)),
                    top);

            separator.draw(c);
        }
    }

    private float convertPixelsToDp(float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }
}
