package com.example.firebasephoneauthentication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.firebasephoneauthentication.databinding.ActivityEnterOtpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class EnterOtpActivity extends AppCompatActivity {

    private ActivityEnterOtpBinding otpBinding;
    private FirebaseAuth mAuth;
    String otpId,phoneno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        otpBinding = ActivityEnterOtpBinding.inflate(getLayoutInflater());
        setContentView(otpBinding.getRoot());

        phoneno= getIntent().getStringExtra("phoneno").toString();
        otpBinding.tvContactNo.setText( "Phone no: "+ phoneno);
        mAuth = FirebaseAuth.getInstance(); //    getting instance of firebase
        mAuth.setLanguageCode("hi");
        initiateOtp(phoneno);
        otpBinding.btnConfirmOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otpBinding.progressBarOtp.setVisibility(View.VISIBLE);
                 PhoneAuthCredential authCredential=PhoneAuthProvider.getCredential(otpId, otpBinding.etOtp.getText().toString());
                 signInWithPhoneAuthCredential(authCredential);
            }
        });
    }
    private void initiateOtp(String phoneno) {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneno,        // Phone number to verify
                    60,                 //Timeout duration
                    TimeUnit.SECONDS,   // Unit of Timeout
                    this,               // Activity (for Callback binding)
                    // OnVerificationStateChangedCallbacks
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            // called when only otp is send on the same device .
                            signInWithPhoneAuthCredential(phoneAuthCredential);
                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            //  only called when otp is send into the same device
                            Toast.makeText(EnterOtpActivity.this, " onVerificationFailed: --> "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onVerificationFailed:  "+ e.getMessage());
                        }

                        @Override


                        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            // called whether otp is send to same or different device
                            otpId=s;
                        }
                    }
            );
        }

        private  void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the sign-in user's information
                                Log.d(TAG, "onComplete:  signInWithCredential:success");
        
                                Intent intent= new Intent(EnterOtpActivity.this, Dashboard.class);
                                intent.putExtra("userName",phoneno);
                                startActivity(intent);
                                otpBinding.progressBarOtp.setVisibility(View.INVISIBLE);
                                finish();
                                FirebaseUser user = task.getResult().getUser();
                                // Update UI

                            } else {
                                otpBinding.progressBarOtp.setVisibility(View.INVISIBLE);
                                // sign in failed, display a message and update the UI
                                Log.w(TAG, "instance initializer: failure ");
                                Toast.makeText(EnterOtpActivity.this, " signin Code Error ", Toast.LENGTH_SHORT).show();
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    //  The verification code entered was invalid
                                    
                                }
                            }
                        }
                    });
        }
}