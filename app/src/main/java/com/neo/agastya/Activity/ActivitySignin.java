package com.neo.agastya.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.neo.agastya.R;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.neo.agastya.Config.DBHelper;
public class ActivitySignin extends AppCompatActivity {


    EditText edtUsername;
    EditText edtPassword;
    TextView btnLogin;

    DBHelper dbHelper;

    String Storeuser;
    String message;

    ProgressBar pbar;
    String checkusername;
    String checkpassword;
     ImageView signinback;
    SharedPreferences sharedpreferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);


        dbHelper=new DBHelper(this);


        sharedpreferences = getSharedPreferences("Mypref",
                Context.MODE_PRIVATE);

        signinback = (ImageView)findViewById(R.id.signinback);
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (TextView) findViewById(R.id.signin);
        pbar = (ProgressBar) findViewById(R.id.log_progress);
//        userService = ApiUtils.getUserService();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkusername = edtUsername.getText().toString();
                checkpassword = edtPassword.getText().toString();
                //validate form
                if(validateLogin(checkusername, checkpassword)){
                    //do loginhj

                    Log.e("ffffffffffffffff",""+checkusername);
                    Log.e("ffffffffffffffff",""+checkpassword);

                    fetchData fetchData = new fetchData();
                    fetchData.execute();

                }
            }
        });



    }

    public boolean validateLogin(String username, String password){
        if(username == null || username.trim().length() == 0){
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public class fetchData extends AsyncTask<Void,Void,Void> {
        String data = "";
        String dataParsed = "";
        String singleParsed = "";

        @Override
        protected void onPreExecute() {
//            pbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            if (checkusername == null || checkpassword == null) {

//                Toast.makeText(LoginActivity.this, " Fill the Fields", Toast.LENGTH_SHORT).show();

            } else {

                try {

                    URL url = new URL("http://neoinfotech.com/neoerp/agastya/api/student_profile.php?login_mobile="+checkusername+"&student_id="+checkpassword);

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                   InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = "";
                    while (line != null) {
                        line = bufferedReader.readLine();
                        data = data + line;
                    }

                    Log.e("dddddddddddddd", "" + data);
                    JSONObject jsonobj = new JSONObject(data);
                    JSONObject jObject = jsonobj.getJSONObject("outward");

                    singleParsed = (String) jObject.get("login_name");
                    Storeuser = (String) jObject.get("login_id");
                    message = (String) jObject.get("type");
                    Log.e("ddddddddd", "" + singleParsed);
                    Log.e("ddddddddd", "" + Storeuser);
                    Log.e("ddddddddd", "" + message);
                    dataParsed = dataParsed + singleParsed + "\n";
                    if (message != "1") {
//                        pbar.setVisibility(View.INVISIBLE);
                        dbHelper.insertData(Storeuser,singleParsed);
                        Intent i=new Intent(ActivitySignin.this,Student_details.class);
                        i.putExtra("user",""+Storeuser);
                        startActivity(i);
                    }else{
                        Toast.makeText(ActivitySignin.this, ""+message, Toast.LENGTH_SHORT).show();

                    }
              /*      if (message != "Welcome !!") {
                        dbHelper.insertData(Storeuser,singleParsed);
//                        pbar.setVisibility(View.INVISIBLE);

                        Intent i=new Intent(ActivitySignin.this,Student_details.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(ActivitySignin.this, ""+message, Toast.LENGTH_SHORT).show();
//                        pbar.setVisibility(View.INVISIBLE);
                    }*/


                } catch (MalformedURLException e) {
                    e.printStackTrace();
//                    WriteFile(e);
                } catch (IOException e) {
                    e.printStackTrace();
//                    WriteFile(e);
                } catch (JSONException e) {
                    e.printStackTrace();
//                    WriteFile(e);
                }

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


        }
    }

}
