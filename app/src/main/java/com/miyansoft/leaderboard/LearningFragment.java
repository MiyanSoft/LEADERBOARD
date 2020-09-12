package com.miyansoft.leaderboard;
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


public class LearningFragment extends Fragment {
    private RecyclerView recyclerHours;
    private HourRecyclerAdapter hourRecyclerAdapter;
    private ArrayList<LearnersModel> hourLearners = new ArrayList<>();




    public LearningFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_learning, container, false);

        recyclerHours = view.findViewById(R.id.list_hours);
        RecyclerView.LayoutManager hoursLayoutManager = new LinearLayoutManager(getContext());
        recyclerHours.setLayoutManager(hoursLayoutManager);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gadsapi.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GadsApi gadsApi = retrofit.create(GadsApi.class);

        Call<List<LearnersModel>> LearningHours = gadsApi.getLearnerHours("/api/hours");

        LearningHours.enqueue(new Callback<List<LearnersModel>>() {
            @Override
            public void onResponse(Call<List<LearnersModel>> call, Response<List<LearnersModel>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    hourLearners = new ArrayList<>(response.body());
                    hourRecyclerAdapter = new HourRecyclerAdapter(hourLearners, getContext());
                    recyclerHours.setAdapter(hourRecyclerAdapter);
                }
                hourRecyclerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<LearnersModel>> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });


        return view;

    }


}