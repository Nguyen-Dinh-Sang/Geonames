package com.example.geonames.service;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.example.geonames.model.Geoname;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class JsonTaskFull {
    private JsonTask jsonTask;

    public void jsonReader(String url, JsonTaskFullListener jsonTaskFullListener) {
        jsonTask = new JsonTask(url, jsonTaskFullListener);
        jsonTask.execute();
    }


    public class JsonTask extends AsyncTask<String, String, String> {

        private String urlLink;
        private ArrayList<Geoname> geonameArrayList;
        private JsonTaskFullListener jsonTaskFullListener;
        public JsonTask(String urlLink, JsonTaskFullListener jsonTaskFullListener){
            this.urlLink=urlLink;
            this.jsonTaskFullListener = jsonTaskFullListener;
        }

        @Override
        protected String doInBackground(String... params) {
            if (TextUtils.isEmpty(urlLink)) {
                return "";
            }

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(urlLink);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);
                }
                geonameArrayList = jsonToObj(buffer.toString());
                return "";
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("")) {
                jsonTaskFullListener.onLoadSuccess(geonameArrayList);
            } else {
                jsonTaskFullListener.onLoadFail(result);
            }
        }
    }

    public interface JsonTaskFullListener{
        void onLoadSuccess(ArrayList<Geoname> geonameArrayList);
        void onLoadFail(String error);
    }

    private ArrayList<Geoname> jsonToObj(String json){
        ArrayList<Geoname> geonameArrayList = new ArrayList<>();
        String countryName = "";
        String population = "";
        String areaInSqKm = "";
        String countryCode = "";
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArraylist = jsonObject.getJSONArray("geonames");
            for (int i=0; i<jsonArraylist.length(); i++){
                JSONObject jsonObjectCountry = jsonArraylist.getJSONObject(i);
                countryName = jsonObjectCountry.getString("countryName");
                population = jsonObjectCountry.getString("population");
                areaInSqKm = jsonObjectCountry.getString("areaInSqKm");
                countryCode = jsonObjectCountry.getString("countryCode");
                geonameArrayList.add(new Geoname(population, areaInSqKm, countryCode, countryName));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return geonameArrayList;
    }
}
