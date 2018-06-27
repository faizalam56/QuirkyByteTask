package com.example.faiz.quirkybytetask;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.faiz.quirkybytetask.fragment.DemoPostFragment;

import java.util.List;

/**
 * Created by faiz on 24/6/18.
 */

public class RvAdapterPost extends RecyclerView.Adapter<RvAdapterPost.ViewHolderPost> {

    private Context context;
    private List<DemoPost> demoPostList;
    private DemoPostFragment demoPostFragment;
    public RvAdapterPost(Context context, List<DemoPost> demoPostList, DemoPostFragment demoPostFragment){
        this.context = context;
        this.demoPostList = demoPostList;
        this.demoPostFragment = demoPostFragment;
    }
    @NonNull
    @Override
    public ViewHolderPost onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_rv_post, parent, false);
        return new ViewHolderPost(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPost holder, int position) {
        DemoPost postItem = demoPostList.get(position);
        RvAdapterPostList rvAdapterPostList = new RvAdapterPostList(context,postItem,demoPostFragment);
        holder.mRvPostList.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        holder.mRvPostList.setAdapter(rvAdapterPostList);
    }

    @Override
    public int getItemCount() {
        return demoPostList!=null?demoPostList.size():0;
    }

    public void updateData(List<DemoPost> demoPostList) {
        this.demoPostList = demoPostList;
        notifyDataSetChanged();
    }

    class ViewHolderPost extends RecyclerView.ViewHolder{

        RecyclerView mRvPostList;
        public ViewHolderPost(View itemView) {
            super(itemView);
            mRvPostList = itemView.findViewById(R.id.rv_post_list);
        }
    }
}
