package com.aaroncheung.client;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpRequest {

    String TAG = "debug_123";

    String URL = "http://192.168.1.144:3000/api/todos/test";
    String POST_URL = "http://192.168.1.144:3000/api/todo";
    RequestQueue requestQueue;


    public void sendGetRequest(){

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

        requestQueue.add(objectRequest);
    }

    public void sendPostRequest(){
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

        requestQueue.add(postReq);
    }


}
