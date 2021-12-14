package com.assignment2.vaccinator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.assignment2.vaccinator.dao.UserDAO;
import com.assignment2.vaccinator.models.User;

public class LaunchActivity extends AppCompatActivity {

    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        Button save = (Button) findViewById(R.id.saveUserButton);

        layout = findViewById(R.id.registerLayout);
        layout.setVisibility(View.GONE);

        //Saving user events
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText firstName, lastName, phone, email, password, confirm;

                firstName = findViewById(R.id.regFirstName);
                lastName = findViewById(R.id.regLastName);
                phone = findViewById(R.id.regPhone);
                email = findViewById(R.id.regEmail);
                password = findViewById(R.id.regPassword);
                confirm = findViewById(R.id.regConfirmPassword);
                if(password.getText().toString().compareTo(confirm.getText().toString()) == 0) {

                    User user = new User(email.getText().toString(), password.getText().toString(), firstName.getText().toString(), lastName.getText().toString(), phone.getText().toString());
                    UserDAO db = new UserDAO(getApplicationContext());
                    db.insert(user);
                    Toast.makeText(getApplicationContext(), "User successfully registered!", Toast.LENGTH_SHORT).show();

                    layout = findViewById(R.id.loginLayout);
                    layout.setVisibility(View.VISIBLE);
                    layout = findViewById(R.id.registerLayout);
                    layout.setVisibility(View.GONE);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Passwords doesn't match", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Start registering new user events
        Button register = (Button) findViewById(R.id.registerButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout = findViewById(R.id.loginLayout);
                layout.setVisibility(View.GONE);
                layout = findViewById(R.id.registerLayout);
                layout.setVisibility(View.VISIBLE);
            }
        });

        //login events
        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText username, password;

                username = findViewById(R.id.loginEmail);
                password = findViewById(R.id.loginPassword);

                UserDAO db = new UserDAO(getApplicationContext());

                if(db.authenticate(username.getText().toString(), password.getText().toString())) {
                    Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(getApplicationContext(), "Login Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}