package com.gilson.dojotest.ws.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
public class MatchHistoryDto {
    @SerializedName("match_history")
    public List<MatchDto> matchHistory;
}
