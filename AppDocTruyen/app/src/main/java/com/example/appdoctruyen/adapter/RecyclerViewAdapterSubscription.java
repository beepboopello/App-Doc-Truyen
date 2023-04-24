package com.example.appdoctruyen.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdoctruyen.Activity.SubscriptionActivity;
import com.example.appdoctruyen.R;
import com.example.appdoctruyen.SQLite.CurrentUser;
import com.example.appdoctruyen.SQLite.ServerInfo;
import com.example.appdoctruyen.model.Subscription;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerViewAdapterSubscription extends RecyclerView.Adapter<RecyclerViewAdapterSubscription.HomeViewHolder> {

    private List<Subscription> list;
    private Context context;

    private AppCompatActivity activity;
    ProgressDialog dialog;

    public RecyclerViewAdapterSubscription(Context context, AppCompatActivity activity) {
        list=new ArrayList<>();
        this.context = context;
        this.activity = activity;
    }

    public void setList(List<Subscription> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Subscription getItem(int posititon){
        return list.get(posititon);
    }
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subscription,parent,false);
        dialog =new ProgressDialog(context);
        dialog.setMessage("Đang chờ xử lý thanh toán");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        return new RecyclerViewAdapterSubscription.HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterSubscription.HomeViewHolder holder, int position) {
        Subscription subscription = list.get(position);
        holder.price.setText(String.valueOf(subscription.getPrice()));
        holder.months.setText(String.valueOf(subscription.getMonths()) + " tháng");
        holder.btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServerInfo serverInfo = new ServerInfo(context);

                RequestQueue queue = Volley.newRequestQueue(context);

                String url = serverInfo.getUserServiceUrl()+"api/subscribe/";

                Map<String,String> body = new HashMap<>();
                CurrentUser currentUser = new CurrentUser(context);
                body.put("userID", String.valueOf(currentUser.getCurrentUser().getId()));
                body.put("token", currentUser.getCurrentUser().getToken());
                body.put("months",String.valueOf(subscription.getMonths()));

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(serverInfo.getPaymentServiceUrl()
                                    +"payment"
                                    + "?amount="+String.valueOf(subscription.getPrice())
                                    +"&paymentID="+jsonObject.getString("id"))));
                            activity.finish();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data, "UTF-8"));
                            System.out.println(jsonObject.getString("error"));
                            Toast.makeText(context,jsonObject.getString("error"),Toast.LENGTH_SHORT);
                        } catch (UnsupportedEncodingException | JSONException e) {
                            throw new RuntimeException(e);
                        } catch (NullPointerException e){
                            System.out.println("Server is offline....");
                            Toast.makeText(context,"Server is offline....",Toast.LENGTH_SHORT);
                        }
                    }
                }){
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        return super.parseNetworkResponse(response);
                    }
                    protected Map<String,String> getParams(){
                        return body;
                    }
                };
                queue.add(stringRequest);
            }
        });


    }

    public void checkPayment(){
        while(true){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ServerInfo serverInfo = new ServerInfo(context);

            RequestQueue queue = Volley.newRequestQueue(context);

            String url = serverInfo.getUserServiceUrl()+"api/checkPayment/";

            Map<String,String> body = new HashMap<>();
            CurrentUser currentUser = new CurrentUser(context);
            body.put("userID",String.valueOf(currentUser.getCurrentUser().getId()));

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    return;
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data, "UTF-8"));
                        System.out.println(jsonObject.getString("error"));
                        Toast.makeText(context,jsonObject.getString("error"),Toast.LENGTH_SHORT);
                    } catch (UnsupportedEncodingException | JSONException e) {
                        throw new RuntimeException(e);
                    } catch (NullPointerException e){
                        System.out.println("Server is offline....");
                        Toast.makeText(context,"Server is offline....",Toast.LENGTH_SHORT);
                    }
                }
            }){
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    return super.parseNetworkResponse(response);
                }
                protected Map<String,String> getParams(){
                    return body;
                }
            };
            queue.add(stringRequest);
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder{
        TextView months, price;
        Button btnSubscribe;
        public HomeViewHolder(@NonNull View view) {
            super(view);
            months = view.findViewById(R.id.months);
            price = view.findViewById(R.id.price);
            btnSubscribe = view.findViewById(R.id.btnSubscribe);
        }


    }


}
