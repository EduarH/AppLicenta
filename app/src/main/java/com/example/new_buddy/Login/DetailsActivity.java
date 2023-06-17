package com.example.new_buddy.Login;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.example.new_buddy.R;

import java.util.HashMap;
import java.util.Map;


/**
 * Fragment Responsible for registering a new user
 */
public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mName;
    private Button mRegister;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initializeObjects();
    }

    /**
     * Register the user, but before that check if every field is correct.
     * After that registers the user and creates an entry for it in the database
     */
    private void register() {
        if (mName.getText().length() == 0) {
            mName.setError("Please fill this field");
            return;
        }

        final String name = mName.getText().toString();

        String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Map<String, Object> newUserMap = new HashMap<>();
        newUserMap.put("name", name);
        newUserMap.put("profileImageUrl", "default");
        newUserMap.put("service", "type_1");
        newUserMap.put("activated", true);

        FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child("Drivers")
                .child(user_id)
                .updateChildren(newUserMap)
                .addOnCompleteListener((OnCompleteListener<Void>) task -> {
                    Intent intent = new Intent(DetailsActivity.this, LauncherActivity.class);
                    startActivity(intent);
                    finish();
                });
    }

    /**
     * Initializes the design elements and sets click listeners for them
     */
    private void initializeObjects() {
        mName = findViewById(R.id.name);
        mRegister = findViewById(R.id.register);

        mRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.register) {
            register();
        }
    }
}
