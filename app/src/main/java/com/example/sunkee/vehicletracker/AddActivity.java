package com.example.sunkee.vehicletracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ProgressBar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.sunkee.vehicletracker.model.User;

public class AddActivity extends AppCompatActivity {
    private EditText inputPhone,Inputname;
    private Button btnSave;
    private ProgressBar progressBar;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId ;


    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_add);

          Inputname = (EditText)findViewById(R.id.nametxt);
          inputPhone = (EditText)findViewById(R.id.phonetxt);
          btnSave = (Button)findViewById(R.id.btnSave);
          progressBar =(ProgressBar)findViewById(R.id.progressBar);
        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");




        //Display toolbar Icon

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Inputname.getText().toString();
                String phone = inputPhone.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                //Check if user already exists
                if(TextUtils.isEmpty(userId)){
                    createUser(name,phone);

                }else{
                    updateUser(name,phone);
                }
            }
        });

        toggleButton();
    }

    private void toggleButton() {
        if (TextUtils.isEmpty(userId)) {
            btnSave.setText("Save");
        } else {
            btnSave.setText("Update");
        }
    }

    private void createUser(String name,String phoneNumber){
//        if(TextUtils.isEmpty(userId)){
//            userId = mFirebaseDatabase.push().getKey();
//        }

        User user = new User(name,phoneNumber);
          mFirebaseDatabase.setValue(user);
        //addUserChangeListener();
        progressBar.setVisibility(View.GONE);


    }

    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if(user == null){
                    Log.e("err", "User data is nll!");
                    return;
                }

                Log.e("err", "User data is changed!" + user.name + ", " + user.phoneNumber);

                // clear edit text
                Inputname.setText("");
                inputPhone.setText("");

                toggleButton();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
              Log.e("error","Failed to read user",databaseError.toException());
            }
        });


    }

    private void updateUser(String name, String phoneNumber) {
        // updating the user via child nodes
        if (!TextUtils.isEmpty(name))
            mFirebaseDatabase.child(userId).child("name").setValue(name);

        if (!TextUtils.isEmpty(phoneNumber))
            mFirebaseDatabase.child(userId).child("phoneNumber").setValue(phoneNumber);
    }
}


