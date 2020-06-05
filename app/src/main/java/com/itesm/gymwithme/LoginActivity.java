package com.itesm.gymwithme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements Callback<User> {

    private static final String TAG = "LoginActivity";
    private Button loginButton;
    private TextInputLayout usernameText, passwordText;
    private ProgressBar progressBar;

    private Button toRegisterButton;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameText = findViewById(R.id.username_text);
        passwordText = findViewById(R.id.password_text);
        progressBar = findViewById(R.id.progress_bar);
        toRegisterButton = findViewById(R.id.to_register_button);
        loginButton = findViewById(R.id.login_button);

        sessionManager = new SessionManager(getApplicationContext());

        loginButton.setOnClickListener(v -> {

            setInProgress(true);

            // Validate they are not blank
            String username = usernameText.getEditText().getText().toString().trim();
            String password = passwordText.getEditText().getText().toString().trim();

            boolean validUsername = InputValidation.isValid(usernameText, username, "username");
            boolean validPassword = InputValidation.isValid(passwordText, password, "password");

            if (!validUsername || !validPassword) {
                setInProgress(false);
                return;
            }

            // Create user to login
            User user = new User(username, password);
            UserManager.loginUser(user, this);

        });

        toRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // If user already is logged in
        if (sessionManager.getLogin()) {
            // Redirect to main
            redirectToMainActivity();
        }
    }

    private void redirectToMainActivity() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    private void setInProgress(boolean inProgress) {
        progressBar.setVisibility(inProgress ? View.VISIBLE : View.GONE);
        loginButton.setEnabled(!inProgress);
        toRegisterButton.setEnabled(!inProgress);
    }


    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if (!response.isSuccessful()) {
            if (response.code() == 400) {
                Toast.makeText(LoginActivity.this, "Invalid username or password",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(LoginActivity.this, "Response error: " + response.message(),
                        Toast.LENGTH_LONG).show();
            }
            setInProgress(false);
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
        Toast.makeText(LoginActivity.this, "We are having troubles on our end, please try again",
                Toast.LENGTH_LONG).show();
        Log.d(TAG, t.getMessage());
        setInProgress(false);
    }
}
