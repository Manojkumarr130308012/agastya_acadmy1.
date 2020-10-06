package com.neo.agastya.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.neo.agastya.R;

public class Login_studentTeacher extends AppCompatActivity {
    TextView student,teacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_student_teacher);


        student = (TextView)findViewById(R.id.student);
//        teacher = (TextView)findViewById(R.id.teacher);

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(Login_studentTeacher.this,teacher.class);
                startActivity(it);

            }
        });



/*

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(Login_studentTeacher.this,student.class);
                startActivity(it);


            }
        });*/
    }
}
