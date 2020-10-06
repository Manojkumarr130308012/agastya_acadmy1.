package com.neo.agastya.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.charts.Pie;
import com.neo.agastya.Config.DBHelper;
import com.neo.agastya.HttpConnection.HttpHandler;
import com.neo.agastya.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Attendance extends AppCompatActivity {
    CompactCalendarView compactCalendar, compactCalendar1;
    DBHelper dbHelper;
    String id,na,pa;
    ArrayList<HashMap<String,String>> studentlist;

    String currentMonth;
    String currentyear;
    String Status;
    java.util.Date date1, date12;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM-yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatMonth1 = new SimpleDateFormat("MM", Locale.getDefault());

    String Studentdate,student_id;
    String Studentstateus;

    String lite_blue="#42A5F5";
    String Dark_blue="#1565C0";
    String Orange="#F4511E";
    String yellow="#FBC02D";
    String darkblackblue="#1A237E";
    String gray="#9E9E9E";
    String green="#2E7D32";
    String red="#C62828";
    TextView Month;

    int present=0,absent=0,leave=0;
    TableLayout stk1;

    Pie pie;
    AnyChartView anyChartView;

    TextView textview_pie;
    List<DataEntry> piedata;
    TableLayout stk;

    ProgressBar progressBar_cyclic;
    View customLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        dbHelper=new DBHelper(this);
        progressBar_cyclic=findViewById(R.id.progressBar_cyclic);
        Intent intent = getIntent();
        student_id = intent.getStringExtra("student_id");
        Cursor res = dbHelper.GetSQLiteDatabaseRecords();
//        imageView=findViewById(R.id.profile_image);
        while (res.moveToNext()) {
            id = res.getString(0);
            na = res.getString(1);
            pa = res.getString(2);
        }

        Log.e("id",""+id);
        Log.e("employee user",""+na);
        Log.e("employee id",""+pa);


        compactCalendar = findViewById(R.id.compactcalendar_view);
        Month = findViewById(R.id.Month);
        Month.setText(dateFormatMonth.format(compactCalendar.getFirstDayOfCurrentMonth()));

        compactCalendar.setUseThreeLetterAbbreviation(true);

        compactCalendar.setUseThreeLetterAbbreviation(true);
        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());

        currentMonth = String.valueOf(localCalendar.get(Calendar.MONTH) + 1);
        currentyear= String.valueOf(localCalendar.get(Calendar.YEAR));
        dbHelper=new DBHelper(this);

        /*anyChartView = findViewById(R.id.any_chart_view);
        pie = AnyChart.pie();*/

        new GetstudentsAttendance().execute();




        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getApplicationContext();
                String pattern = "yyyy-MM-dd";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String date = simpleDateFormat.format(dateClicked);
                Log.e("t5yteyhtryry", "" + date);
            }


            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                studentlist=null;
             /*   piedata.clear();
               anyChartView.clear();*/
                Month.setText(dateFormatMonth.format(firstDayOfNewMonth));
                currentMonth = dateFormatMonth1.format(firstDayOfNewMonth);
                compactCalendar.removeAllEvents();
                new GetstudentsAttendance().execute();
            }

        });










    }

    private class GetstudentsAttendance extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            studentlist = new ArrayList<>();
            piedata = new ArrayList<>();
            progressBar_cyclic.setVisibility(View.VISIBLE);
//            Toast.makeText(Student_details.this,"Json Data is downloading",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://neoinfotech.com/neoerp/agastya/api/get_attendance.php?student_id="+student_id+"&month="+currentMonth+"&year="+currentyear;
            String jsonStr = sh.makeServiceCall(url);

            Log.e("", "Response from url: " + jsonStr);


            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray studentsjson = jsonObj.getJSONArray("attendance");

                    // looping through All Contacts
                    for (int i = 0; i < studentsjson.length(); i++) {
                        JSONObject c = studentsjson.getJSONObject(i);
                        String student_date = c.getString("date");
                        String student_status = c.getString("attendance_status");
                        HashMap<String,String> students = new HashMap<>();
                        students.put("date",student_date);
                        students.put("student_status",student_status);
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

            present = 0;
            absent = 0;
            leave = 0;

            stk = findViewById(R.id.table_main);
            // Clear Previous Data
            stk.removeAllViewsInLayout();

            for (int i = 0; i < studentlist.size(); i++) {
                Log.e("Errrrror", "" + studentlist.get(i).get("date"));
                Log.e("Errrrror", "" + studentlist.get(i).get("student_status"));
                Studentdate = studentlist.get(i).get("date");
                Studentstateus = studentlist.get(i).get("student_status");
                Log.e("88528", "" + Studentdate);

                if (Studentstateus.equals("0")) {
                    try {
                        date1 = new SimpleDateFormat("yyyy-MM-dd").parse(Studentdate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long Milli = date1.getTime();
                    Event ev1 = new Event(Color.parseColor(yellow), Milli, Studentstateus);
                    compactCalendar.addEvent(ev1);
                    leave = leave + 1;
                } else if (Studentstateus.equals("1")) {
                    try {
                        date1 = new SimpleDateFormat("yyyy-MM-dd").parse(Studentdate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Log.e("Errrrror", "" + date1.getTime() + "L");
                    long Milli = date1.getTime();
                    Event ev2 = new Event(Color.parseColor(green), Milli, Studentstateus);
                    compactCalendar.addEvent(ev2);
                    present = present + 1;
                } else if (Studentstateus.equals("2")) {
                    try {
                        date1 = new SimpleDateFormat("yyyy-MM-dd").parse(Studentdate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Log.e("Errrrror", "" + date1.getTime() + "L");
                    long Milli = date1.getTime();
                    Event ev3 = new Event(Color.parseColor(red), Milli, Studentstateus);
                    compactCalendar.addEvent(ev3);
                    absent = absent + 1;
                }
            }


            final TableRow tbrow0 = new TableRow(getApplicationContext());


            final TextView tv1 = new TextView(getApplicationContext());
            tv1.setText("            Present           ");
            tv1.setTypeface(null, Typeface.BOLD);
            tv1.setPadding(5, 5, 5, 5);
            tv1.setGravity(Gravity.CENTER);
            tv1.setBackgroundResource(R.drawable.cell_shape);
            tv1.setBackgroundColor(getResources().getColor(R.color.md_green_900));
//            tv1.setTextColor(Color.WHITE);
            tv1.setTextColor(Color.WHITE);
            tbrow0.addView(tv1);


            final TextView tv2 = new TextView(getApplicationContext());
            tv2.setText("              Absent             ");
            tv2.setTypeface(null, Typeface.BOLD);
            tv2.setPadding(5, 5, 5, 5);
            tv2.setGravity(Gravity.CENTER);
            tv2.setBackgroundResource(R.drawable.cell_shape);
            tv2.setBackgroundColor(getResources().getColor(R.color.red_dark));
//        2   tv1.setTextColor(Color.WHITE);
            tv2.setTextColor(Color.WHITE);
            tbrow0.addView(tv2);


            final TextView tv4 = new TextView(getApplicationContext());
            tv4.setText("            Leave            ");
            tv4.setTypeface(null, Typeface.BOLD);
            tv4.setPadding(5, 5, 5, 5);
            tv4.setGravity(Gravity.CENTER);
            tv4.setBackgroundResource(R.drawable.cell_shape);
            tv4.setBackgroundColor(getResources().getColor(R.color.yellow));
//      tv4   tv1.setTextColor(Color.WHITE);
            tv4.setTextColor(Color.WHITE);
            tbrow0.addView(tv4);
            stk.addView(tbrow0);


            TableRow tbrow = new TableRow(getApplicationContext());




            TextView t4v = new TextView(getApplicationContext());
            t4v.setText(""+present);
            t4v.setPadding(5, 5, 5, 5);
            t4v.setBackgroundResource(R.drawable.cell_shape);
            t4v.setTextColor(Color.BLACK);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);

            TextView t2v = new TextView(getApplicationContext());
            t2v.setText(""+absent);
            t2v.setPadding(5, 5, 5, 5);
            t2v.setBackgroundResource(R.drawable.cell_shape);

//          t2v   t4v.setTextColor(Color.WHITE);
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);



            TextView t3v = new TextView(getApplicationContext());
            t3v.setText(""+leave);
            t3v.setPadding(5, 5, 5, 5);
            t3v.setBackgroundResource(R.drawable.cell_shape);

//          t3v   t4v.setTextColor(Color.WHITE);
            t3v.setTextColor(Color.BLACK);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);


            stk.addView(tbrow);





            progressBar_cyclic.setVisibility(View.GONE);

            t2v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Attendance.this, ""+tv2.getText().toString(), Toast.LENGTH_SHORT).show();
                    Status=""+tv2.getText().toString().trim();
                    showAlertDialogButtonClicked(v);
                }
            });



            t4v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Attendance.this, ""+tv1.getText().toString(), Toast.LENGTH_SHORT).show();
                    Status=""+tv1.getText().toString().trim();
                    showAlertDialogButtonClicked(v);
                }
            });


            t3v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Attendance.this, ""+tv4.getText().toString(), Toast.LENGTH_SHORT).show();
                    Status=""+tv4.getText().toString().trim();
                    showAlertDialogButtonClicked(v);
                }
            });

        }
    }
    public void showAlertDialogButtonClicked(View view) {
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Attendance Details");
        // set the custom layout
        customLayout = getLayoutInflater().inflate(R.layout.custom_layout, null);
        builder.setView(customLayout);
        stk1=customLayout.findViewById(R.id.table_main1);
//        stk1.removeAllViewsInLayout();
        final TableRow tbrow0 = new TableRow(getApplicationContext());


        final TextView tv1 = new TextView(getApplicationContext());
        tv1.setText("                Date               ");
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setPadding(5, 5, 5, 5);
        tv1.setGravity(Gravity.CENTER);
        tv1.setBackgroundResource(R.drawable.cell_shape);
//        tv1.setBackgroundColor(getResources().getColor(R.color.md_green_900));
//            tv1.setTextColor(Color.WHITE);
        tv1.setTextColor(Color.BLACK);
        tbrow0.addView(tv1);


        final TextView tv2 = new TextView(getApplicationContext());
        tv2.setText("               Status              ");
        tv2.setTypeface(null, Typeface.BOLD);
        tv2.setPadding(5, 5, 5, 5);
        tv2.setGravity(Gravity.CENTER);
        tv2.setBackgroundResource(R.drawable.cell_shape);
//        tv2.setBackgroundColor(getResources().getColor(R.color.red_dark));
//        2   tv1.setTextColor(Color.WHITE);
        tv2.setTextColor(Color.BLACK);
        tbrow0.addView(tv2);
        stk1.addView(tbrow0);

            if (Status.equals("Present"))
            {
                for (int j = 0; j < studentlist.size(); j++) {
                    Log.e("Errrrror", "" + studentlist.get(j).get("date"));
                    Log.e("Errrrror", "" + studentlist.get(j).get("student_status"));
                    Studentdate = studentlist.get(j).get("date");
                    Studentstateus = studentlist.get(j).get("student_status");
                    Log.e("88528", "" + Studentdate);

                    if (Studentstateus.equals("1")) {
                        try {
                            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(Studentdate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Log.e("Errrrror", "" + date1.getTime() + "L");
                        long Milli = date1.getTime();
                        Event ev2 = new Event(Color.parseColor(green), Milli, Studentstateus);
                        compactCalendar.addEvent(ev2);
                        present = present + 1;
                        TableRow tbrow = new TableRow(getApplicationContext());

                        TextView t4v = new TextView(getApplicationContext());
                        t4v.setText(""+Studentdate);
                        t4v.setPadding(5, 5, 5, 5);
                        t4v.setBackgroundResource(R.drawable.cell_shape);
                        t4v.setTextColor(Color.BLACK);
                        t4v.setGravity(Gravity.CENTER);
                        tbrow.addView(t4v);

                        TextView t2v = new TextView(getApplicationContext());
                        t2v.setText("Present");
                        t2v.setPadding(5, 5, 5, 5);
                        t2v.setBackgroundResource(R.drawable.cell_shape);
                        t2v.setTextColor(Color.BLACK);
                        t2v.setGravity(Gravity.CENTER);
                        tbrow.addView(t2v);

                        stk1.addView(tbrow);
                    }


                }



            }
            else if(Status.equals("Absent")){

                for (int k = 0; k < studentlist.size(); k++) {
                    Log.e("Errrrror", "" + studentlist.get(k).get("date"));
                    Log.e("Errrrror", "" + studentlist.get(k).get("student_status"));
                    Studentdate = studentlist.get(k).get("date");
                    Studentstateus = studentlist.get(k).get("student_status");
                    Log.e("88528", "" + Studentdate);
                    if (Studentstateus.equals("2")) {
                        try {
                            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(Studentdate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Log.e("Errrrror", "" + date1.getTime() + "L");
                        long Milli = date1.getTime();
                        Event ev3 = new Event(Color.parseColor(red), Milli, Studentstateus);
                        compactCalendar.addEvent(ev3);
                        absent = absent + 1;
                        TableRow tbrow = new TableRow(getApplicationContext());

                        TextView t4v = new TextView(getApplicationContext());
                        t4v.setText(""+Studentdate);
                        t4v.setPadding(5, 5, 5, 5);
                        t4v.setBackgroundResource(R.drawable.cell_shape);
                        t4v.setTextColor(Color.BLACK);
                        t4v.setGravity(Gravity.CENTER);
                        tbrow.addView(t4v);

                        TextView t2v = new TextView(getApplicationContext());
                        t2v.setText("Absent");
                        t2v.setPadding(5, 5, 5, 5);
                        t2v.setBackgroundResource(R.drawable.cell_shape);

//          t2v   t4v.setTextColor(Color.WHITE);
                        t2v.setTextColor(Color.BLACK);
                        t2v.setGravity(Gravity.CENTER);
                        tbrow.addView(t2v);

                        stk1.addView(tbrow);
                    }




                }


            }
            else if (Status.equals("Leave")){

                for (int l = 0; l < studentlist.size(); l++) {
                    Log.e("Errrrror", "" + studentlist.get(l).get("date"));
                    Log.e("Errrrror", "" + studentlist.get(l).get("student_status"));
                    Studentdate = studentlist.get(l).get("date");
                    Studentstateus = studentlist.get(l).get("student_status");
                    Log.e("88528", "" + Studentdate);
                    if (Studentstateus.equals("0")) {
                        try {
                            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(Studentdate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        long Milli = date1.getTime();
                        Event ev1 = new Event(Color.parseColor(yellow), Milli, Studentstateus);
                        compactCalendar.addEvent(ev1);
                        leave = leave + 1;
                        TableRow tbrow = new TableRow(getApplicationContext());

                        TextView t4v = new TextView(getApplicationContext());
                        t4v.setText(""+Studentdate);
                        t4v.setPadding(5, 5, 5, 5);
                        t4v.setBackgroundResource(R.drawable.cell_shape);
                        t4v.setTextColor(Color.BLACK);
                        t4v.setGravity(Gravity.CENTER);
                        tbrow.addView(t4v);

                        TextView t2v = new TextView(getApplicationContext());
                        t2v.setText("Leave");
                        t2v.setPadding(5, 5, 5, 5);
                        t2v.setBackgroundResource(R.drawable.cell_shape);

//          t2v   t4v.setTextColor(Color.WHITE);
                        t2v.setTextColor(Color.BLACK);
                        t2v.setGravity(Gravity.CENTER);
                        tbrow.addView(t2v);

                        stk1.addView(tbrow);
                    }

                }

}

        // add a button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
             /*   // send data from the AlertDialog to the Activity
                EditText editText = customLayout.findViewById(R.id.editText);
                sendDialogDataToActivity(editText.getText().toString());*/
             dialog.dismiss();
            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    // do something with the data coming from the AlertDialog
    private void sendDialogDataToActivity(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }

}
