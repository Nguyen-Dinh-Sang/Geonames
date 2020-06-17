package com.example.geonames.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.example.geonames.R;
import com.example.geonames.adapter.CountryAdapter;
import com.example.geonames.model.Geoname;
import com.example.geonames.service.JsonTaskFull;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CountryAdapter countryAdapter;
    private CardView cardViewReload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        internet();
    }

    private void initData() {
        final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "Loading data",
                "Loading. Please wait...", true);
        dialog.setCancelable(false);
        dialog.show();
        new JsonTaskFull().jsonReader("http://api.geonames.org/countryInfoJSON?formatted=true&lang=it&username=sangdeptrai&style=full",
                new JsonTaskFull.JsonTaskFullListener() {
                    @Override
                    public void onLoadSuccess(ArrayList<Geoname> geonameArrayList) {
                        cardViewReload.setVisibility(View.GONE);
                        dialog.cancel();
                        countryAdapter = new CountryAdapter(MainActivity.this, geonameArrayList);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(countryAdapter);
                    }

                    @Override
                    public void onLoadFail(String error) {
                        dialog.cancel();
                        internet();
                    }
                });
    }

    private void initEvent() {
        cardViewReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                internet();
            }
        });
    }

    private void initView() {
        recyclerView = findViewById(R.id.rv_list_country);
        cardViewReload = findViewById(R.id.cv_reload);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_view, menu);

        MenuItem searchItem = menu.findItem(R.id.it_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                countryAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                countryAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    private void internet() {
        if (isOnline()) {
            initData();
        } else {
            android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(this);
            builder1.setTitle("Info");
            builder1.setMessage("Internet not available, Cross check your internet connectivity and try again.");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            cardViewReload.setVisibility(View.VISIBLE);
                        }
                    });
            android.app.AlertDialog alert11 = builder1.create();
            alert11.setCancelable(false);
            alert11.show();
        }
    }

    private boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(this, "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        } else {
            Toast.makeText(this, "Internet connection!", Toast.LENGTH_LONG).show();
            return true;
        }
    }
}
