package com.neo.agastya.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.neo.agastya.Config.DBHelper;
import com.neo.agastya.R;

public class SplashActivity extends AppCompatActivity {

    DBHelper dbHelper;
    String id,na,pa;



    ImageView splash_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splash_image=findViewById(R.id.splash_image);
        Animation fade_in= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        splash_image.setAnimation(fade_in);


        dbHelper=new DBHelper(this);
        Cursor res = dbHelper.GetSQLiteDatabaseRecords();

        while (res.moveToNext()) {
            id = res.getString(0);
            na = res.getString(1);
            pa = res.getString(2);
        }



        Thread background=new Thread(){
            @Override
            public void run() {
                try {
                    sleep(1000);


                    Log.e("ddddddddddddddddd",""+id);
                    Log.e("ddddddddddddddddd",""+na);
                    Log.e("ddddddddddddddddd",""+pa);

                    if (na!=null){
                        startActivity(new Intent(SplashActivity.this,Student_details.class));
                        finish();
                    }else {
                        startActivity(new Intent(SplashActivity.this,Login_studentTeacher.class));
                        finish();
                    }

                }catch (Exception e) {

                }
            }
        };

        background.start();

    }
}
