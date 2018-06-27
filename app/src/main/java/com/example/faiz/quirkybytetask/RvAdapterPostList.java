package com.example.faiz.quirkybytetask;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.faiz.quirkybytetask.fragment.DemoPostDescriptionFragment;
import com.example.faiz.quirkybytetask.fragment.DemoPostFragment;
import com.example.faiz.quirkybytetask.fragment.FragmentWebView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by faiz on 24/6/18.
 */

public class RvAdapterPostList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int Type_Image = 1;
    private static final int Type_Video = 2;
    private Context context;
    private DemoPost postItem;
    MainActivity mainActivity;
    DemoPostFragment currentFragment;
    private Date currentDate;
    private int dayToday, monthToday, yearToday;
    private int hourToday, minuteToday, secondToday;

    public RvAdapterPostList(Context context, DemoPost postItem, DemoPostFragment currentFragment){
        this.context = context;
        this.postItem = postItem;
        this.mainActivity = (MainActivity) context;
        this.currentFragment = currentFragment;

        currentDate = new Date(System.currentTimeMillis());
        dayToday = currentDate.getDate();
        monthToday = currentDate.getMonth();
        yearToday = currentDate.getYear();
        hourToday = currentDate.getHours();
        minuteToday = currentDate.getMinutes();
        secondToday = currentDate.getSeconds();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

           /* View itemVew = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_rv_post_list, parent, false);
            return new ViewHolderPostList(itemVew);*/

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case Type_Image:
                View itemView = inflater.inflate(R.layout.row_rv_post_list, parent, false);
                viewHolder = new ViewHolderPostList(itemView);
                break;
            case Type_Video:
                View loadingView = inflater.inflate(R.layout.row_rv_post_list_video_type,parent,false);
                viewHolder = new VHPostListVideo(loadingView);
                break;
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(postItem!=null) {

            switch (getItemViewType(position)){
                case Type_Image: {
                    final ViewHolderPostList holderPostList = (ViewHolderPostList) holder;

                    holderPostList.mTextView1.setText(getTimeStamp(convertMillisecondToDate(postItem.postDateTime)));
                    holderPostList.mTextView2.setText(postItem.postAuthor);
                    holderPostList.mTextView3.setText(postItem.postTitle);

                    ArrayList<DemoPost.PostMedia> postMedia = postItem.postMedia;
                    DemoPost.PostMedia movieImage = postMedia.get(position);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ((ViewHolderPostList) holder).mImageView.setTransitionName("transition" + position);
                    }
                    if (!TextUtils.isEmpty(movieImage.MediaUrl)) {
                        Glide.with(context).load(movieImage.MediaUrl).apply(new RequestOptions().transforms(new CenterCrop())).into(holderPostList.mImageView);

                    } else {
                        holderPostList.mImageView.setImageDrawable(null);
                    }

                    if (postItem.postIsArticle.equals("true")) {
                        holderPostList.mImageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DemoPostDescriptionFragment fragment = new DemoPostDescriptionFragment();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("postItem", postItem);
                                bundle.putString("transitionName", "transition" + position);
                                bundle.putInt("position", position);
                                fragment.setArguments(bundle);
                                mainActivity.showFragmentWithTransition(currentFragment, fragment, "postDetail", holderPostList.mImageView, "transition" + position);
                            }
                        });
                    } else {
                        holderPostList.mImageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FragmentWebView fragmentWebView = new FragmentWebView();
                                Bundle bundle = new Bundle();
                                bundle.putString("postlink", postItem.postLink);
                                fragmentWebView.setArguments(bundle);
                                mainActivity.showFragment(fragmentWebView, "link");
                            }
                        });
                    }
                }
                    break;

                case Type_Video:
                    VHPostListVideo holderVideo = (VHPostListVideo) holder;

                    holderVideo.mTextView1.setText(getTimeStamp(convertMillisecondToDate(postItem.postDateTime)));
                    holderVideo.mTextView2.setText(postItem.postAuthor);
                    holderVideo.mTextView3.setText(postItem.postTitle);

                    ArrayList<DemoPost.PostMedia> postMedia = postItem.postMedia;
                    final DemoPost.PostMedia movieImage = postMedia.get(position);

                    holderVideo.mIvVideo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FragmentWebView fragmentWebView = new FragmentWebView();
                            Bundle bundle = new Bundle();
                            bundle.putString("postlink", movieImage.MediaUrl);
                            fragmentWebView.setArguments(bundle);
                            mainActivity.showFragment(fragmentWebView, "link");
                        }
                    });
            }

        }
    }

    @Override
    public int getItemCount() {
        ArrayList<DemoPost.PostMedia> postMedia = postItem.postMedia;
        return postMedia!=null?postMedia.size():0;
    }

    @Override
    public int getItemViewType(int position) {
        ArrayList<DemoPost.PostMedia> postMedia = postItem.postMedia;
        DemoPost.PostMedia media =postMedia.get(position);
        if(media.MediaType.equalsIgnoreCase("Image")){
            return Type_Image;
        }else if(media.MediaType.equalsIgnoreCase("Video")){
            return Type_Video;
        }
        return -1;
    }

    ////***********************View Holder***********************************

    class ViewHolderPostList extends RecyclerView.ViewHolder{

        private TextView mTextView1,mTextView2,mTextView3;
        private ImageView mImageView;

        public ViewHolderPostList(View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.tv_1);
            mTextView2 = itemView.findViewById(R.id.tv_2);
            mTextView3 = itemView.findViewById(R.id.tv_3);
            mImageView = itemView.findViewById(R.id.iv_image);
        }
    }

    class VHPostListVideo extends RecyclerView.ViewHolder{

        private TextView mTextView1,mTextView2,mTextView3;
        private ImageView mIvVideo;
        public VHPostListVideo(View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.tv_1);
            mTextView2 = itemView.findViewById(R.id.tv_2);
            mTextView3 = itemView.findViewById(R.id.tv_3);
            mIvVideo = itemView.findViewById(R.id.iv_video);
        }
    }

    private String convertMillisecondToDate(String timeStamp){


        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        long milliSeconds= Long.parseLong(timeStamp);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    private String getTimeStamp(String time) {
        Log.d("TimeVal", "val :" + time);
//        String dateTime = "2013-11-12 13:14:15";
        SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

        Date date = new Date();
        try {
            date = dateParser.parse(time);
            Log.d("TimeVal", "val real :" + date.toString());
            if (date.getYear() == yearToday) {
                if (date.getMonth() == monthToday) {
                    if (date.getDate() == dayToday) {
                        if (date.getHours() == hourToday) {
                            if (date.getMinutes() == minuteToday) {
                                return "just now.";
                            } else {
                                return minuteToday - date.getMinutes() + " minutes ago";
                            }
                        } else {
                            return hourToday - date.getHours() + " hours ago";
                        }
                    } else {
                        return dayToday - date.getDate() + " days ago";
                    }
                } else {
                    return monthToday - date.getMonth() + " months ago";
                }
            } else {
                return yearToday - date.getYear() + " years ago";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.toString();
    }
}
