package com.itesm.gymwithme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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

    private TextInputLayout nameText;
    private TextInputLayout ageText;
    private TextInputLayout genderText;
    private TextInputLayout usernameText;
    private TextInputLayout passwordText;
    private TextInputLayout beenWorkingOutText;

    private SessionManager sessionManager;

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

            // Get token
            String token = response.headers().get("auth-token");
            // Store login session
            sessionManager.setLogin(true);
            // Store token session
            sessionManager.setToken(token);
            // Store name in session
            sessionManager.setName(response.body().firstName());
            // Redirect to main
            redirectToMainActivity();
            // Finish activity
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

        sessionManager = new SessionManager(getApplicationContext());

        registerButton = findViewById(R.id.button_register);
        cancelButton = findViewById(R.id.button_cancel);

        nameText = findViewById(R.id.name_text);
        ageText = findViewById(R.id.age_text);
        genderText = findViewById(R.id.gender_text);
        usernameText = findViewById(R.id.username_text);
        passwordText = findViewById(R.id.password_text);
        beenWorkingOutText = findViewById(R.id.working_out_text);

        progressBar = findViewById(R.id.progressBar);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInProgress(true);
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

        String usernameInput = usernameText.getEditText().getText().toString().trim();
        String passwordInput = passwordText.getEditText().getText().toString().trim();
        String nameInput = nameText.getEditText().getText().toString().trim();
        String ageInput = ageText.getEditText().getText().toString().trim();
        String genderInput = genderText.getEditText().getText().toString().trim();
        String beenWOInput = beenWorkingOutText.getEditText().getText().toString().trim();

        boolean validUsername = InputValidation.isValid(usernameText, usernameInput, "username",
                20, false);

        boolean validPassword = InputValidation.isValid(passwordText, passwordInput, "password",
                50, true);

        boolean validName = InputValidation.isValid(nameText, nameInput, "name", 50,
                true);

        boolean validAge = InputValidation.isValidAge(ageText, ageInput);

        boolean validGender = InputValidation.isValidGender(genderText, genderInput);

        if (!validUsername || !validPassword || !validName || !validAge || !validGender ) {
            setInProgress(false);
            return;
        }

        User user = new User(usernameInput, passwordInput, nameInput, Integer.valueOf(ageInput),
                genderInput, beenWOInput);

        UserManager.register(user, this);
    }

    private void setInProgress(boolean inProgress) {
        progressBar.setVisibility(inProgress ? View.VISIBLE : View.GONE);
        cancelButton.setVisibility(inProgress ? View.GONE : View.VISIBLE);
        registerButton.setEnabled(!inProgress);
    }

    private void redirectToMainActivity() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if (!response.isSuccessful()) {
            usernameText.setError("the username already exists");
            setInProgress(false);
            return;
        }
        Log.d("User registered", "User registered");
        Toast.makeText(getApplicationContext(), "redirecting to login in...", Toast.LENGTH_SHORT).show();
        UserManager.loginUser(response.body(), loginCallback);
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        Toast.makeText(getApplicationContext(), "We are having problems in our end, please try again", Toast.LENGTH_SHORT).show();
        setInProgress(false);
    }
}
