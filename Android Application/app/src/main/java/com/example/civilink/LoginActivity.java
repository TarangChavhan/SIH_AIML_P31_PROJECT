package com.example.civilink;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText etUsername, etPassword;
    androidx.appcompat.widget.AppCompatButton btnLogin, btnGoogle;
    TextView tvSignUp;

    // 🔗 Replace with your backend API
    String BASE_URL = "http://YOUR_IP_OR_DOMAIN/login.php";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Bind views
        etUsername = findViewById(R.id.LoginTextInputEditTextUsername);
        etPassword = findViewById(R.id.LoginTextInputEditTextPassword);
        btnLogin   = findViewById(R.id.Loginbtn);
        btnGoogle  = findViewById(R.id.GoogleLoginBtn);
        tvSignUp   = findViewById(R.id.SignUp);

        // Login Button Click
        btnLogin.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        });

        // Google Login Placeholder
        btnGoogle.setOnClickListener(v -> {
            Toast.makeText(LoginActivity.this, "Google Login clicked", Toast.LENGTH_SHORT).show();
            // 👉 Later: Implement Firebase Google Sign-In here
        });

        // Redirect to Registration
        tvSignUp.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(i);
        });
    }

    private void loginUser(String username, String password) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);

        client.post(BASE_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String response = new String(responseBody);
                    // Expected: {"status":"success","message":"Login Successful"}
                    org.json.JSONObject json = new org.json.JSONObject(response);

                    String status = json.getString("status");
                    String message = json.getString("message");

                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();

                    if (status.equals("success")) {
                        // ✅ Redirect to HomeActivity after login
                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(LoginActivity.this, "Login Failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
