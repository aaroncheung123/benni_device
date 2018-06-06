package com.aaroncheung.client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    String TAG = "debug_123";

    String URL = "http://192.168.1.144:3000/api/todos/test";
    String POST_URL = "http://192.168.1.144:3000/api/todo";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.d(TAG, "onCreate");
        requestQueue = Volley.newRequestQueue(this);

    }

    public void getRequestClick(View view){

        Log.d(TAG, "getRequest");
        JsonArrayRequest objectRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            JSONObject jsonObject = response.getJSONObject(1);
                            Log.d(TAG, jsonObject.toString());
                            Log.d(TAG, "GET Success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
                    }
                }
        );

        Toast.makeText(this, "GET",
                Toast.LENGTH_LONG).show();
        requestQueue.add(objectRequest);
    }

    public void postRequestClick(View view){
        Log.d(TAG, "postRequest");
        JSONObject jsonBodyPost = new JSONObject();
        try {
            jsonBodyPost.put("todo", "cartwheels");
            jsonBodyPost.put("isDone","false");
            jsonBodyPost.put("hasAttachment","false");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest postReq = new JsonObjectRequest(Request.Method.POST,
                POST_URL, jsonBodyPost,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        Log.d(TAG, "POST Success");
                        //Success Callback

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
                        //Failure Callback

                    }
                });

        Toast.makeText(this, "POST",
                Toast.LENGTH_LONG).show();

        requestQueue.add(postReq);
    }


    public void deleteRequestClick(View view){
        Log.d(TAG, "deleteRequest");

//        JSONObject jsonBodyDelete = new JSONObject();
//        try {
//            jsonBodyDelete.put("id", "5b15cfbf796e640b09d07d29");
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


        StringRequest deleteReq = new StringRequest(Request.Method.DELETE, POST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
            }
        }){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null){
                    responseString = String.valueOf(response.statusCode);
                }
                //return super.parseNetworkResponse(response);
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };


                Toast.makeText(this, "DELETE",
                        Toast.LENGTH_LONG).show();

        requestQueue.add(deleteReq);
    }
}
