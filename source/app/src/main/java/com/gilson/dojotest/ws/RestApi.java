package com.gilson.dojotest.ws;

import com.gilson.dojotest.ws.dto.MatchDetailDto;
import com.gilson.dojotest.ws.dto.MatchDto;

import java.util.List;

import rx.Observable;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
public interface RestApi {
    Observable<List<MatchDto>> queryMatches(long summonerId);

    Observable<List<MatchDetailDto>> queryMatchDetails(long summonerId);

    Observable<MatchDetailDto> queryMatchDetail(long matchId);
}
