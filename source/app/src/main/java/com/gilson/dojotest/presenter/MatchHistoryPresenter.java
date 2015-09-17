package com.gilson.dojotest.presenter;

import com.gilson.dojotest.util.DateUtil;
import com.gilson.dojotest.view.MatchView;
import com.gilson.dojotest.ws.RestApi;
import com.gilson.dojotest.ws.dto.MatchDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
public class MatchHistoryPresenter {
    private final RestApi api;
    MatchView view;

    public MatchHistoryPresenter(MatchView view,  RestApi api) {
        this.view = view;
        this.api = api;
        this.loadData();
    }

    private void loadData() {
        view.showLoading();
        api.queryMatches(-1)
                .map(addTimeDivisions())
                .subscribe(new Subscriber<List<MatchDto>>() {
                    @Override
                    public void onCompleted() {
                        view.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<MatchDto> matchDtos) {
                        view.renderMatches(matchDtos);
                    }
                });
    }

    private Func1<? super List<MatchDto>, List<MatchDto>> addTimeDivisions() {
        return new Func1<List<MatchDto>, List<MatchDto>>() {
            @Override
            public List<MatchDto> call(List<MatchDto> matchDtos) {
                List<MatchDto> newList = new ArrayList<>();
                Date first = matchDtos.get(0).createDate;

                for (int i = 0; i < matchDtos.size(); i++) {
                    MatchDto match = matchDtos.get(i);

                    /**
                     * This needs improvement, i'm not sure how the groupings
                     * comes from the server with the date provided
                     * i changed the date a bit so we would have at least two scenarios for now
                     */
                    if (match.createDate.before(first)) {
                        MatchDto division = new MatchDto();
                        division.dateOnly = true;
                        String dateLabel;
                        if (DateUtil.getDayDateDifference(first, match.createDate) == 1) {
                            dateLabel = "YESTERDAY";
                        } else {
                            dateLabel = DateUtil.getDateMonthYear(match.createDate)
                                    .toUpperCase();
                        }

                        division.dateLabel = dateLabel;

                        newList.add(division);
                    }

                    newList.add(match);
                }

                return newList;
            }
        };
    }
}
