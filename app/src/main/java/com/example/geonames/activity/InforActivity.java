package com.example.geonames.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.geonames.R;
import com.example.geonames.model.Geoname;

public class InforActivity extends AppCompatActivity {
    private TextView textViewCountryName, textViewName, textViewAreaInSqKm, textViewPopulation;
    private ImageView imageViewCo, imageViewBack;
    private Geoname geoname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor);
        initView();
        initEvent();
        initData();
    }

    private void initData() {
        getData();
        Glide.with(this).load("https://img.geonames.org/flags/x/" + geoname.getCountryCode().toLowerCase() + ".gif").into(imageViewCo);
        textViewCountryName.setText(geoname.getCountryName());
        textViewName.setText(geoname.getCountryName());
        textViewPopulation.setText(geoname.getPopulation());
        textViewAreaInSqKm.setText(geoname.getAreaInSqKm() + " SqKm");
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("country")) {
                geoname = (Geoname) intent.getSerializableExtra("country");
            }
        }
    }

    private void initEvent() {
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initView() {
        textViewCountryName = findViewById(R.id.tv_title_toolbar);
        textViewName = findViewById(R.id.tv_name);
        textViewPopulation = findViewById(R.id.tv_population);
        textViewAreaInSqKm = findViewById(R.id.tv_areaInSqKm);
        imageViewCo = findViewById(R.id.iv_co);
        imageViewBack = findViewById(R.id.iv_back);
    }
}
