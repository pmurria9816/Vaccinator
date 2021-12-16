package com.assignment2.vaccinator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.assignment2.vaccinator.database.DatabaseHandler;
import com.assignment2.vaccinator.models.User;

public class LaunchActivity extends AppCompatActivity {

    LinearLayout layout;

    DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Button save = (Button) findViewById(R.id.saveUserButton);
        dbHandler = DatabaseHandler.getInstance(getApplicationContext());


        layout = findViewById(R.id.registerLayout);
        layout.setVisibility(View.GONE);

        //Saving user events
        save.setOnClickListener(view -> {

            EditText firstName, lastName, phone, email, password, confirm;

                firstName = findViewById(R.id.regFirstName);
                lastName = findViewById(R.id.regLastName);
                phone = findViewById(R.id.regPhone);
                email = findViewById(R.id.regEmail);
                password = findViewById(R.id.regPassword);
                confirm = findViewById(R.id.regConfirmPassword);

                boolean wrongFirst = checkError(firstName);
                boolean wrongLast = checkError(lastName);
                boolean wrongPhone = checkError(phone);
                boolean wrongEmail = checkError(email);
                boolean wrongPass = checkError(password);
                boolean wrongConfirm = checkError(confirm);

                if(wrongFirst || wrongLast || wrongPhone || wrongEmail || wrongPass || wrongConfirm)
                    return;

                if(password.getText().toString().compareTo(confirm.getText().toString()) == 0) {

                User user = new User(email.getText().toString(), password.getText().toString(), firstName.getText().toString(), lastName.getText().toString(), phone.getText().toString());
                dbHandler.addUser(user);
                Toast.makeText(getApplicationContext(), "User successfully registered!", Toast.LENGTH_SHORT).show();

                layout = findViewById(R.id.loginLayout);
                layout.setVisibility(View.VISIBLE);
                layout = findViewById(R.id.registerLayout);
                layout.setVisibility(View.GONE);
            }
            else{
                Toast.makeText(getApplicationContext(), "Passwords doesn't match", Toast.LENGTH_SHORT).show();
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

                boolean wrongName = checkError(username);
                boolean wrongPass = checkError(password);

                if( wrongPass || wrongName )
                    return;

                int auth = dbHandler.authenticate(username.getText().toString(), password.getText().toString());

                if(auth > 0) {

                    SharedPreferences pref = getApplicationContext().getSharedPreferences("preferences", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("userId", auth);
                    editor.commit();

                    Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(getApplicationContext(), "Login Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkError(EditText element){
        boolean error = false;
        if (element.getText().toString().trim().equalsIgnoreCase("")) {
            element.setError("This field can not be blank");
            error = true;
        }

        return error;
    }
}