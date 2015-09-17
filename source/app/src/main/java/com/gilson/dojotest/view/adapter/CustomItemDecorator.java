package com.gilson.dojotest.view.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gilson.dojotest.R;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
public class CustomItemDecorator extends RecyclerView.ItemDecoration {
    private final Context context;
    private final Drawable separator;

    public CustomItemDecorator(Context context) {
        this.context = context;
        this.separator = context.getResources()
                .getDrawable(R.drawable.separator_bg);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = 70;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent) {
        int center = parent.getPaddingLeft() + parent.getWidth() / 2;
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 1; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getTop() + params.topMargin;
            separator.setBounds((int) (center - convertPixelsToDp(10)),
                    top - 70,
                    (int) (center + convertPixelsToDp(10)),
                    (int) top);

            separator.draw(c);
        }
    }

    private float convertPixelsToDp(float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }
}
