package me.gunna.exemploteste.androidapp.service.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by root on 05/08/17.
 */

public class BtcHistoryResponse {
    @SerializedName("bpi")
    private JsonObject mHistory;
    @SerializedName("time")
    private Time mTime;


    public JsonObject getmHistory() {
        return mHistory;
    }

    public void setmHistory(JsonObject mHistory) {
        this.mHistory = mHistory;
    }

    public Time getmTime() {
        return mTime;
    }

    public void setmTime(Time mTime) {
        this.mTime = mTime;
    }

    public float[] getValues(int quant) {
        float[] values = new float[quant];
        float[] v = new float[mHistory.entrySet().size()];

        int x = -1;
        for (Map.Entry<String, JsonElement> i : mHistory.entrySet()) {
            v[++x] = Float.parseFloat(i.getValue().getAsString());
        }
        int j = 0;
        for(int i = quant  ; i >0; i--, j++)
            if (j < quant)
                values[j] = v[i];
        return values;
    }

    public String[] getDates(int quant) {
        String[] d = new String[mHistory.entrySet().size()];
        String[] dates = new String[quant];
        int x = -1;
        for (Map.Entry<String, JsonElement> i : mHistory.entrySet()) {
            d[++x] = i.getKey().split("-")[2];
        }
        int j = 0;
        for(int i = quant  ; i >0; i--, j++)
            if (j < quant)
                dates[j] = d[i];

        return dates;
    }

    public static class Time {
        @SerializedName("updated")
        private String mLastUpdate;

        public String getmLastUpdate() {
            return mLastUpdate;
        }

        public void setmLastUpdate(String mLastUpdate) {
            this.mLastUpdate = mLastUpdate;
        }

        public String getmLastUpdateIso() {
            return mLastUpdateIso;
        }

        public void setmLastUpdateIso(String mLastUpdateIso) {
            this.mLastUpdateIso = mLastUpdateIso;
        }

        @SerializedName("updatedISO")
        private String mLastUpdateIso;


    }
}
