package com.example.zerodhademo.screens;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.angelbroking.smartapi.SmartConnect;
import com.angelbroking.smartapi.models.User;
import com.example.zerodhademo.R;
import com.example.zerodhademo.adapters.InstrumentsListAdapter;
import com.example.zerodhademo.models.Instrument;
import com.example.zerodhademo.viewmodels.MainActivityViewModel;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private final SmartConnect smartConnect = new SmartConnect("eDpmeQpP");

    private MainActivityViewModel viewModel;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        final String clientId = "";
        final String password = "";

        CompletableFuture<User> future = CompletableFuture.supplyAsync(() -> smartConnect.generateSession(clientId,password));

        User user = null;
        try {
            user = future.get();
            if(user != null){
                User finalUser = user;
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        viewModel.connectTicker(clientId, finalUser.feedToken, "nse_cm|2885&nse_cm|1594&nse_cm|11536", "mw");
                    }
                });
                thread.start();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        RecyclerView instrumentsList = findViewById(R.id.instruments_list);
        instrumentsList.setLayoutManager(new LinearLayoutManager(this));

        InstrumentsListAdapter adapter = new InstrumentsListAdapter();

        viewModel.instrumentMap.observe(this, instruments -> {
            adapter.submitList(new ArrayList<>(instruments.values()));
        });
        instrumentsList.setAdapter(adapter);
    }
}