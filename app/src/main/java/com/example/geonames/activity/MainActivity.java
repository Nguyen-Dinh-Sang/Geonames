package com.example.geonames.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.example.geonames.R;
import com.example.geonames.adapter.CountryAdapter;
import com.example.geonames.model.Geoname;
import com.example.geonames.service.JsonTaskFull;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CountryAdapter countryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        initData();


    }

    private void initData() {
        new JsonTaskFull().jsonReader("http://api.geonames.org/countryInfoJSON?formatted=true&lang=it&username=sangdeptrai&style=full",
                new JsonTaskFull.JsonTaskFullListener() {
                    @Override
                    public void onLoadSuccess(ArrayList<Geoname> geonameArrayList) {
                        countryAdapter = new CountryAdapter(MainActivity.this, geonameArrayList);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(countryAdapter);
                    }

                    @Override
                    public void onLoadFail(String error) {

                    }
                });
    }

    private void initEvent() {

    }

    private void initView() {
        recyclerView = findViewById(R.id.rv_list_country);
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
}
