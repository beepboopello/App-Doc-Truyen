package com.example.appdoctruyen.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdoctruyen.R;
import com.example.appdoctruyen.SQLite.ServerInfo;
import com.example.appdoctruyen.model.Genre;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class SpinnerAdapter extends BaseAdapter {
    private String [] listGenre;

    public void setListGenre(String[] listGenre) {
        this.listGenre = listGenre;
        notifyDataSetChanged();
    }

    private Context context;

    public SpinnerAdapter(Context context) {
        this.context = context;
        List<Genre> l=new ArrayList<>();
        ServerInfo serverInfo = new ServerInfo(context.getApplicationContext());

        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());

        String url = serverInfo.getUserServiceUrl()+"api/genre/";


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    JSONArray arr=object.getJSONArray("data");
                    for(int i=0;i<arr.length();i++){
                        JSONObject j= (JSONObject) arr.get(i);
                        //String id, String name, String description, String created_at, String update_at
                        Genre genre=new Genre(j.getString("id"),
                                j.getString("name"),
                                j.getString("description"),
                                j.getString("created_at"),
                                j.getString("updated_at"));
                        l.add(genre);
                    }

                } catch (JSONException ex) {
                    throw new RuntimeException(ex);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data, "UTF-8"));
                    System.out.println(jsonObject.getString("error"));
                    Toast.makeText(context.getApplicationContext(),jsonObject.getString("error"),Toast.LENGTH_LONG);
                } catch (UnsupportedEncodingException | JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }

        };
        queue.add(stringRequest);
        listGenre=new String[l.size()];
        for(int i=0;i<l.size();i++){
            listGenre[i]= String.valueOf(l.get(i).getName());
            System.out.println(l.get(i).getName());
        }
    }

    @Override
    public int getCount() {
        return listGenre.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View item= LayoutInflater.from(context).inflate(R.layout.item_spinner, viewGroup, false);
        TextView img=item.findViewById(R.id.item_spin);
        img.setText(listGenre[i]);
        return item;
    }
}
