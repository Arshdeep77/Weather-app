package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener , GestureDetector.OnGestureListener {
ConstraintLayout layout;
float curPos,alert_pos;

ImageView Weather_icon;
LinearLayout alert;
    String cel;
ArrayList<apiData> demo,


        TodayData;
TextView TodayTemp,
    LocationTv,
    Dis_tv;
EditText locEnter;
    ArrayList<Data> list;
    TextView minMaxTv,dateTv;
    private RecyclerView rcv;
GestureDetector gestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


layout=findViewById(R.id.parent_layout);
locEnter=findViewById(R.id.EnterTv);
Weather_icon=findViewById(R.id.weather_icon);

curPos=locEnter.getY();
set_gesture();
TodayTemp=findViewById(R.id.TempTv);
LocationTv=findViewById(R.id.loc);
Dis_tv=findViewById(R.id.des);
alert=findViewById(R.id.alert_constraint);
dateTv=findViewById(R.id.date);
minMaxTv=findViewById(R.id.min_max);
        int mWidth= this.getResources().getDisplayMetrics().widthPixels;
        int mHeight= this.getResources().getDisplayMetrics().heightPixels;
        alert_pos=mHeight/2;

        char cel1=0x00B0;
         cel=cel1+""+"C";
list=new ArrayList<>();

        String city_name="Amritsar";

Call_and_Load_Data(city_name);














        rcv=findViewById(R.id.rcV);



        rcv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

    }

    private void Call_and_Load_Data(String city_name) {
        demo=new ArrayList<>();
        TodayData=new ArrayList<>();


        String url="http://api.openweathermap.org/data/2.5/forecast?id=524901&appid=dfbf2ccf9c503e779137a5a13c4405e2&q="+city_name+"&cnt=7&units=metric";
        String url2="http://api.openweathermap.org/data/2.5/weather?appid=dfbf2ccf9c503e779137a5a13c4405e2&q="+city_name+"&units=metric";

        Load_Data(url, new MY_call() {
            @Override
            public void My_response(ArrayList<apiData> demoData) {
                demo=demoData;
                afterLoad(demo);

            }
        },1);

        Load_Data2(url2, new MY_call() {
            @Override
            public void My_response(ArrayList<apiData> demoData) {
                TodayData=demoData;
                update_today(TodayData.get(0));
            }
        },2);

    }

    private void set_gesture() {
        layout.setOnTouchListener(this);
        gestureDetector=new GestureDetector(this,this);
    }

    private void update_today(apiData apiData) {


        int data=Math.round(Float.parseFloat(apiData.temp));
        String temperature=data+cel;
        TodayTemp.setText(temperature);
        LocationTv.setText(apiData.location);
        Dis_tv.setText(apiData.dis);

        String Current_date=gen_cur_date(apiData.ts);
        dateTv.setText(Current_date);

String icon_url=icon_url_gen(apiData.icon.id);
        Picasso.get().load(icon_url).into(Weather_icon);




    }

    private String gen_cur_date(String timeStamp) {
        long ts=Long.parseLong(timeStamp)*1000;
        SimpleDateFormat format=new SimpleDateFormat("EEE, dd MMM");
        String time=format.format(new Date(ts));
        return time;
    }

    private int Round(String mintem) {

        int te=Math.round(Float.parseFloat(mintem));
        return te;

    }

    private String icon_url_gen(String id) {

        String imgUrl="http://openweathermap.org/img/wn/"+id+"@2x.png";
        return imgUrl;
    }


    private void Load_Data2(String url,MY_call callback,int code) {

        ArrayList<apiData> dataList=new ArrayList<>();
        RequestQueue queue=Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url,null,

                data-> {
                    try{

                      //dt
                        RiseSet tp;

                        String time;
                        String date;

                        //weather
                        String dis;

                        //main
                        String temp;
                        String maxtem,mintem;
                        String humidity;


String loca=data.getString("name");


JSONObject sys=data.getJSONObject("sys");
Locale locale=new Locale("",sys.getString("country"));

loca=loca+" - "+locale.getDisplayCountry();

tp=new RiseSet(sys.getString("sunset"),sys.getString("sunrise"));
JSONObject mains= (JSONObject) data.get("main");
                            JSONArray weather=data.getJSONArray("weather");

                            String ts=data.getString("dt");

                            //main block
                            temp=mains.getString("temp");
                            maxtem=mains.getString("temp_max");
                            mintem=mains.getString("temp_min");
                            humidity=mains.getString("humidity");

                            //weather

                            dis=weather.getJSONObject(0).getString("description");
                            String icon_id=weather.getJSONObject(0).getString("icon");


                            time=stamp_to_time(Long.parseLong(ts)*1000);

                            apiData sData=new apiData(time,ts,dis,temp,maxtem,mintem,humidity,loca,tp,new Icon(icon_id));
                            dataList.add(sData);



                        callback.My_response(dataList);







                    } catch (Exception e) {
                        try {
                            String mes = data.getString("message");

                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();

                        }



                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                  Animation_alert();

                    }
                }
        );
        queue.add(jsonObjectRequest);

    }

    private void Animation_alert() {
        alert.setY(alert_pos);

alert.setAlpha(1);
alert.setVisibility(View.VISIBLE);

  ViewPropertyAnimator b=alert.animate().y(-1000).setStartDelay(3000).setDuration(3050);
b.start();



    }


    private void Load_Data(String url,MY_call callback,int code) {

ArrayList<apiData> dataList=new ArrayList<>();
        RequestQueue queue=Volley.newRequestQueue(this);

JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url,null,

        response -> {
try{

JSONArray data=response.getJSONArray("list");
   //dt
    String time;
    String date;

    //weather
    String dis;

    //main
    String temp;
    String maxtem,mintem;
    String humidity;

    for(int i=0;i<data.length();i++){
        JSONObject mains= (JSONObject) data.getJSONObject(i).get("main");
       JSONArray weather=data.getJSONObject(i).getJSONArray("weather");
       String icon_id=weather.getJSONObject(0).getString("icon");

        String ts=data.getJSONObject(i).getString("dt");

        //main block
       temp=mains.getString("temp");
       maxtem=mains.getString("temp_max");
        mintem=mains.getString("temp_min");
        humidity=mains.getString("humidity");

        //weather

        dis=weather.getJSONObject(0).getString("description");


        time=stamp_to_time(Long.parseLong(ts)*1000);

        apiData sData=new apiData(time,ts,dis,temp,maxtem,mintem,humidity,new Icon(icon_id));
       dataList.add(sData);



    }
    callback.My_response(dataList);







} catch (Exception e) {


e.printStackTrace();
}
        },

        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
);
queue.add(jsonObjectRequest);

    }

    private void afterLoad(ArrayList<apiData> ApiData) {
        list.clear();
        int low,high;
        low=Integer.MAX_VALUE;
        high=Integer.MIN_VALUE;
        for(apiData i:ApiData){
            String icon_url=icon_url_gen(i.icon.id);
            Data d=new Data(i.temp,icon_url ,i.time);
            low=Math.min((Round(i.mintem)),low);
            high=Math.max(high,Round(i.maxtem));

            list.add(d);
        }
apiData mdata=ApiData.get(0);
        String min_max=low+cel+"-"+high+cel;
        minMaxTv.setText(min_max);
        MyAdapter adapter=new MyAdapter(list);

        rcv.setAdapter(adapter);
        rcv.getAdapter().notifyDataSetChanged();


    }



    String stamp_to_time(long ts){
        SimpleDateFormat format=new SimpleDateFormat("hh:mm a");
        String time=format.format(new Date(ts));
        return time;

    }




    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {

        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
      if(v1<0.0){
          String city_name=locEnter.getText().toString();
locEnter.animate().translationY(-1000).setDuration(990);

if(city_name!="" && city_name!=null && city_name.length()!=0){
    Call_and_Load_Data(city_name);
    locEnter.getText().clear();
    closeKeyboard();
}

      }else{

         locEnter.animate().translationY(curPos).setDuration(700);
      }
        return false;
    }

    private void closeKeyboard()
    {
        // this will give us the view
        // which is currently focus
        // in this layout
        View view = this.getCurrentFocus();

        // if nothing is currently
        // focus then this will protect
        // the app from crash
        if (view != null) {

            // now assign the system
            // service to InputMethodManager
            InputMethodManager manager
                    = (InputMethodManager)
                    getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            manager
                    .hideSoftInputFromWindow(
                            view.getWindowToken(), 0);
        }
    }

}


