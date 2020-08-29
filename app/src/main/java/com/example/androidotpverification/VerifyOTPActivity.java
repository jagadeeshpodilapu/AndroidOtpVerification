package com.example.androidotpverification;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyOTPActivity extends AppCompatActivity {

    private EditText et1, et2, et3, et4, et5, et6;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        TextView textMobile = findViewById(R.id.textMobile);

        textMobile.setText(String.format("+91- %s", getIntent().getStringExtra("mobile")));

        et1 = findViewById(R.id.inputCode1);
        et2 = findViewById(R.id.inputCode2);
        et3 = findViewById(R.id.inputCode3);
        et4 = findViewById(R.id.inputCode4);
        et5 = findViewById(R.id.inputCode5);
        et6 = findViewById(R.id.inputCode6);
        setUpOTPInputs();

        final ProgressBar progressBar = findViewById(R.id.progressBarVerify);

        final Button verify = findViewById(R.id.buttonVerify);
        verificationId = getIntent().getStringExtra("verificationId");


        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et1.getText().toString().trim().isEmpty()
                        || et2.getText().toString().trim().isEmpty()
                        || et3.getText().toString().trim().isEmpty()
                        || et4.getText().toString().trim().isEmpty()
                        || et5.getText().toString().trim().isEmpty()
                        || et6.getText().toString().trim().isEmpty()
                ) {
                    Toast.makeText(VerifyOTPActivity.this, "Please Enter Valid OTP", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code = et1.getText().toString() +
                        et2.getText().toString() +
                        et3.getText().toString() +
                        et4.getText().toString() +
                        et5.getText().toString() +
                        et6.getText().toString();

                if(verificationId!=null){
                    progressBar.setVisibility(View.VISIBLE);
                    verify.setVisibility(View.GONE);
                    PhoneAuthCredential phoneAuthCredential=PhoneAuthProvider.getCredential(verificationId,code);

                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    verify.setVisibility(View.VISIBLE);

                                    if(task.isSuccessful()){
                                        Intent i=new Intent(getApplicationContext(),MainActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(i);
                                    }else{
                                        Toast.makeText(VerifyOTPActivity.this, "Verification Code was Wrong", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
            }
        });

        findViewById(R.id.textResendOTP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91"+ getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        VerifyOTPActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(VerifyOTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                verificationId=newVerificationId;
                                Toast.makeText(VerifyOTPActivity.this, "OTP sent", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });

    }

    private void setUpOTPInputs() {

        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    et2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    et3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    et4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    et5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    et6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
