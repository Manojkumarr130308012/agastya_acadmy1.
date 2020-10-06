package com.neo.agastya.Activity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.neo.agastya.MainActivity;
import com.neo.agastya.R;


public class ActivitySignup extends AppCompatActivity {

  ImageView signupback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        signupback = (ImageView)findViewById(R.id.signupback);

        signupback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it = new Intent(ActivitySignup.this, MainActivity.class);
                startActivity(it);

            }
        });

    }
}
