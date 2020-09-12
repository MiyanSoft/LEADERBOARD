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


public class SkillRecyclerAdapter extends RecyclerView.Adapter<SkillRecyclerAdapter.SkillViewHolder> {
    private ArrayList<LearnersModel> skillLearners = new ArrayList<>();
    private Context context;


    public SkillRecyclerAdapter (ArrayList<LearnersModel> skillLearners, Context context)   {
        this.context = context;
        this.skillLearners = skillLearners;
    }
    @NonNull
    @Override
    public SkillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View skillView = layoutInflater.inflate(R.layout.learners_skill_list, parent, false);
        return new SkillViewHolder(skillView);


    }

    @Override
    public void onBindViewHolder(@NonNull SkillViewHolder holder, int position) {
        Picasso.get().load(skillLearners.get(position).getBadgeUrl()).into(holder.imageSkill);
        holder.name.setText(skillLearners.get(position).getName());
        holder.score.setText(skillLearners.get(position).getScore() + " Skill IQ Score, ");
        holder.country.setText(skillLearners.get(position).getCountry());

    }

    @Override
    public int getItemCount() {
        return skillLearners.size();
    }

    public class SkillViewHolder extends RecyclerView.ViewHolder{
            ImageView imageSkill;
            TextView name;
            TextView score;
            TextView country;
        public SkillViewHolder(@NonNull View itemView) {
            super(itemView);
            imageSkill = itemView.findViewById(R.id.imgSkill);
            name = itemView.findViewById(R.id.skill_learner_name);
            score = itemView.findViewById(R.id.learner_score);
            country = itemView.findViewById(R.id.learner_country);
        }

    }
}
