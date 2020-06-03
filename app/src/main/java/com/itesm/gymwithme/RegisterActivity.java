package com.itesm.gymwithme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements Callback<User> {

    private Button registerButton;
    private Button cancelButton;

    private EditText nameText;
    private EditText ageText;
    private EditText genderText;
    private EditText usernameText;
    private EditText passwordText;
    private EditText beenWorkingOutText;

    private ProgressBar progressBar;

    private final Callback<User> loginCallback = new Callback<User>() {
        @Override
        public void onResponse(Call<User> call, Response<User> response) {

            if (!response.isSuccessful()) {
                if (response.code() == 400) {
                    Toast.makeText(
                            RegisterActivity.this,
                            "invalid username or password",
                            Toast.LENGTH_LONG)
                            .show();
                } else {
                    Toast.makeText(
                            RegisterActivity.this,
                            "Response error: " + response.message(),
                            Toast.LENGTH_LONG)
                            .show();
                }
                progressBar.setVisibility(View.GONE);
                return;
            }

            String token = response.headers().get("auth-token");
            Intent firstIntent = new Intent(RegisterActivity.this, MainActivity.class);
            firstIntent.putExtra("auth-token", token);
            startActivity(firstIntent);
            finish();

        }

        @Override
        public void onFailure(Call<User> call, Throwable t) {

            Toast.makeText(
                    RegisterActivity.this,
                    "Failure " + t.getMessage(),
                    Toast.LENGTH_SHORT)
                    .show();
            progressBar.setVisibility(View.GONE);

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = findViewById(R.id.button_register);
        cancelButton = findViewById(R.id.button_cancel);

        nameText = ((TextInputLayout) findViewById(R.id.name_text)).getEditText();
        ageText = ((TextInputLayout) findViewById(R.id.age_text)).getEditText();
        genderText = ((TextInputLayout) findViewById(R.id.gender_text)).getEditText();
        usernameText = ((TextInputLayout) findViewById(R.id.username_text)).getEditText();
        passwordText = ((TextInputLayout) findViewById(R.id.password_text)).getEditText();
        beenWorkingOutText = ((TextInputLayout) findViewById(R.id.working_out_text)).getEditText();

        progressBar = findViewById(R.id.progressBar);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerButton.setEnabled(false);
                cancelButton.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                registerUser();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.super.onBackPressed();
            }
        });

        String[] genders = new String[] {"Male", "Female"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, genders);
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.gender_text_edit);
        autoCompleteTextView.setAdapter(adapter);

    }

    private void registerUser() {
        User user = new User(usernameText.getText().toString(),
                passwordText.getText().toString(),
                nameText.getText().toString(),
                Integer.valueOf(ageText.getText().toString()),
                genderText.getText().toString(),
                beenWorkingOutText.getText().toString());

        UserManager.register(user, this);
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if (!response.isSuccessful()) {
            Log.e("ERROR", response.message());
            return;
        }
        Log.d("User registered", "User registered");
        UserManager.loginUser(response.body(), loginCallback);
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
