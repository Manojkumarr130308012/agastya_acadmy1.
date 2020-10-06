package com.neo.agastya.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.baoyachi.stepview.VerticalStepView;
import com.neo.agastya.Config.DBHelper;
import com.neo.agastya.HttpConnection.HttpHandler;
import com.neo.agastya.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Homework extends AppCompatActivity {
    List<String> list0;
    DBHelper dbHelper;
    String na;

    int time;

    String pa,student_id;
    ArrayList<HashMap<String, Object>> studentlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);
        dbHelper=new DBHelper(this);

        Intent intent = getIntent();
        student_id = intent.getStringExtra("student_id");
        dbHelper=new DBHelper(this);

        Cursor res = dbHelper.GetSQLiteDatabaseRecords();

        while (res.moveToNext()) {
//            id = res.getString(0);
            na = res.getString(1);
            pa = res.getString(2);

        }
        studentlist = new ArrayList<>();
        new gethomework().execute();
    }



    private class gethomework extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            Toast.makeText(Student_details.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://neoinfotech.com/neoerp/agastya/api/homeworks.php?student_id="+student_id;
            String jsonStr = sh.makeServiceCall(url);

            Log.e("", "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray studentsjson = jsonObj.getJSONArray("homework");

                    // looping through All Contacts
                    for (int i = 0; i < studentsjson.length(); i++) {
                        JSONObject c = studentsjson.getJSONObject(i);
                        String date = c.getString("date");
                        String homework = c.getString("homework");
                        HashMap<String,Object> students = new HashMap<>();
                        students.put("date",date);
                        students.put("homework", homework);
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
            Timeline();
        }
    }



    private void Timeline(){
        VerticalStepView mSetpview0 = (VerticalStepView) findViewById(R.id.step_view0);
        list0 = new ArrayList<>();
        Log.e("dfh1111111",""+list0.size());



        for (int i=0;i<studentlist.size();i++){

            String Loc=""+studentlist.get(i).get("date")+"\n"+studentlist.get(i).get("homework");
            list0.add(""+Loc);

        }


        Log.e("1234ggggggggg",""+list0.toString());

        mSetpview0.setStepsViewIndicatorComplectingPosition(list0.size())
                .reverseDraw(false)
                .setStepViewTexts(list0)
                .setLinePaddingProportion(3f)
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(getApplicationContext(), android.R.color.black))
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(getApplicationContext(), R.color.uncompleted_text_color))
                .setStepViewComplectedTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.black))
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(getApplicationContext(), R.color.uncompleted_text_color))
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.homework))
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_icon))
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.attention));


      /*  String[] fromwhere = {"Date_time", "Geo_loc"};
        int[] viewswhere = {R.id.lblID, R.id.lblcountryname};

        ADAhere = new SimpleAdapter(getApplicationContext(), MyData, R.layout.listtemplate, fromwhere, viewswhere);

        LV_Country.setAdapter(ADAhere);*/

    }

}
