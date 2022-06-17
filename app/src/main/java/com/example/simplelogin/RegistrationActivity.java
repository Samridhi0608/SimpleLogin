package com.example.simplelogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class RegistrationActivity extends AppCompatActivity {

    private EditText regName;
    private EditText regMobile;
    private EditText regEmail;
    private EditText regPassword;
    private AppCompatButton regBtn;
    private TextView loginText;

    public static Details details;

    String mobilePattern="^[6-9]\\d{9}$";
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        regName=findViewById(R.id.regName);
        regMobile=findViewById(R.id.regMobile);
        regEmail=findViewById(R.id.regEmail);
        regPassword=findViewById(R.id.regPassword);
        regBtn=findViewById(R.id.regButton);
        loginText=findViewById(R.id.loginText);

        sharedPreferences = getApplicationContext().getSharedPreferences("DetailsDB", MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Username= regName.getText().toString();
                String Mobile= regMobile.getText().toString();
                String Email= regEmail.getText().toString();
                String Password= regPassword.getText().toString();

                if (TextUtils.isEmpty(Username) || TextUtils.isEmpty(Email) || TextUtils.isEmpty(Mobile) || TextUtils.isEmpty(Password)) {
                    Toast.makeText(RegistrationActivity.this, "Fill all the fields!", Toast.LENGTH_SHORT).show();
                }else if(Username.length()<4 || Username.length()>25){
                    regName.setError("Username must be more than 4 & less than 25 characters");
                    Toast.makeText(RegistrationActivity.this, "Invalid username", Toast.LENGTH_SHORT).show();
                }else if (!Mobile.matches(mobilePattern)){
                    regMobile.setError("Invalid mobile no.");
                    Toast.makeText(RegistrationActivity.this, "Please enter valid mobile no.", Toast.LENGTH_SHORT).show();
                }else if(!Email.matches(emailPattern)){
                    regEmail.setError("Invalid email address");
                    Toast.makeText(RegistrationActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                }else if(checkPass(Password,Username)==false){
                    regPassword.setError("Password must contain 8-15 characters, starting with lowercase, at least 2 uppercase, 2 digits & 1 special character");
                    Toast.makeText(RegistrationActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                }else{
                    details = new Details(Username, Mobile, Email, Password);

                    /* Store the credentials */
                    sharedPreferencesEditor.putString("Username", Username);
                    sharedPreferencesEditor.putString("Mobile", Mobile);
                    sharedPreferencesEditor.putString("Email", Email);
                    sharedPreferencesEditor.putString("Password", Password);

                    /* Commits the changes and adds them to the file */
                    sharedPreferencesEditor.apply();

                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                    Toast.makeText(RegistrationActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean checkPass(String pass, String name){

        if(pass.length()<8 || pass.length()>15){
            return false;
        }else if(!Character.isLowerCase(pass.charAt(0))){
            return false;
        }else if(pass.contains(name)){
            return false;
        }else if(countUpper(pass)<2){
            return false;
        }else if(countDigits(pass)<2){
            return false;
        }else if(checkSpecialChar(pass)==-1){
            return false;
        }

        return true;
    }

    public int countUpper(String pass){
        int count=0;
        for(int i=0; i<pass.length();i++){
            if(Character.isUpperCase(pass.charAt(i))){
                count++;
            }
        }
        return count;
    }

    public int countDigits(String pass){
        int count=0;
        for(int i=0; i<pass.length();i++){
            if(Character.isDigit(pass.charAt(i))){
                count++;
            }
        }
        return count;
    }

    public int checkSpecialChar(String pass){
        int count=0;
        for (int i = 0; i < pass.length(); i++) {

            // Checking the character for not being a
            // letter,digit or space
            if (!Character.isDigit(pass.charAt(i))
                    && !Character.isLetter(pass.charAt(i))
                    && !Character.isWhitespace(pass.charAt(i))) {
                // Incrementing the countr for spl
                // characters by unity
                count++;
            }
        }

        if(count==0){
            return -1;
        }

        return 0;

    }

}