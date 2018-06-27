package com.example.faiz.quirkybytetask.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.faiz.quirkybytetask.DemoPost;
import com.example.faiz.quirkybytetask.R;
import com.example.faiz.quirkybytetask.RvAdapterPost;
import com.example.faiz.quirkybytetask.network.ApiClient;
import com.example.faiz.quirkybytetask.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by faiz on 25/6/18.
 */

public class DemoPostFragment extends Fragment {
    View view;
    ApiInterface apiInterface;
    RecyclerView mRecyclerViewPost;
    RvAdapterPost adapterPost;
    List<DemoPost> demoPostList;
    Context context;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_demo_post,container,false);
        init();
        return view;
    }

    private void init(){
        context = getActivity();
        mRecyclerViewPost = view.findViewById(R.id.rv_post);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        adapterPost = new RvAdapterPost(context,demoPostList,this);
        mRecyclerViewPost.setLayoutManager(layoutManager);
        mRecyclerViewPost.setAdapter(adapterPost);

        callDemoPostApi();
    }

    private void callDemoPostApi(){
        Call<List<DemoPost>> call = apiInterface.demoPostRequest();
        call.enqueue(new Callback<List<DemoPost>>() {
            @Override
            public void onResponse(Call<List<DemoPost>> call, Response<List<DemoPost>> response) {
                if(response!=null && response.body()!=null){
                    demoPostList = response.body();
                    adapterPost.updateData(demoPostList);
                }
            }

            @Override
            public void onFailure(Call<List<DemoPost>> call, Throwable t) {

            }
        });
    }
}
