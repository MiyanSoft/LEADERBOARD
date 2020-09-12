package com.miyansoft.leaderboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HourRecyclerAdapter extends RecyclerView.Adapter<HourRecyclerAdapter.HourViewHolder> {
    private ArrayList<LearnersModel> hourLearners = new ArrayList<>();
    private Context context;

    public HourRecyclerAdapter (ArrayList<LearnersModel> hourLearners, Context context) {
        this.context = context;
        this.hourLearners = hourLearners;
    }
    @NonNull
    @Override
    public HourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View hourView = layoutInflater.inflate(R.layout.learners_hour_list, parent, false);
        return new HourRecyclerAdapter.HourViewHolder(hourView);
    }

    @Override
    public void onBindViewHolder(@NonNull HourViewHolder holder, int position) {
        Picasso.get().load(hourLearners.get(position).getBadgeUrl()).into(holder.imageHour);
        holder.name.setText(hourLearners.get(position).getName());
        holder.hour.setText(hourLearners.get(position).getHours() + " learning hours, ");
        holder.country.setText(hourLearners.get(position).getCountry() + ".");

    }

    @Override
    public int getItemCount() {
        return hourLearners.size();
    }

    public class HourViewHolder extends RecyclerView.ViewHolder {
        ImageView imageHour;
        TextView name;
        TextView hour;
        TextView country;

        public HourViewHolder(@NonNull View itemView) {
            super(itemView);
            imageHour = itemView.findViewById(R.id.imgHour);
            name = itemView.findViewById(R.id.hour_learner_name);
            hour = itemView.findViewById(R.id.learner_hour);
            country = itemView.findViewById(R.id.learner_country);
        }
    }
}
