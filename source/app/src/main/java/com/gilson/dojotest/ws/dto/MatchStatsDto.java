package com.gilson.dojotest.ws.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Gilson Maciel on 17/09/2015.
 */
public class MatchStatsDto {
    @SerializedName("match_stats")
    public List<MatchDetailDto> details;
}
