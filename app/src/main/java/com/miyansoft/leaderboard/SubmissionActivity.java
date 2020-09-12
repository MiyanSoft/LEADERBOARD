package com.miyansoft.leaderboard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmissionActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText firstName, lastName, emailAddress, gitHubLink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submission_form_toolbar);

        ImageView imageView = findViewById(R.id.img_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubmissionActivity.this, MainActivity.class));
            }
        });

        firstName = findViewById(R.id.txt_first_name);
        lastName = findViewById(R.id.txt_last_name);
        emailAddress = findViewById(R.id.txt_email_address);
        gitHubLink = findViewById(R.id.txt_github_link);

        findViewById(R.id.btn_submit_form).setOnClickListener(this);
    }
  private void confirmSubmission() {
      Dialog myDialogue = new Dialog(SubmissionActivity.this);
      myDialogue.setContentView(R.layout.confirmation_dialogue);
      myDialogue.requestWindowFeature(Window.FEATURE_NO_TITLE);
      ImageButton cancel = findViewById(R.id.cancel_dialogue);
      Button yesButton = findViewById(R.id.btn_confirmation);
      yesButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              uploadData();
              displaySubmissionSucceed();
          }
      });
      cancel.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              myDialogue.dismiss();
              displaySubmissionFailed();
          }
      });
        myDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialogue.show();
  }

    private void uploadData() {
        String fName = firstName.getText().toString().trim();
        String lName = lastName.getText().toString().trim();
        String email = emailAddress.getText().toString().trim();
        String gitLink = lastName.getText().toString().trim();

        Call<LearnersModel> call = RetrofitClient
                .getInstance()
                .getGadsApi()
                .submitProject(email, fName, lName, gitLink);
        call.enqueue(new Callback<LearnersModel>() {
            @Override
            public void onResponse(Call<LearnersModel> call, Response<LearnersModel> response) {
                Toast.makeText(SubmissionActivity.this, "success", Toast.LENGTH_SHORT).show();
                // if(response.isSuccessful() && response.body()!=null)    { }
            }

            @Override
            public void onFailure(Call<LearnersModel> call, Throwable t) {
                Toast.makeText(SubmissionActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displaySubmissionFailed() {
        Dialog myDialogue = new Dialog(SubmissionActivity.this);
        myDialogue.setContentView(R.layout.submission_failed);
        myDialogue.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialogue.show();
    }

    private void displaySubmissionSucceed() {
        Dialog myDialogue = new Dialog(SubmissionActivity.this);
        myDialogue.setContentView(R.layout.submission_success_dialogue);
        myDialogue.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialogue.show();
    }

    private void validateData(){
        String fName = firstName.getText().toString().trim();
        String lName = lastName.getText().toString().trim();
        String email = emailAddress.getText().toString().trim();
        String gitLink = lastName.getText().toString().trim();

        if(fName.isEmpty()) {
            firstName.setError("Required field!");
            firstName.requestFocus();
            return;
    }
        if(lName.isEmpty()) {
            lastName.setError("Required field!");
            lastName.requestFocus();
            return;
    }

        if(email.isEmpty()) {
            emailAddress.setError("Required field!");
            emailAddress.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailAddress.setError("Please, enter a valid email");
            emailAddress.requestFocus();
            return;
        }

        if(gitLink.isEmpty()) {
            gitHubLink.setError("Required Field!");
            gitHubLink.requestFocus();
            return;
        }

        confirmSubmission();
}

    @Override
    public void onClick(View v) {
        switch (v.getId())  {
            case R.id.btn_submit_form:
                validateData();
                break;
        }
    }

}
