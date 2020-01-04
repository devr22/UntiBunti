package com.example.untibunti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tuyenmonkey.mkloader.MKLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class VerificationActivity extends AppCompatActivity {

    private TextView resend;
    private String number,id;
    FirebaseAuth mAuth;
    private MKLoader mkLoader;
    TextView che;
    FirebaseFirestore firebaseFirestore;
    Map<String,String> obj=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        final EditText otp=findViewById(R.id.etOTP);
        Button verify=findViewById(R.id.btnOTPVerfy);
        resend=findViewById(R.id.tvResend);
        mkLoader=findViewById(R.id.loader);
        mAuth=FirebaseAuth.getInstance();
        che=findViewById(R.id.tvCheck);
        firebaseFirestore=FirebaseFirestore.getInstance();

        number=getIntent().getStringExtra("number");
        che.setText(number);
        obj.put("Name","No_Name");
        obj.put("Mobile",number);
        obj.put("Coins",""+0);
        obj.put("C_Streak",""+0);
        obj.put("H_Streak",""+0);
        sendVeri();
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(otp.getText().toString()))
                {
                    Toast.makeText(VerificationActivity.this, "Enter OTP", Toast.LENGTH_SHORT).show();
                }
                else if(otp.getText().toString().replace(" ","").length()!=6)
                {
                    Toast.makeText(VerificationActivity.this, "Enter Correct", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    mkLoader.setVisibility(View.VISIBLE);
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(id, otp.getText().toString().replace(" ",""));
                    signInWithPhoneAuthCredential(credential);


                }
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVeri();
            }
        });

    }
    private void sendVeri() {
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
                resend.setText(""+l/1000);
                resend.setEnabled(false);

            }

            @Override
            public void onFinish() {
                resend.setText("Resend");
                resend.setEnabled(true);

            }
        }.start();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        id=s;
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);

                    }



                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        che.setText(e.getMessage());
                        Log.e("Error",""+e.getMessage());
                        Toast.makeText(VerificationActivity.this, "Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                });



    }



    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mkLoader.setVisibility(View.GONE);
                        if (task.isSuccessful()) {

                            firebaseFirestore.collection("USERS").document(number).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(documentSnapshot.get("Name")==null)
                                    {
                                        firebaseFirestore.collection("USERS").document(number).set(obj);
                                        Toast.makeText(VerificationActivity.this, "New User", Toast.LENGTH_SHORT).show();
                                        Log.e("Status","new");
                                    }
                                    else
                                    {
                                        Toast.makeText(VerificationActivity.this, "New User", Toast.LENGTH_SHORT).show();
                                        Log.e("Status","old");
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });

                            Intent intent =new Intent(VerificationActivity.this,MainActivity.class);




                            startActivity(intent);
                            finish();




                        } else {
                            Toast.makeText(VerificationActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();


                        }
                    }
                });
    }
}
