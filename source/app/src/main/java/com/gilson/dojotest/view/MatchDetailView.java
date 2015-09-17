package com.gilson.dojotest.view;

import com.gilson.dojotest.ws.dto.MatchDetailDto;

import java.util.List;

/**
 * Created by Gilson Maciel on 17/09/2015.
 */
public interface MatchDetailView extends LoadDataView {
    void renderBadgeIcon(int resId);
    void renderMatchDetail(List<MatchDetailDto> data);
    long getMatchId();
}
