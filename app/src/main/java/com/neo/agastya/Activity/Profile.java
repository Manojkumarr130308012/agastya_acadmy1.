package com.neo.agastya.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.neo.agastya.Config.DBHelper;
import com.neo.agastya.HttpConnection.HttpHandler;
import com.neo.agastya.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Profile extends AppCompatActivity {
    String student_id;
    ArrayList<HashMap<String, Object>> studentlist;
    DBHelper dbHelper;
    String id,na,pa;
    ImageView image,back;

    TextView stuid,stuname,father,mother,DOB,address,district,state,registerno,parentno,personalname;
    LinearLayout personalinfo, experience, review;
    TextView personalinfobtn, experiencebtn, reviewbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        student_id = intent.getStringExtra("student_id");
        dbHelper=new DBHelper(this);

        image=findViewById(R.id.image);
        stuid=findViewById(R.id.stuid);
        stuname=findViewById(R.id.stuname);
        father=findViewById(R.id.father);
        mother=findViewById(R.id.mother);
        DOB=findViewById(R.id.DOB);
        address=findViewById(R.id.address);
        district=findViewById(R.id.district);
        state=findViewById(R.id.state);
        registerno=findViewById(R.id.registerno);
        parentno=findViewById(R.id.parentno);
        personalname=findViewById(R.id.personalname);
        back=findViewById(R.id.back);

        Cursor res = dbHelper.GetSQLiteDatabaseRecords();

        while (res.moveToNext()) {
            id = res.getString(0);
            na = res.getString(1);
            pa = res.getString(2);
        }
        studentlist = new ArrayList<>();
        Log.e("id",""+id);
        Log.e("employee user",""+na);
        Log.e("employee id",""+pa);

        Toast.makeText(this, ""+student_id, Toast.LENGTH_SHORT).show();

        new GetProfile().execute();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Profile.this,Student_details.class);
                startActivity(i);
            }
        });

        personalinfo = findViewById(R.id.personalinfo);
        experience = findViewById(R.id.experience);
        review = findViewById(R.id.review);
        personalinfobtn = findViewById(R.id.personalinfobtn);
        experiencebtn = findViewById(R.id.experiencebtn);

        /*making personal info visible*/
        personalinfo.setVisibility(View.VISIBLE);
        experience.setVisibility(View.GONE);
        review.setVisibility(View.GONE);


        personalinfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                personalinfo.setVisibility(View.VISIBLE);
                experience.setVisibility(View.GONE);
                review.setVisibility(View.GONE);
                personalinfobtn.setTextColor(getResources().getColor(R.color.blue));
                experiencebtn.setTextColor(getResources().getColor(R.color.grey));
//                reviewbtn.setTextColor(getResources().getColor(R.color.grey));

            }
        });

        experiencebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                personalinfo.setVisibility(View.GONE);
                experience.setVisibility(View.VISIBLE);
                review.setVisibility(View.GONE);
                personalinfobtn.setTextColor(getResources().getColor(R.color.grey));
                experiencebtn.setTextColor(getResources().getColor(R.color.blue));
//                reviewbtn.setTextColor(getResources().getColor(R.color.grey));

            }
        });

  /*      reviewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                personalinfo.setVisibility(View.GONE);
                experience.setVisibility(View.GONE);
                review.setVisibility(View.VISIBLE);
                personalinfobtn.setTextColor(getResources().getColor(R.color.grey));
                experiencebtn.setTextColor(getResources().getColor(R.color.grey));
                reviewbtn.setTextColor(getResources().getColor(R.color.blue));

            }
        });*/

    }


    private class GetProfile extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            Toast.makeText(Student_details.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://neoinfotech.com/neoerp/agastya/api/student_profile.php?login_mobile="+na+ "&student_id="+student_id;
            String jsonStr = sh.makeServiceCall(url);

            Log.e("", "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray studentsjson = jsonObj.getJSONArray("student_profile");

                    // looping through All Contacts
                    for (int i = 0; i < studentsjson.length(); i++) {
                        JSONObject c = studentsjson.getJSONObject(i);
                        String student_id = c.getString("student_id");
                        String student_name = c.getString("student_name");
                        String student_photo = c.getString("student_photo");
                        String register_no = c.getString("register_no");
                        String admission_no = c.getString("admission_no");
                        String dob = c.getString("dob");
                        String father_name = c.getString("father_name");
                        String mother_name = c.getString("mother_name");
                        String address = c.getString("address");
                        String district = c.getString("district");
                        String state = c.getString("state");
                        String parent_cell_no = c.getString("parent_cell_no");
                        String student_cell_no = c.getString("student_cell_no");

                        HashMap<String,Object> students = new HashMap<>();

                        students.put("student_photo",student_photo);
                        students.put("student_name", student_name);
                        students.put("student_id", student_id);
                        students.put("register_no", register_no);
                        students.put("admission_no", admission_no);
                        students.put("dob", dob);
                        students.put("father_name", father_name);
                        students.put("mother_name", mother_name);
                        students.put("address", address);
                        students.put("district", district);
                        students.put("state", state);
                        students.put("parent_cell_no", parent_cell_no);
                        students.put("student_cell_no", student_cell_no);
                        // adding contact to contact list
                        studentlist.add(students);


                    }
                } catch (final JSONException e) {
                    Log.e("", "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e("", "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Picasso.get().load(""+studentlist.get(0).get("student_photo")).into(image);

            stuid.setText(""+studentlist.get(0).get("student_id"));
            stuname.setText(""+studentlist.get(0).get("student_name"));
            father.setText(""+studentlist.get(0).get("father_name"));
            mother.setText(""+studentlist.get(0).get("mother_name"));
            DOB.setText(""+studentlist.get(0).get("dob"));
            address.setText(""+studentlist.get(0).get("address"));
            district.setText(""+studentlist.get(0).get("district"));
            state.setText(""+studentlist.get(0).get("state"));
            registerno.setText(""+studentlist.get(0).get("register_no"));
            parentno.setText(""+studentlist.get(0).get("parent_cell_no"));
            personalname.setText(""+studentlist.get(0).get("student_cell_no"));
        }
    }

}
