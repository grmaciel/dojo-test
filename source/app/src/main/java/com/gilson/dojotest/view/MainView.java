package com.gilson.dojotest.view;

import com.gilson.dojotest.ws.dto.MatchDto;

import java.util.List;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
public interface MainView extends LoadDataView {
    void renderMatches(List<MatchDto> matches);
}
