package com.example.untibunti;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFrag extends Fragment {




    public ProfileFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ListView lv=view.findViewById(R.id.lvProfile);
        LinearLayout linearLayout=view.findViewById(R.id.aboutdispLL);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(),About.class));

            }
        });

        String[] Title={"Coupons","History","Setting","Share","Check"};
        MyAdapter adapter =new MyAdapter(view.getContext(),Title);
        lv.setAdapter(adapter);
        ImageView iv=view.findViewById(R.id.imageView);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), view.getContext().toString()+"  ", Toast.LENGTH_SHORT).show();
            }
        });


    }

    class MyAdapter extends ArrayAdapter<String>
    {
        Context context;
        String[] rTitle;

        MyAdapter(Context c,String[] title)
        {
            super(c,R.layout.profile_row,title);
            this.context=c;
            this.rTitle=title;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View r=convertView;
            ViewHolder viewHolder =null;
            if(r==null)
            {
                LayoutInflater layoutInflater= (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                r=layoutInflater.inflate(R.layout.profile_row,null,true);
                viewHolder=new ViewHolder(r);
                r.setTag(viewHolder);

            }
            else
            {
                viewHolder= (ViewHolder) r.getTag();

            }
            viewHolder.ttl.setText(rTitle[position]);




            return r;

        }

        class ViewHolder
        {
            TextView ttl;
            ViewHolder(View v)
            {
                ttl=v.findViewById(R.id.tvItemName);
            }
        }
    }


}

