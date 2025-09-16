package com.example.civilink;

import androidx.appcompat.app.AppCompatActivity;

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
    TextView tvRegister;
    androidx.appcompat.widget.AppCompatButton btnLogin, btnGoogle;

    String BASE_URL = "http://YOUR_LOCALHOST_OR_SERVER/login.php";
    // 👉 Replace with your backend API link

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        etUsername = findViewById(R.id.LoginTextInputEditTextUsername);
        etPassword = findViewById(R.id.LoginTextInputEditTextPassword);
        btnLogin = findViewById(R.id.Loginbtn);
        tvRegister = findViewById(R.id.TVRegistration);
        btnGoogle = findViewById(R.id.btnGooglelogin);

        // Login Button Click
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(username, password);
                }
            }
        });

        // Navigate to Register
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        // Google Button (Just Example, implement Firebase/Google SDK if needed)
        btnGoogle.setOnClickListener(v -> {
            Toast.makeText(LoginActivity.this, "Google Login clicked", Toast.LENGTH_SHORT).show();
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
                    // Example Response: {"status":"success","message":"Login Successful"}
                    org.json.JSONObject json = new org.json.JSONObject(response);

                    String status = json.getString("status");
                    String message = json.getString("message");

                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();

                    if (status.equals("success")) {
                        // Go to Home/Dashboard
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
