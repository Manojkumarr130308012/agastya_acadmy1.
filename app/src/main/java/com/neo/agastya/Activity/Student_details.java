package com.neo.agastya.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.neo.agastya.Adapter.CustomGrid;
import com.neo.agastya.R;
import com.neo.agastya.Config.DBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import android.widget.ListView;

import com.neo.agastya.HttpConnection.HttpHandler;

public class Student_details extends AppCompatActivity {
    DBHelper dbHelper;
    String id,na,pa;
    ImageView imageView;
    String student_id;
    String student_name;
    String class_sec;
    ArrayList<HashMap<String, Object>> studentlist;
    private ListView lv;
    SharedPreferences sharedpreferences;
    String student_photo;
    GridView grid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        dbHelper=new DBHelper(this);

        Intent intent = getIntent();
        na = intent.getStringExtra("user");
        Log.e("feafefref",""+na);

        Cursor res = dbHelper.GetSQLiteDatabaseRecords();
        imageView=findViewById(R.id.profile_image);
        while (res.moveToNext()) {
            id = res.getString(0);
            na = res.getString(1);
            pa = res.getString(2);
        }

        Log.e("id",""+id);
        Log.e("employee user",""+na);
        Log.e("employee id",""+pa);
        studentlist = new ArrayList<>();
//        lv = (ListView) findViewById(R.id.list);


        new Getstudents().execute();
    }

    private class Getstudents extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            Toast.makeText(Student_details.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://neoinfotech.com/neoerp/agastya/api/students_list.php?login_mobile="+na;
            String jsonStr = sh.makeServiceCall(url);

            Log.e("", "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray studentsjson = jsonObj.getJSONArray("student_list");

                    // looping through All Contacts
                    for (int i = 0; i < studentsjson.length(); i++) {
                        JSONObject c = studentsjson.getJSONObject(i);
                        String student_id = c.getString("student_id");
                        String student_name = c.getString("student_name");
                         student_photo = c.getString("student_photo");
                        String class_sec = c.getString("class_sec");

                        HashMap<String,Object> students = new HashMap<>();

                        students.put("student_photo",student_photo);
                        students.put("class_sec", class_sec);
                        students.put("student_name", student_name);
                        students.put("student_id", student_id);

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
            CustomGrid adapter = new CustomGrid(Student_details.this,studentlist);
            grid=(GridView)findViewById(R.id.grid);
            grid.setAdapter(adapter);
            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                   Toast.makeText(Student_details.this, "You Clicked at "+studentlist.get(position).get("student_id"), Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(Student_details.this,Student_Profile.class);
                    i.putExtra("student_photo", ""+studentlist.get(position).get("student_photo"));
                    i.putExtra("class_sec", ""+studentlist.get(position).get("class_sec"));
                    i.putExtra("student_name", ""+studentlist.get(position).get("student_name"));
                    i.putExtra("student_id", ""+studentlist.get(position).get("student_id"));
                    startActivity(i);

                }
            });
        }
    }


}
