package com.example.civilink;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView createAccountTextView;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize SessionManager
        sessionManager = new SessionManager(this);

        // Check if user is already logged in
        if (sessionManager.isLoggedIn()) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
            return;
        }

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        createAccountTextView = findViewById(R.id.createAccount);
        progressBar = findViewById(R.id.loading);

        // Add TextWatcher for real-time validation
        setupTextWatchers();

        loginButton.setOnClickListener(v -> attemptLogin());

        createAccountTextView.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        });
    }

    private void setupTextWatchers() {
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateEmail();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePassword();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private boolean validateEmail() {
        String email = emailEditText.getText().toString().trim();
        if (!ValidationUtils.isValidEmail(email)) {
            emailEditText.setError("Please enter a valid email address");
            return false;
        }
        emailEditText.setError(null);
        return true;
    }

    private boolean validatePassword() {
        String password = passwordEditText.getText().toString();
        if (!ValidationUtils.isValidPassword(password)) {
            passwordEditText.setError(ValidationUtils.getPasswordValidationMessage());
            return false;
        }
        passwordEditText.setError(null);
        return true;
    }

    private void attemptLogin() {
        if (!validateEmail() || !validatePassword()) {
            return;
        }

        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();

        progressBar.setVisibility(View.VISIBLE);
        loginButton.setEnabled(false);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    progressBar.setVisibility(View.GONE);
                    loginButton.setEnabled(true);

                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Create login session
                            sessionManager.createLoginSession(
                                user.getUid(),
                                user.getEmail(),
                                user.getIdToken(true).getResult().getToken()
                            );

                            // Navigate to Home screen
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        String errorMessage = task.getException() != null ? 
                            task.getException().getMessage() : 
                            "Authentication failed";
                        Toast.makeText(LoginActivity.this, errorMessage,
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is already signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null && sessionManager.isLoggedIn()) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }
    }
}