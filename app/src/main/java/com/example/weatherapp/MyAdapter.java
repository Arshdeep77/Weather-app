package com.example.weatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<ViewHolder> {

ArrayList<Data> MyList;
String cel="\u2103";
    public MyAdapter(ArrayList<Data> list) {

    MyList=new ArrayList<>();
    MyList=list;
    }

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
Data obj=MyList.get(position);
String text,image,time;
text=obj.txt;
image=obj.img;
time=obj.time;
int Temp=Math.round(Float.parseFloat(text));

holder.time.setText(time);
holder.tv.setText(Temp+cel);
        Picasso.get().load(image).into(holder.img);



    }

    @Override
    public int getItemCount() {
        return MyList.size();
    }
}
class ViewHolder extends RecyclerView.ViewHolder{
    ImageView img;
    TextView tv;
    TextView time;
    public ViewHolder(View itemView) {
        super(itemView);

        img=itemView.findViewById(R.id.Eicon);
        tv=itemView.findViewById(R.id.info);

    time=itemView.findViewById(R.id.time);
    }
}
