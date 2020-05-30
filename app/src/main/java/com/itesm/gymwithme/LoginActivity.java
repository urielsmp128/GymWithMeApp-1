package com.itesm.gymwithme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button login;
    EditText usernameEdit, passwordEdit;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEdit = findViewById(R.id.inUser);
        passwordEdit = findViewById(R.id.inPassword);
        progressBar = findViewById(R.id.progress_bar);

        login= (Button)findViewById(R.id.button_login);
        login.setOnClickListener(v -> {

            progressBar.setVisibility(View.VISIBLE);

            LoginUser loginUser = new LoginUser(usernameEdit.getText().toString().trim(),
                    passwordEdit.getText().toString().trim());

            GymService gymService = ServiceBuilder.INSTANCE.buildService(GymService.class);

            Call<LoginUser> loginCall = gymService.logIn(loginUser);
            loginCall.enqueue(new Callback<LoginUser>() {
                @Override
                public void onResponse(Call<LoginUser> call, Response<LoginUser> response) {

                    if (!response.isSuccessful()) {
                        if (response.code() == 400) {
                            Toast.makeText(
                                    LoginActivity.this,
                                    "invalid username or password",
                                    Toast.LENGTH_LONG)
                                    .show();
                        } else {
                            Toast.makeText(
                                    LoginActivity.this,
                                    "Response error: " + response.message(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                        progressBar.setVisibility(View.GONE);
                        return;
                    }

                    String token = response.headers().get("auth-token");
                    Intent firstIntent = new Intent(LoginActivity.this, MainActivity.class);
                    firstIntent.putExtra("auth-token", token);
                    startActivity(firstIntent);
                    finish();

                }

                @Override
                public void onFailure(Call<LoginUser> call, Throwable t) {

                    Toast.makeText(
                            LoginActivity.this,
                            "Failure " + t.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();
                    progressBar.setVisibility(View.GONE);

                }
            });
        });
    }
}
