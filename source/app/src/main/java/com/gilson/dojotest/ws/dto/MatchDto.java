package com.gilson.dojotest.ws.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
public class MatchDto {
    public boolean win;
    @SerializedName("game_id")
    public Long id;
    public String champion;
    public String lane;
    public String gameMode;
    public String totalPerformance;
    public Date createDate;
    public boolean dateOnly;
    public String dateLabel;

}
