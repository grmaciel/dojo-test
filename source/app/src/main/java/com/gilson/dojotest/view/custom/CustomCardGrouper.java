package com.gilson.dojotest.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gilson.dojotest.R;
import com.gilson.dojotest.view.ViewUtil;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
public class CustomCardGrouper extends LinearLayout implements View.OnTouchListener {
    private OnLayoutChangeListener layoutChangeListener;
    private float mStartY;
    private float mStartViewY;
    private boolean moving = false;
    private float movableCardY;
    private ICardListener cardListener;
    private View bottom;
    private String matchStatus;
    private String champion;
    private String matchDetail;
    private int badgeResource;

    public CustomCardGrouper(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.configureLayout();
    }

    public void configureCard(String matchStatus,
                              String champion,
                              String matchDetail,
                              int badgeResource,
                              ICardListener cardListener) {
        this.cardListener = cardListener;
        this.matchStatus = matchStatus;
        this.champion = champion;
        this.matchDetail = matchDetail;
        this.badgeResource = badgeResource;
        this.populateCardData();
    }

    private void configureLayout() {
        layoutChangeListener = new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                addCards();

                CustomCardGrouper.this.removeOnLayoutChangeListener(layoutChangeListener);
            }
        };

        this.addOnLayoutChangeListener(layoutChangeListener);
    }

    private void addCards() {
        /**
         * Fixed rendering of three cards
         * the bottom being the only one that can be dragged
         */
        int smallHeight = (int) (this.getHeight() * 0.3);

        this.bottom = LayoutInflater.from(getContext()).inflate(R.layout.movable_card_layout, null);//new View(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, this.getHeight());
        bottom.setLayoutParams(layoutParams);
        bottom.setBackgroundResource(R.drawable.card_background);
        movableCardY = bottom.getY();

        View medium = new View(getContext());
        LayoutParams mediumParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getPixelFromDp(50));
        mediumParams.setMargins(20, 0, 20, 0);
        medium.setLayoutParams(mediumParams);
        medium.setBackgroundResource(R.drawable.card_background);
        medium.setY(bottom.getY() + smallHeight / 1.5f);

        View top = new View(getContext());
        LayoutParams topParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getPixelFromDp(50));
        topParams.setMargins(40, 0, 40, 0);
        top.setLayoutParams(topParams);
        top.setBackgroundResource(R.drawable.card_background);
        top.setY(medium.getY() + smallHeight / 1.5f);

        this.addView(top);
        this.addView(medium);
        this.addView(bottom);

        bottom.setOnTouchListener(this);
    }

    private void populateCardData() {
        if (bottom == null) {
            recreateView();
            return;
        }

        TextView txt = (TextView) bottom.findViewById(R.id.txtCardStatus);
        txt.setText(ViewUtil.getMatchPlayerSpannable(getContext(), matchStatus, champion));
        TextView txtMatch = (TextView) bottom.findViewById(R.id.txtCardMatch);
        txtMatch.setText(matchDetail);
        ImageView img = (ImageView) bottom.findViewById(R.id.imgMatchRank);
        img.setImageResource(badgeResource);
    }

    private int getPixelFromDp(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getContext().getResources().getDisplayMetrics());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        /**
         * Allow a maximum amount of dragging for the bottom card
         * before firing the event listener, if the amount is not reached
         * move the card back to its initial position.
         */
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartY = event.getY();
                mStartViewY = v.getY();
                moving = true;
                break;

            case MotionEvent.ACTION_MOVE:
                if (!moving) {
                    v.setY(movableCardY);
                    return false;
                }

                float currentY = event.getY();
                float dY = currentY - mStartY;

                if (dY > 0) {
                    break;
                }

                animateViewToPosition(v, v.getY() + dY);

                if (mStartViewY - v.getY() > 100) {
                    moving = false;
                    cardListener.onCardDragged();
                    this.recreateView();
                    break;
                }

                return false;

            case MotionEvent.ACTION_UP:
                moving = false;
                float v1 = v.getY() - mStartViewY;

                if (v1 <= 0 && v1 > -10) {
                    cardListener.onCardClick();
                } else {
                    animateViewToPosition(v, mStartViewY);
                }

                break;

        }
        return true;
    }

    private void animateViewToPosition(View v, float value) {
        v.animate()
                .y(value)
                .setDuration(0)
                .start();
    }

    private void recreateView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                removeAllViews();
                addCards();
                populateCardData();
            }
        }, 500);
    }
}
