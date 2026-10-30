package com.example.mammaldex.model.database;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseInitializer extends AppCompatActivity{

    private DatabaseReference databaseReference;
    private FirebaseDatabase mammalDexFirebase;

    public FirebaseInitializer(){
        mammalDexFirebase = FirebaseDatabase.getInstance();
        // reference will be the user credentials
        databaseReference = mammalDexFirebase.getReference();
    }

    private boolean authenticateUser(String key, String value){
        return false;
    }


    private void readUserData(){

    }

    private void addUserData(String key, String value){
        databaseReference.child(key).setValue(value).addOnSuccessListener(aVoid->{
            // on success perform action
        }).addOnFailureListener(e->{
            // on failure perform action
        });
    }
}
