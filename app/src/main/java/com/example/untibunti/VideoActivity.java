package com.example.untibunti;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.khizar1556.mkvideoplayer.MKPlayer;
import com.khizar1556.mkvideoplayer.MKPlayerActivity;

public class VideoActivity extends AppCompatActivity {

    StorageReference storageReference;
    MKPlayer mkplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        final Activity activity =this;
        Context cc=this;




        mkplayer = new  MKPlayer(this);
        mkplayer.play("https://www.radiantmediaplayer.com/media/bbb-360p.mp4");
        final long[] c = new long[1];
        mkplayer.onComplete(new Runnable() {
            @Override
            public void run() {
                final String no= FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
                Log.e("no",no);
               FirebaseFirestore.getInstance().runTransaction(new Transaction.Function<Void>() {
                   @Nullable
                   @Override
                   public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                       DocumentSnapshot documentSnapshot= transaction.get(FirebaseFirestore.getInstance().collection("USERS").document(no));
                       long ne = documentSnapshot.getLong("Coins")+10;
                       transaction.update(FirebaseFirestore.getInstance().collection("USERS").document(no),"Coins",ne);

                       return null;
                   }
               }).addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void aVoid) {
                       Log.e("update","yes");
                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Log.e("update Error",e.getMessage());
                   }
               });

                activity.startActivity(new Intent(activity,MainActivity.class));
                finish();
            }
        });







    }

    @Override
    protected void onStop() {
        super.onStop();
        mkplayer.stop();
    }


}
