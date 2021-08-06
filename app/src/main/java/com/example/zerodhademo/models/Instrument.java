package com.example.zerodhademo.models;

import java.util.Map;
import java.util.Objects;
import androidx.annotation.NonNull;

public class Instrument {
    private final String exchangeAndTradingSymbol;
    private final String instrumentToken;
    private final String lastPrice;

    public Instrument(@NonNull String exchangeAndTradingSymbol, @NonNull String instrumentToken, @NonNull String lastPrice){
        this.exchangeAndTradingSymbol = exchangeAndTradingSymbol;
        this.instrumentToken = instrumentToken;
        this.lastPrice = lastPrice;
    }

    public String getExchangeAndTradingSymbol(){
        return exchangeAndTradingSymbol;
    }

    public String getInstrumentToken(){
        return instrumentToken;
    }

    public String getLastPrice(){
        return lastPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instrument that = (Instrument) o;
        return Objects.equals(exchangeAndTradingSymbol, that.exchangeAndTradingSymbol) &&
                Objects.equals(instrumentToken, that.instrumentToken) &&
                Objects.equals(lastPrice, that.lastPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exchangeAndTradingSymbol, instrumentToken, lastPrice);
    }
}
