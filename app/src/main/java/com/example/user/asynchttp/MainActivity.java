package com.example.user.asynchttp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    AsyncHttpClient client;
    JSONArray jarray;
    JSONObject jobject;
    RequestParams params;
    ListView lv;
    Button submit;
    ArrayList<String> hspt_name;
    ArrayList<String>place;
    ArrayList<String> phone;
    ArrayList<String>email;
    ArrayList<String>fax;
    ArrayList<String>reg;
    ArrayList<String>type;
//    String latitude="22.574864";
//    String longitudes="88.437915";
String temp,tem;
    //	String url="http://sicsglobal.co.in/StudentManagementSystem/API/StudViewProf.aspx?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //prof=(EditText)findViewById(R.id.userProfile);
        client = new AsyncHttpClient();
        params = new RequestParams();
        //	submit=(Button)findViewById(R.id.submit);
        lv = (ListView) findViewById(R.id.nearhlist);
        hspt_name = new ArrayList<String>();
        place = new ArrayList<String>();
        email = new ArrayList<String>();
        phone = new ArrayList<String>();
        fax = new ArrayList<String>();
        reg = new ArrayList<String>();
        type = new ArrayList<String>();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
        temp=pref.getString("current_longitude","");
        Log.e("fffffffffffg",temp);

        tem=pref.getString("current_latitude","");
        Log.e("fffffffffffg",tem);

//        params.put("lattitude",tem);
//        params.put("longitude",temp);


        client.get("http://srishti-systems.info/projects/accident/viewallhospital.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                // TODO Auto-generated method stub
                super.onSuccess(content);
                Log.e(content,"content");

                try {
                    jobject = new JSONObject(content);
                    Log.e(content,"content");

                    String s = jobject.getString("Result");
                    Log.e(s,"RRRRRRRR");

                    if(s.equals("success")){

                        jarray =jobject.getJSONArray("HospitalDetails");
                        for (int i = 0; i < jarray.length(); i++) {
                            JSONObject obj = jarray.getJSONObject(i);

                            String plc = obj.getString("place");
                            place.add("Place:" + plc);
                            Log.e(plc, "fghfgfgf");
                            String phn = obj.getString("phone");
                            phone.add("Phone:" + phn);
//                            SharedPreferences pref=getApplicationContext().getSharedPreferences("pref",MODE_PRIVATE);
//                            SharedPreferences.Editor editor=pref.edit();
//                            editor.putString("msg",phn);
//                            editor.apply();
                            Log.e(phn, "fghfgfgf");

                            String mail = obj.getString("email_id");
                            email.add("Email:" + mail);

                            String fx = obj.getString("fax");
                            fax.add("Fax:" + fx);

                            String regis = obj.getString("reg.no");
                            reg.add("Reg.No :" + regis);

                            String typ = obj.getString("type");
                            type.add("Type :" + typ);

                        }
                    }
                    adapter adpt = new adapter();
                    lv.setAdapter(adpt);
                } catch (Exception e) {

                }


            }
        });

    }


    class adapter extends BaseAdapter {
        LayoutInflater Inflater;
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return hspt_name.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            Inflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=Inflater.inflate(R.layout.near_hsptl_list,null);
            Viewholder holder=new Viewholder();

            holder.name=(TextView)convertView.findViewById(R.id.near_hsptl_name);
            holder.name.setText(hspt_name.get(position));

            holder.plce=(TextView)convertView.findViewById(R.id.near_hsptl_place);
            holder.plce.setText(place.get(position));

            holder.em=(TextView)convertView.findViewById(R.id.near_hsptl_mail);
            holder.em.setText(email.get(position));

            holder.ph=(TextView)convertView.findViewById(R.id.near_hsptl_phn);
            holder.ph.setText(phone.get(position));

            holder.f=(TextView)convertView.findViewById(R.id.near_hsptl_fax);
            holder.f.setText(fax.get(position));

            holder.rg=(TextView)convertView.findViewById(R.id.near_hsptl_No);
            holder.rg.setText(reg.get(position));

            holder.tp=(TextView)convertView.findViewById(R.id.near_hsptl_type);
            holder.tp.setText(type.get(position));


            return convertView;
        }
        class Viewholder{
            TextView name;
            TextView plce;
            TextView em;
            TextView ph;
            TextView f;
            TextView rg;
            TextView tp;


        }
    }

}

