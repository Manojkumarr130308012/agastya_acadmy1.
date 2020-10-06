package com.neo.agastya.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.neo.agastya.Activity.Viewcirculer;
import com.neo.agastya.Model.Newscirculer;
import com.neo.agastya.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CirculerNewsAdapter extends RecyclerView.Adapter<CirculerNewsAdapter.ProductViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Newscirculer> newsList;
    Newscirculer news;
    String title;
    String subtitle;
    String date;
    String link;
    String image;

    //getting the context and product list with constructor
    public CirculerNewsAdapter(Context mCtx, List<Newscirculer> newsList) {
        this.mCtx = mCtx;
        this.newsList = newsList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.newslistview, null);



        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {
        //getting the product of the specified position
        news = newsList.get(position);
        //binding the data with the viewholder views
        holder.textViewTitle.setText(news.getTitle());
        holder.textViewShortDesc.setText(news.getMessage());
        holder.textViewRating.setText(news.getDate());
        holder.textViewPrice.setText(news.getLink());
        holder.imageeye.setImageDrawable(mCtx.getResources().getDrawable(news.getImageeye()));
        Picasso.get().load(""+news.getImage()).into(holder.imageView);

        holder.imageeye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                news = newsList.get(position);
                Intent i=new Intent(mCtx,Viewcirculer.class);
                Log.e("cccccccccccccccccccc",""+title);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                i.putExtra("student_title",""+news.getTitle());
                i.putExtra("subtitle", ""+news.getMessage());
                i.putExtra("date", ""+news.getDate());
                i.putExtra("link", ""+news.getLink());
                i.putExtra("image", ""+news.getImage());

                Toast.makeText(mCtx, ""+news.getImage(), Toast.LENGTH_SHORT).show();
                mCtx.getApplicationContext().startActivity(i);
            }
        });

    }


    @Override
    public int getItemCount() {
        return newsList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
        ImageView imageView,imageeye;


        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = itemView.findViewById(R.id.date);
            textViewPrice = itemView.findViewById(R.id.link);
            imageView = itemView.findViewById(R.id.imageView);
            imageeye = itemView.findViewById(R.id.eye);

        }


    }
}
