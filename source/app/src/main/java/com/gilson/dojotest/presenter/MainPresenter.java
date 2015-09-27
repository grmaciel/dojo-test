package com.gilson.dojotest.presenter;

import com.gilson.dojotest.util.DateUtil;
import com.gilson.dojotest.view.MainView;
import com.gilson.dojotest.ws.RestApi;
import com.gilson.dojotest.ws.dto.MatchDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observer;
import rx.functions.Func1;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
public class MainPresenter {
    private MainView view;
    private RestApi api;

    public MainPresenter(MainView view, RestApi api) {
        this.view = view;
        this.api = api;
        this.loadData();
    }

    private void loadData() {
        view.showLoading();
        api.queryMatchs(-1)
                .map(addTimeDivisions())
                .subscribe(new MatchObserver());
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
                     * I'm not sure how the date groupings comes from the server so
                     * i created the divisions and changed the date a bit
                     * so we would have at least two scenarios for now
                     */
                    if (match.createDate.before(first)) {
                        MatchDto division = new MatchDto();
                        division.dateOnly = true;
                        division.dateLabel = getSeparatorLabel(first, match);
                        newList.add(division);
                    }
                    newList.add(match);
                }

                return newList;
            }
        };
    }

    private String getSeparatorLabel(Date first, MatchDto match) {
        String dateLabel;
        if (DateUtil.getDayDateDifference(first, match.createDate) == 1) {
            dateLabel = "YESTERDAY";
        } else {
            dateLabel = DateUtil.getDateMonthYear(match.createDate)
                    .toUpperCase();
        }
        return dateLabel;
    }

    private class MatchObserver implements Observer<List<MatchDto>> {
        @Override
        public void onCompleted() {
            view.hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            view.showError(e.toString());
            view.hideLoading();
        }

        @Override
        public void onNext(List<MatchDto> matchDtos) {
            view.renderMatches(matchDtos);
        }
    }
}
