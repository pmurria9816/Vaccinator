package com.assignment2.vaccinator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.assignment2.vaccinator.database.DatabaseHandler;
import com.assignment2.vaccinator.models.User;

/**
 * This class responsibility is to take care of all activities regarding logging in and
 * register of a new user
 *
 * */
public class LaunchActivity extends AppCompatActivity {

    LinearLayout layout;
    TextView title;
    DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        //instantiating the database handler
        dbHandler = DatabaseHandler.getInstance(getApplicationContext());

        //retrieving layout and title view widgets
        layout = findViewById(R.id.registerLayout);
        layout.setVisibility(View.GONE);
        title = findViewById(R.id.Title);

        //in case the user is already logged in, redirect to main activity
        if(getUserId()!=-1){
            Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
            startActivity(intent);
        }

        Button save = (Button) findViewById(R.id.saveUserButton);
        //setting up new listener for the save button for registering new user
        save.setOnClickListener(view -> {

            EditText firstName, lastName, phone, email, password, confirm;

            //retrieving all view widgets and storing them into the local variables created above
            firstName = findViewById(R.id.regFirstName);
            lastName = findViewById(R.id.regLastName);
            phone = findViewById(R.id.regPhone);
            email = findViewById(R.id.regEmail);
            password = findViewById(R.id.regPassword);
            confirm = findViewById(R.id.regConfirmPassword);

            //checking if any of the form fields is empty
            boolean wrongFirst = checkError(firstName);
            boolean wrongLast = checkError(lastName);
            boolean wrongPhone = checkError(phone);
            boolean wrongEmail = checkError(email);
            boolean wrongPass = checkError(password);
            boolean wrongConfirm = checkError(confirm);

            //if any of the form fields is empty, stop and show end user the error
            if(wrongFirst || wrongLast || wrongPhone || wrongEmail || wrongPass || wrongConfirm)
                return;

            //in case they are not empty, verify if the password and the confirmation password are the same
            if(password.getText().toString().compareTo(confirm.getText().toString()) == 0) {

                //instantiating a new user with the data from the register form
                User user = new User(email.getText().toString(), password.getText().toString(), firstName.getText().toString(), lastName.getText().toString(), phone.getText().toString());
                //saving the new user into the database and showing the message to the user
                dbHandler.addUser(user);
                Toast.makeText(getApplicationContext(), "User successfully registered!", Toast.LENGTH_SHORT).show();

                //turn the register fields invisible and turn the login fields visible
                layout = findViewById(R.id.loginLayout);
                layout.setVisibility(View.VISIBLE);
                layout = findViewById(R.id.registerLayout);
                layout.setVisibility(View.GONE);
            }
            else{
                //in case passwords doesn't match, show the corresponding message for the end user
                Toast.makeText(getApplicationContext(), "Passwords doesn't match", Toast.LENGTH_SHORT).show();
            }
        });

        //Start of registering new user events
        Button register = (Button) findViewById(R.id.registerButton);
        //setting up a new listener for the register button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title.setText("Register");
                layout = findViewById(R.id.loginLayout);
                layout.setVisibility(View.GONE);
                layout = findViewById(R.id.registerLayout);
                layout.setVisibility(View.VISIBLE);
            }
        });

        //start of logging in events
        Button login = (Button) findViewById(R.id.login);

        //setting up a new listenner for the login button;
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText username, password;

                //retrieve username and password fields and store them in the variables created above
                username = findViewById(R.id.loginEmail);
                password = findViewById(R.id.loginPassword);

                //checking if the fields are empty
                boolean wrongName = checkError(username);
                boolean wrongPass = checkError(password);

                //in case the fields are empty, stop the proccess
                if( wrongPass || wrongName )
                    return;

                //try to authenticate the user based on the information provided on the login form
                int auth = dbHandler.authenticate(username.getText().toString(), password.getText().toString());

                if(auth > 0) { //if the data the user provided matches with the one in the database proceed to authenticate him

                    //instantiate a new shared preference editor
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("preferences", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("userId", auth);
                    editor.putString("username", dbHandler.getUsername(username.getText().toString()));
                    editor.commit();

                    Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                    startActivity(intent); //change the activity
                }
                else //in case data doesn't match, show the proper error message
                    Toast.makeText(getApplicationContext(), "Login Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * This method validates the EditText fields, checking if they were filled or not
     * @param element the EditText element you want to check
     * @return boolean, true if an error is detected, false if there is no error
     * */
    private boolean checkError(EditText element){
        boolean error = false;
        if (element.getText().toString().trim().equalsIgnoreCase("")) {
            element.setError("This field can not be blank");
            error = true;
        }

        return error;
    }

    /**
     * This method checks if the user has already logged in or not
     * @return int, the logged on user id
     * */

    private int getUserId (){

        SharedPreferences pref = getApplicationContext().getSharedPreferences("preferences", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        return pref.getInt("userId", -1); // getting Integer
    }
}