package com.example.messfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    Button button;
    ProgressBar progressBar;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foodiee-dfd2d-default-rtdb.firebaseio.com/");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar=findViewById(R.id.progress_bar);
        final EditText username = findViewById(R.id.email_edit_text);
        final EditText password = findViewById(R.id.password_edit_text);
        final Button LoginBtn = findViewById(R.id.logout_btn);

        button = findViewById(R.id.logout_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                final String usernameTxt = username.getText().toString();
                final String PasswordTxt = password.getText().toString();

                if (usernameTxt.isEmpty() || PasswordTxt.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter your Username and Password", Toast.LENGTH_SHORT).show();
                }
                else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //check if email is existing in the db
                            if (snapshot.hasChild(usernameTxt)){

                                final String getpassword = snapshot.child(usernameTxt).child("password").getValue(String.class);

                                if (getpassword.equals(PasswordTxt)){
                                    Toast.makeText(LoginActivity.this, "Successfully Logged in",Toast.LENGTH_SHORT).show();

                                    //Starting student form

                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }
                                else {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(LoginActivity.this,"Wrong Credentials",Toast.LENGTH_SHORT).show();
                                }

                            }
//
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                ////////////////////////////////////////////////////////////////////////////////
                databaseReference.child("admin").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        //check if email is existing in the db
                        if (snapshot.hasChild(usernameTxt)){

                            final String getpassword = snapshot.child(usernameTxt).child("password").getValue(String.class);

                            if (getpassword.equals(PasswordTxt)){
                                Toast.makeText(LoginActivity.this, "Successfully Logged in admin panel",Toast.LENGTH_SHORT).show();

                                //Starting student form
                                startActivity(new Intent(LoginActivity.this, Adminhome.class));
                                finish();
                            }
//                                                      Toast.makeText(login.this," ",Toast.LENGTH_SHORT).show();
                            else {
                                Toast.makeText(LoginActivity.this,"Incorrect username or password",Toast.LENGTH_SHORT).show();
                            }
                        }
//
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                ////////////////////////////////////////////////////////////////////////////////
            }
        });

        TextView tv = (TextView) this.findViewById(R.id.create_account_text_view_btn);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //open register activity
                startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
            }
        });




    }
}