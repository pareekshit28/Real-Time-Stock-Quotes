package com.example.zerodhademo.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zerodhademo.R;
import com.example.zerodhademo.models.Instrument;

public class InstrumentsListAdapter extends ListAdapter<Instrument, InstrumentsListAdapter.ViewHolder> {

    public InstrumentsListAdapter(){
        super(InstrumentsListAdapter.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.instruments_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tradingSymbol;
        private final TextView ltp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tradingSymbol= itemView.findViewById(R.id.tradingSymbol);
            this.ltp= itemView.findViewById(R.id.ltp);
        }

        public void bind(Instrument item){
            tradingSymbol.setText(item.getExchangeAndTradingSymbol());
            ltp.setText(item.getLastPrice());
        }
    }

    public static final DiffUtil.ItemCallback<Instrument> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Instrument>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Instrument oldInstrument, @NonNull Instrument newInstrument) {

                    return oldInstrument.equals(newInstrument);
                }

                @Override
                public boolean areContentsTheSame(
                        @NonNull Instrument oldInstrument, @NonNull Instrument newInstrument) {

                    return oldInstrument.getExchangeAndTradingSymbol().equals(newInstrument.getExchangeAndTradingSymbol());
                }
            };
}
