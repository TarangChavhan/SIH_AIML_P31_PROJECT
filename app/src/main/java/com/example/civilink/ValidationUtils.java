package com.example.civilink;

import android.text.TextUtils;
import android.util.Patterns;
import java.util.regex.Pattern;

public class ValidationUtils {
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final Pattern PASSWORD_PATTERN = 
        Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
    
    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    
    public static boolean isValidPassword(String password) {
        if (password == null) return false;
        return password.length() >= MIN_PASSWORD_LENGTH && PASSWORD_PATTERN.matcher(password).matches();
    }
    
    public static String getPasswordValidationMessage() {
        return "Password must contain at least 8 characters including uppercase, lowercase, number and special character";
    }
    
    public static boolean isValidName(String name) {
        return !TextUtils.isEmpty(name) && name.length() >= 2;
    }
    
    public static String getPasswordStrength(String password) {
        int strength = 0;
        if (password.length() >= 8) strength++;
        if (password.matches(".*[A-Z].*")) strength++;
        if (password.matches(".*[a-z].*")) strength++;
        if (password.matches(".*[0-9].*")) strength++;
        if (password.matches(".*[@#$%^&+=].*")) strength++;
        
        switch (strength) {
            case 5: return "Very Strong";
            case 4: return "Strong";
            case 3: return "Medium";
            case 2: return "Weak";
            default: return "Very Weak";
        }
    }
}