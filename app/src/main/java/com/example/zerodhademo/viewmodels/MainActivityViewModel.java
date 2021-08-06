package com.example.zerodhademo.viewmodels;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.angelbroking.smartapi.SmartConnect;
import com.angelbroking.smartapi.http.SessionExpiryHook;
import com.angelbroking.smartapi.http.exceptions.SmartAPIException;
import com.angelbroking.smartapi.models.TokenSet;
import com.angelbroking.smartapi.models.User;
import com.angelbroking.smartapi.smartTicker.SmartWSOnConnect;
import com.angelbroking.smartapi.smartTicker.SmartWSOnDisconnect;
import com.angelbroking.smartapi.smartTicker.SmartWSOnError;
import com.angelbroking.smartapi.smartTicker.SmartWSOnTicks;
import com.angelbroking.smartapi.smartTicker.SmartWebsocket;
import com.angelbroking.smartapi.ticker.OnConnect;
import com.angelbroking.smartapi.ticker.OnTicks;
import com.angelbroking.smartapi.ticker.SmartAPITicker;
import com.example.zerodhademo.models.Instrument;
import com.google.gson.JsonObject;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivityViewModel extends ViewModel {
    public MutableLiveData<Map<String,Instrument>> instrumentMap = new MutableLiveData<>();
    public Map<String, String> tradingSymbols = new HashMap<>();

    public MainActivityViewModel(){
        tradingSymbols.put("2885","RELIANCE-EQ");
        tradingSymbols.put("1594","INFY-EQ");
        tradingSymbols.put("11536", "TCS-EQ");
    }

    public void connectTicker(String clientId, String feedToken, String strWatchListScript, String task){

        SmartAPITicker tickerProvider = new SmartAPITicker(clientId, feedToken, strWatchListScript, task);

        tickerProvider.setOnConnectedListener(new OnConnect() {
            @Override
            public void onConnected() {
                Log.d("connected","subscribe() called!");
                tickerProvider.subscribe();
            }
        });

        tickerProvider.setOnTickerArrivalListener(new OnTicks() {
            @Override
            public void onTicks(JSONArray ticks) {
                if(ticks.length() > 0){
                    Map<String, Instrument> newMap = instrumentMap.getValue();

                    if(newMap == null){
                        newMap = new HashMap<>();
                    }

                    for (int i = 0; i < ticks.length(); i++) {

                        try {
                            JSONObject jsonObject = ticks.getJSONObject(i);

                            if(jsonObject.getString("name").equals("sf")){
                                newMap.put(jsonObject.getString("tk") ,new Instrument(Objects.requireNonNull(tradingSymbols.get(jsonObject.getString("tk"))), jsonObject.getString("tk"), jsonObject.getString("ltp")));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    instrumentMap.postValue(newMap);
                }
            }
        });

        tickerProvider.connect();
    }
}