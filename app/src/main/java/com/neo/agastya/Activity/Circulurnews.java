package com.neo.agastya.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.neo.agastya.Adapter.CirculerNewsAdapter;
import com.neo.agastya.Config.DBHelper;
import com.neo.agastya.HttpConnection.HttpHandler;
import com.neo.agastya.Model.Newscirculer;
import com.neo.agastya.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Circulurnews extends AppCompatActivity {
    RecyclerView recyclerView;
    //a list to store all the products
    List<Newscirculer> newsList;

    DBHelper dbHelper;
    String id,na,pa;

    String student_id;
    String student_name;
    String class_sec;
    ArrayList<HashMap<String, Object>> studentlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circulurnews);
        dbHelper=new DBHelper(this);
        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        student_id = intent.getStringExtra("student_id");

        Cursor res = dbHelper.GetSQLiteDatabaseRecords();
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


        new Getcirculer().execute();



       /* //initializing the productlist
        newsList = new ArrayList<>();





        //adding some items to our list
        newsList.add(
                new Newscirculer(
                        "1",
                        "Apple MacBook Air Core i5 5th Gen - (8 GB/128 GB SSD/Mac OS Sierra)",
                        "13.3 inch, Silver, 1.35 kg",
                        "4.3",
                        "https:\\/\\/thenavodayaacademy.edu.in\\/admins\\/news\\/118DSCN1207.JPG"
                     ));

        newsList.add(
                new Newscirculer(
                        "2",
                        "Dell Inspiron 7000 Core i5 7th Gen - (8 GB/1 TB HDD/Windows 10 Home)",
                        "14 inch, Gray, 1.659 kg",
                        "4.3",
                        "https:\\/\\/thenavodayaacademy.edu.in\\/admins\\/news\\/118DSCN1207.JPG"
                        ));

        newsList.add(
                new Newscirculer(
                        "3",
                        "Microsoft Surface Pro 4 Core m3 6th Gen - (4 GB/128 GB SSD/Windows 10)",
                        "13.3 inch, Silver, 1.35 kg",
                        "4.3",
                        "https:\\/\\/thenavodayaacademy.edu.in\\/admins\\/news\\/118DSCN1207.JPG"));

        //creating recyclerview adapter
        CirculerNewsAdapter adapter = new CirculerNewsAdapter(this, newsList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);*/

    }



    private class Getcirculer extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            newsList = new ArrayList<>();
//            Toast.makeText(Student_details.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://neoinfotech.com/neoerp/agastya/api/circular.php?student_id="+student_id;
            String jsonStr = sh.makeServiceCall(url);

            Log.e("", "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray studentsjson = jsonObj.getJSONArray("circular");

                    // looping through All Contacts
                    for (int i = 0; i < studentsjson.length(); i++) {
                        JSONObject c = studentsjson.getJSONObject(i);
                        String date = c.getString("date");
                        String title = c.getString("title");
                        String circular_message = c.getString("circular_message");
                        String link = c.getString("link");
                        String image = c.getString("image");

                        newsList.add(
                                new Newscirculer(
                                        ""+date,
                                        ""+title,
                                        ""+circular_message,
                                        ""+link,
                                        ""+image,
                                         R.drawable.eye
                                ));

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

            CirculerNewsAdapter adapter = new CirculerNewsAdapter(getApplicationContext(),newsList);

            recyclerView.setAdapter(adapter);


        }
    }




}
