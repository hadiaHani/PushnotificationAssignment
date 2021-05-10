package com.example.pushnotificationassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MainActivity2 extends AppCompatActivity {
    EditText email,password;
    RequestQueue requestQueue;
    Button login_button;
    TextView new_account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        email = findViewById(R.id.login_email);
        new_account=findViewById(R.id.new_account);
        password = findViewById(R.id.login_password);
        login_button = findViewById(R.id.login_button);

        new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this,
                        MainActivity.class ));
            }
        });
        login_button.setOnClickListener(new View.OnClickListener() {


          @Override
            public void onClick(View v) {

                String data = "{" +
                        "\"email\"" + ":" + "\"" + email.getText().toString() + "\"," +
                        "\"password\"" + ":" + "\"" + password.getText().toString() + "\"" +
                        "}";

                Submit(data);

                startActivity(new Intent(MainActivity2.this,
                        Home.class ));
            }
        });
    }

    private void Submit(String data) {
        String savedata=data;
        String URL="https://mcc-users-api.herokuapp.com/login";
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        Log.d("TAG", "requestQueue: "+requestQueue);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject objres = new JSONObject(response);
                    Log.d("TAG", "onResponse: "+objres.toString());
                } catch (JSONException e) {
                    Log.d("TAG", "Server Error ");
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
            public String getBodyContentType(){return "application/json; charset=utf-8";}
            @Override
            public byte[] getBody() throws AuthFailureError {
                try{
                    Log.d("TAG", "savedata: "+savedata);
                    return savedata==null?null:savedata.getBytes("utf-8");
                }catch(UnsupportedEncodingException uee){
                    return null;
                }
            }
        };
        requestQueue.add(stringRequest);





    }
}