package com.example.messfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateAccountActivity extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foodiee-dfd2d-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        final EditText Email= findViewById(R.id.email_edit_text);
        final EditText password = findViewById(R.id.password_edit_text);
        final EditText confirmpassword = findViewById(R.id.confirm_password_edit_text);

        final Button registerBtn = findViewById(R.id.create_account_btn);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailtxt = Email.getText().toString();

                final String passwordtxt = password.getText().toString();
                final String conPasswordtxt = confirmpassword.getText().toString();

                //checking no feilds are empty
                if (emailtxt.isEmpty() || emailtxt.isEmpty() || passwordtxt.isEmpty() || conPasswordtxt.isEmpty()){
                    Toast.makeText(CreateAccountActivity.this, "Please fill all feilds", Toast.LENGTH_SHORT).show();
                }
                //check if passwords are matching with each other
                else if (!passwordtxt.equals(conPasswordtxt)){
                    Toast.makeText(CreateAccountActivity.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();
                }
                else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //check if email is registered
                            if (snapshot.hasChild(emailtxt)) {
                                Toast.makeText(CreateAccountActivity.this, "Email is already registered", Toast.LENGTH_SHORT).show();
                            } else {
                                //sending data to firebase realtime database
                                //using email as unique identity

                                databaseReference.child("users").child(emailtxt).child("Email").setValue(emailtxt);
                                databaseReference.child("users").child(emailtxt).child("password").setValue(passwordtxt);
                                databaseReference.child("users").child(emailtxt).child("ConfirmPassword").setValue(conPasswordtxt);


                                Toast.makeText(CreateAccountActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                //finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
        TextView tv = (TextView) this.findViewById(R.id.login_text_view_btn);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //open register activity
                startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
            }
        });

    }


}