package com.example.faiz.quirkybytetask.network;

import com.example.faiz.quirkybytetask.DemoPost;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by faiz on 25/6/18.
 */

public interface ApiInterface {
    @GET("/demo.json")
    Call<List<DemoPost>> demoPostRequest();
}
