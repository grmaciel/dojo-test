package com.gilson.dojotest.ws.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
public class MatchDetailDto {
    @SerializedName("game_id")
    public long idGame;
    @SerializedName("performanceBreakedown")
    public List<PerformanceDto> performance;
}
