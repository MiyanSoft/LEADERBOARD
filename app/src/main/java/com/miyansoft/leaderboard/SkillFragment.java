package com.miyansoft.leaderboard;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SkillFragment extends Fragment {

     private RecyclerView recyclerSkills;
     private SkillRecyclerAdapter skillRecyclerAdapter;
     private ArrayList<LearnersModel> skillLearners = new ArrayList<>();



    public SkillFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_skill, container, false);

         recyclerSkills = view.findViewById(R.id.list_skills);
         RecyclerView.LayoutManager skillsLayoutManager = new LinearLayoutManager(getContext());
         recyclerSkills.setLayoutManager(skillsLayoutManager);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gadsapi.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GadsApi gadsApi = retrofit.create(GadsApi.class);

        Call<List<LearnersModel>> skillIQ = gadsApi.getLearnerSkills("/api/skilliq");

        skillIQ.enqueue(new Callback<List<LearnersModel>>() {
            @Override
            public void onResponse(Call<List<LearnersModel>> call, Response<List<LearnersModel>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    skillLearners = new ArrayList<>(response.body());
                    skillRecyclerAdapter = new SkillRecyclerAdapter(skillLearners, getContext());
                    recyclerSkills.setAdapter(skillRecyclerAdapter);
                }
                skillRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<LearnersModel>> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });



        return view;


    }




}