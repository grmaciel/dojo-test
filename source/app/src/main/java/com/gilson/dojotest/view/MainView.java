package com.gilson.dojotest.view;

import com.gilson.dojotest.ws.dto.MatchDto;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
public interface MainView extends LoadDataView {
    void renderCardsResume(MatchDto dto);
}
