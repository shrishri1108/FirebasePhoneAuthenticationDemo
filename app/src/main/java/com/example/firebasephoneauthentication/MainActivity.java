package com.example.firebasephoneauthentication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.firebasephoneauthentication.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;
    private String phoneno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding= ActivityMainBinding.inflate( getLayoutInflater());
        setContentView(mainBinding.getRoot());
        mainBinding.btnSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainBinding.progressBar.setVisibility(View.VISIBLE);
                phoneno= "+91"+mainBinding.etPhoneNo.getText().toString();
                if(mainBinding.etPhoneNo.getText().toString().isEmpty()|| mainBinding.etPhoneNo.getText().toString().length()!= 10){
                    mainBinding.etPhoneNo.setError("Mandatory Field . length should be 10");  
                    mainBinding.etPhoneNo.requestFocus();
                    return;
                }
                Intent intent=new Intent(MainActivity.this, EnterOtpActivity.class);
                intent.putExtra("phoneno",phoneno);
                startActivity(intent);
                finish();
            }
        });
    }


}