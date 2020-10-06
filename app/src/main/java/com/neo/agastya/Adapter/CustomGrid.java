package com.neo.agastya.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.neo.agastya.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class  CustomGrid extends BaseAdapter {
    private Context mContext;

    private final ArrayList<HashMap<String, Object>> studentlist;

    public CustomGrid(Context c, ArrayList<HashMap<String, Object>> studentlist) {
        mContext = c;
        this.studentlist = studentlist;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return studentlist.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.studentlist_content, null);
            TextView textView = (TextView) grid.findViewById(R.id.cash);
            TextView textView1= (TextView) grid.findViewById(R.id.textName);
            CircleImageView imageView = (CircleImageView) grid.findViewById(R.id.imageView);



                textView.setText(""+studentlist.get(position).get("class_sec"));
                textView1.setText(""+studentlist.get(position).get("student_name"));
//                imageView.setImageResource(Imageid[position]);
                Picasso.get().load(""+studentlist.get(position).get("student_photo")).into(imageView);



        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}