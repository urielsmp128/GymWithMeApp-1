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

    private Button toRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEdit = findViewById(R.id.inUser);
        passwordEdit = findViewById(R.id.inPassword);
        progressBar = findViewById(R.id.progress_bar);

        toRegisterButton = findViewById(R.id.to_register_button);

        login= (Button)findViewById(R.id.button_login);
        login.setOnClickListener(v -> {

            progressBar.setVisibility(View.VISIBLE);

            User user = new User(usernameEdit.getText().toString().trim(),
                    passwordEdit.getText().toString().trim());

            GymService gymService = ServiceBuilder.INSTANCE.buildService(GymService.class);

            Call<User> loginCall = gymService.logIn(user);
            loginCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

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
                public void onFailure(Call<User> call, Throwable t) {

                    Toast.makeText(
                            LoginActivity.this,
                            "Failure " + t.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();
                    progressBar.setVisibility(View.GONE);

                }
            });
        });

        toRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
