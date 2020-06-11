package com.example.geonames.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geonames.R;
import com.example.geonames.activity.InforActivity;
import com.example.geonames.model.Geoname;

import java.util.ArrayList;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> implements Filterable {
    private Context context;
    private ArrayList<Geoname> geonameArrayList;
    private ArrayList<Geoname> geonameArrayListFull;

    public CountryAdapter(Context context, ArrayList<Geoname> geonameArrayList) {
        this.context = context;
        this.geonameArrayList = geonameArrayList;
        geonameArrayListFull = new ArrayList<>();
        geonameArrayListFull.addAll(geonameArrayList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_country, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Geoname geoname = geonameArrayList.get(position);
        holder.textViewCountyName.setText(geoname.getCountryName());
    }

    @Override
    public int getItemCount() {
        return geonameArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCountyName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewCountyName = itemView.findViewById(R.id.tv_country_name);

            textViewCountyName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, InforActivity.class);
                    intent.putExtra("country", geonameArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Geoname> filterList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(geonameArrayListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Geoname item : geonameArrayListFull) {
                    if (item.getCountryName().toLowerCase().contains(filterPattern) || item.getCountryCode().toLowerCase().contains(filterPattern)) {
                        filterList.add(item);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            geonameArrayList.clear();
            geonameArrayList.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
}
