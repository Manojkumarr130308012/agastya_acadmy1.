package com.neo.agastya.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.neo.agastya.R;
import com.squareup.picasso.Picasso;

public class Viewcirculer extends AppCompatActivity {
ImageView image;
TextView date,subtitle,title,link;

String imagestr,datestr,subtitlestr,titlestr,linkstr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcirculer);
         image=findViewById(R.id.image);
         date=findViewById(R.id.date);
        subtitle=findViewById(R.id.subtitle);
        title=findViewById(R.id.title);
        link=findViewById(R.id.link);



        Intent intent = getIntent();
        imagestr = intent.getStringExtra("image");
        datestr = intent.getStringExtra("date");
        subtitlestr = intent.getStringExtra("subtitle");
        titlestr = intent.getStringExtra("student_title");
        linkstr = intent.getStringExtra("link");



        Picasso.get().load(""+imagestr).into(image);
        date.setText(datestr);
        title.setText(titlestr);
        subtitle.setText(subtitlestr);
        link.setText(linkstr);


    }
}
