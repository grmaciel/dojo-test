package com.gilson.dojotest.ws;

import android.support.annotation.NonNull;

import com.gilson.dojotest.ws.dto.MatchDetailDto;
import com.gilson.dojotest.ws.dto.MatchDto;
import com.gilson.dojotest.ws.dto.MatchHistoryDto;
import com.gilson.dojotest.ws.dto.PerformanceDto;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Gilson Maciel on 16/09/2015.
 */
public class RestApiFakeImpl implements RestApi {
    private Observable<List<MatchDto>> queryMatchsObservable;
    private Observable<List<MatchDetailDto>> queryMatchsDetailObservable;

    public RestApiFakeImpl() {
        this.queryMatchsObservable = getQueryMatchsObservable();
        this.queryMatchsDetailObservable = getQueryMatchsDetailObservable();
    }

    private Observable<List<MatchDto>> getQueryMatchsObservable() {
        return Observable.create(new Observable.OnSubscribe<List<MatchDto>>() {
            @Override
            public void call(Subscriber<? super List<MatchDto>> subscriber) {
                try {
                    /**
                     * WS Query
                     */
                    Thread.sleep(2000);
                    GsonBuilder builder = new GsonBuilder();
                    builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                        @Override
                        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                            return new Date(json.getAsJsonPrimitive().getAsLong());
                        }
                    });

                    MatchHistoryDto history = builder.create().fromJson(getJsonMatches(), MatchHistoryDto.class);
                    subscriber.onNext(history.matchHistory);
                } catch (InterruptedException e) {
                    subscriber.onError(e);
                    e.printStackTrace();
                }

                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(createLocalCache())
                .cache();
    }

    private Action1<? super List<MatchDto>> createLocalCache() {
        return new Action1<List<MatchDto>>() {
            @Override
            public void call(List<MatchDto> matchDtos) {
                /**
                 * I could store this data locally in the database
                 * to create some sort of offline cache to user
                 * or a temporary cache
                 */
            }
        };
    }

    @Override
    public Observable<List<MatchDto>> queryMatchs(long summonerId) {
        return queryMatchsObservable;
    }

    @Override
    public Observable<MatchDto> queryMatch(final long matchId) {
        return this.queryMatchsObservable
                .flatMap(getMatchById(matchId));
    }

    @NonNull
    private Func1<List<MatchDto>, Observable<MatchDto>> getMatchById(final long matchId) {
        return new Func1<List<MatchDto>, Observable<MatchDto>>() {
            @Override
            public Observable<MatchDto> call(List<MatchDto> matchDetailDtos) {
                for (MatchDto match : matchDetailDtos) {
                    if (match.id == matchId) {
                        return Observable.just(match);
                    }
                }
                return Observable.empty();
            }
        };
    }

    @Override
    public Observable<List<MatchDetailDto>> queryMatchDetails(long summonerId) {
        return queryMatchsDetailObservable;
    }

    private Observable<List<MatchDetailDto>> getQueryMatchsDetailObservable() {
        return Observable.create(new Observable.OnSubscribe<List<MatchDetailDto>>() {
            @Override
            public void call(Subscriber<? super List<MatchDetailDto>> subscriber) {
                try {
                    /**
                     * WS Query
                     */
                    Thread.sleep(1000);

                    JSONObject obj = new JSONObject(getJsonDetail());

                    if (obj.has("match_stats")) {
                        JSONArray array = obj.getJSONArray("match_stats");

                        subscriber.onNext(extractMatchDetail(array));
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .cache();
    }

    private List<MatchDetailDto> extractMatchDetail(JSONArray array) throws JSONException {
        List<MatchDetailDto> details = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            JSONObject stats = array.getJSONObject(i);

            MatchDetailDto detail = new MatchDetailDto();
            detail.idGame = stats.getLong("game_id");
            detail.performance = new ArrayList<>();

            JSONArray performances = stats
                    .getJSONArray("performanceBreakedown");

            for (int j = 0; j < performances.length(); j++) {
                detail.performance.add(getDetailDto(performances.getJSONObject(j)));
            }

            details.add(detail);
        }

        return details;
    }

    private PerformanceDto getDetailDto(JSONObject performance) throws JSONException {
        PerformanceDto perfDto = new PerformanceDto();
        perfDto.performance = (String) performance.remove("performance");
        JSONArray names = performance.names();

        for (int i = 0; i < names.length(); i++) {
            String name = names.getString(i);
            perfDto.label = name;
            perfDto.value = performance.getString(name);
        }

        return perfDto;
    }

    /**
     * Not sure if i can call the rest api with a specific match id
     * or if i would just call every detail and the cache it locally, nonetheless i assumed
     * i could query all of them and the detail only aswell
     *
     * @param matchId
     * @return
     */
    @Override
    public Observable<MatchDetailDto> queryMatchDetail(final long matchId) {
        return this.queryMatchsDetailObservable
                .flatMap(new Func1<List<MatchDetailDto>, Observable<MatchDetailDto>>() {
                    @Override
                    public Observable<MatchDetailDto> call(List<MatchDetailDto> matchDetailDtos) {
                        for (MatchDetailDto detail : matchDetailDtos) {
                            if (detail.idGame == matchId) {
                                return Observable.just(detail);
                            }
                        }
                        return Observable.empty();
                    }
                });
    }

    private String getJsonDetail() {
        return "{\n" +
                "    \"summoner_id\":35547295,\n" +
                "    \"match_stats\":[\n" +
                "            {\"game_id\":2296261271,\n" +
                "            \"performanceBreakedown\":[\n" +
                "                {\n" +
                "                    \"CS\":130,\n" +
                "                    \"performance\":\"platinum\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"Wards\":10,\n" +
                "                    \"performance\":\"gold\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"KDARatio\":130,\n" +
                "                    \"performance\":\"platinum\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"DMGtoChampions\":13000,\n" +
                "                    \"performance\":\"platinum\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"GPM\":500,\n" +
                "                    \"performance\":\"gold\"\n" +
                "                }\n" +
                "            ]},\n" +
                "            {\"game_id\":2295841949,\n" +
                "            \"performanceBreakedown\":[\n" +
                "                {\n" +
                "                    \"CS\":130,\n" +
                "                    \"performance\":\"platinum\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"Wards\":12,\n" +
                "                    \"performance\":\"platinum\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"KDARatio\":130,\n" +
                "                    \"performance\":\"platinum\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"DMGtoChampions\":13500,\n" +
                "                    \"performance\":\"platinum\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"GPM\":600,\n" +
                "                    \"performance\":\"platinum\"\n" +
                "                }\n" +
                "            ]}, {\n" +
                "            \"game_id\":2294240279,\n" +
                "            \"performanceBreakedown\":[\n" +
                "                {\n" +
                "                    \"CS\":90,\n" +
                "                    \"performance\":\"silver\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"Wards\":12,\n" +
                "                    \"performance\":\"platinum\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"KDARatio\":130,\n" +
                "                    \"performance\":\"platinum\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"DMGtoChampions\":13500,\n" +
                "                    \"performance\":\"platinum\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"GPM\":700,\n" +
                "                    \"performance\":\"master\"\n" +
                "                }\n" +
                "            ]}\n" +
                "    ]\n" +
                "}\n";
    }

    private String getJsonMatches() {
        return "{\n" +
                "    \"match_history\":[\n" +
                "        {\n" +
                "            \"summoner_id\":35547295,\n" +
                "            \"game_id\":2296261271,\n" +
                "            \"region\":\"euw\",\n" +
                "            \"lane\":\"middle\",\n" +
                "            \"gameMode\":\"Normal\",\n" +
                "            \"gametype\":\"Solo\",\n" +
                "            \"createDate\":1442164185080,\n" +
                "            \"win\":true,\n" +
                "            \"champion\":\"Alistar\",\n" +
                "            \"totalPerformance\":\"platinum\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"summoner_id\":35547295,\n" +
                "            \"game_id\":2295841949,\n" +
                "            \"region\":\"euw\",\n" +
                "            \"lane\":\"top\",\n" +
                "            \"gameMode\":\"Ranked\",\n" +
                "            \"gametype\":\"Solo\",\n" +
                "            \"createDate\":1442098800000,\n" +
                "            \"win\":false,\n" +
                "            \"champion\":\"Vayne\",\n" +
                "            \"totalPerformance\":\"challenger\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"summoner_id\":35547295,\n" +
                "            \"game_id\":2294240279,\n" +
                "            \"region\":\"euw\",\n" +
                "            \"lane\":\"middle\",\n" +
                "            \"gameMode\":\"Ranked\",\n" +
                "            \"gametype\":\"Team\",\n" +
                "            \"createDate\":1439337600000,\n" +
                "            \"win\":false,\n" +
                "            \"champion\":\"Janna\",\n" +
                "            \"totalPerformance\":\"diamond\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    }
}
