package com.example.pushnotificationassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {
    RequestQueue requestQueue;
    String email ;
    String password;
    String token;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        email =getIntent().getStringExtra("emailL");
        password= getIntent().getStringExtra("passwordL");
        getRegToken();


    }
    private void getRegToken(){

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.e("TAG Home", "Failed to get Token:" + task.getException());
                }else {
                    token = task.getResult();
                    Submit(token);

                    Log.e("TAG Home", "Token:" + token);

                }
            }
        });

    }

    private void Submit(String token) {
        String URL="https://mcc-users-api.herokuapp.com/add_reg_token";
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        Log.d("TAG", "requestQueue: "+requestQueue);
        StringRequest stringRequest=new StringRequest(Request.Method.PUT, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject objres = new JSONObject(response);
                    Log.d("TAGHome", "onResponse: "+objres.toString());

                } catch (JSONException e) {
                    Log.d("TAGHome", "Server Error ");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: "+error);
            }
        })
        {
            @Override

            protected Map<String,String> getParams() throws AuthFailureError{
                HashMap<String,String> map = new HashMap<>();
                map.put("emailL",email);
                map.put("passwordL",password);
                map.put("regToken", token);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }



}