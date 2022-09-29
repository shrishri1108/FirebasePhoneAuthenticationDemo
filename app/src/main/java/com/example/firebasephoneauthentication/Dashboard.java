package com.example.firebasephoneauthentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.firebasephoneauthentication.databinding.ActivityDashboardBinding;
import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {

    private String user_name;
    private ActivityDashboardBinding dashboardBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dashboardBinding= ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(dashboardBinding.getRoot());

        user_name= getIntent().getStringExtra("user_name");
        dashboardBinding.userName.setText(user_name);

        dashboardBinding.btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dashboardBinding.progressBarDashboard.setVisibility(View.VISIBLE);
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Dashboard.this, "Successfully Logged Out! ", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(Dashboard.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}