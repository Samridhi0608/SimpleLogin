package com.example.simplelogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {


    private EditText loginUser;
    private EditText loginPass;
    private AppCompatButton loginBtn;
    private TextView signUpText;

    boolean isValid = false;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUser=findViewById(R.id.loginUser);
        loginPass=findViewById(R.id.loginPassword);
        loginBtn=findViewById(R.id.loginButton);
        signUpText=findViewById(R.id.signUpText);

        sharedPreferences = getApplicationContext().getSharedPreferences("DetailsDB", MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

        if(sharedPreferences != null){
            String savedName= sharedPreferences.getString("Username","");
            String savedMobile = sharedPreferences.getString("Mobile", "");
            String savedEmail = sharedPreferences.getString("Email", "");
            String savedPass = sharedPreferences.getString("Password", "");

            RegistrationActivity.details = new Details(savedName, savedMobile, savedEmail, savedPass);

        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputUser = loginUser.getText().toString();
                String inputPassword = loginPass.getText().toString();

                if(inputUser.isEmpty() || inputPassword.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please enter data", Toast.LENGTH_SHORT).show();
                }else{
                    isValid= validate(inputUser,inputPassword);

                    if(!isValid){
                        Toast.makeText(LoginActivity.this, "Incorrect credentials entered!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }

            }
        });

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

    }

    private boolean validate(String name, String password){

        if(RegistrationActivity.details != null){
            if((name.equals(RegistrationActivity.details.getMobile()) || name.equals(RegistrationActivity.details.getEmail()) ) && password.equals(RegistrationActivity.details.getPassword())){
                return true;
            }
        }

        return false;
    }
}