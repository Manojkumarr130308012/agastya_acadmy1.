package com.neo.agastya.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.neo.agastya.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Student_Profile extends AppCompatActivity {
    CircleImageView imageview;
    TextView class_sec,student_name,student_id;

    CardView profileId,attendance,homeworkId,annocement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__profile);

        imageview=findViewById(R.id.profile_image);
        class_sec=(TextView)findViewById(R.id.classname);
        student_name=(TextView) findViewById(R.id.name);
        profileId=findViewById(R.id.profileId);
        homeworkId=findViewById(R.id.homeworkId);
        annocement=findViewById(R.id.annocement);
        attendance=findViewById(R.id.attendance);
        Intent intent = getIntent();
        String student_photo = intent.getStringExtra("student_photo");
        String class_sec_text= intent.getStringExtra("class_sec");
        String student_name_text = intent.getStringExtra("student_name");
        final String student_id = intent.getStringExtra("student_id");

        Picasso.get().load(""+student_photo).into(imageview);
        student_name.setText(""+student_name_text);
        class_sec.setText(""+class_sec_text);

        profileId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Student_Profile.this,Profile.class);
                i.putExtra("student_id", ""+student_id);
                startActivity(i);
            }
        });

        homeworkId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Student_Profile.this,Homework.class);
                i.putExtra("student_id", ""+student_id);
                startActivity(i);
            }
        });
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Student_Profile.this,Attendance.class);
                i.putExtra("student_id", ""+student_id);
                startActivity(i);
            }
        });


        annocement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Student_Profile.this,Circulurnews.class);
                i.putExtra("student_id", ""+student_id);
                startActivity(i);
            }
        });
    }
}
