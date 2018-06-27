package com.example.faiz.quirkybytetask.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.faiz.quirkybytetask.DemoPost;
import com.example.faiz.quirkybytetask.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by faiz on 25/6/18.
 */

public class DemoPostDescriptionFragment extends Fragment {
    private View view;
    private ImageView mImageView;
    private WebView mWebview;
    private TextView mTextView1,mTextView2,mTextView3,mTvContent,mTvContentHtml,mTextViewDescription,mTextViewTime;
    private Bundle bundle;
    private Date currentDate;
    private int dayToday, monthToday, yearToday;
    private int hourToday, minuteToday, secondToday;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentDate = new Date(System.currentTimeMillis());
        Log.d("TimeVal", "current :" + currentDate.toString());
        dayToday = currentDate.getDate();
        monthToday = currentDate.getMonth();
        yearToday = currentDate.getYear();
        hourToday = currentDate.getHours();
        minuteToday = currentDate.getMinutes();
        secondToday = currentDate.getSeconds();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_post_description,container,false);
        init();
        return view;
    }

    private void init(){
        mImageView = view.findViewById(R.id.iv_image);
        mTextView1 = view.findViewById(R.id.tv_1);
        mTextView2 = view.findViewById(R.id.tv_2);
        mTextView3 = view.findViewById(R.id.tv_3);
        mTextViewTime = view.findViewById(R.id.tv_time);
        mTextViewDescription = view.findViewById(R.id.tv_description);
        mTvContent = view.findViewById(R.id.tv_content);
//        mTvContentHtml = view.findViewById(R.id.tv_content_html);
        mWebview = view.findViewById(R.id.wv_description);
        bundle = getArguments();
        if (bundle!=null){
            String transitionName = bundle.getString("transitionName");
            DemoPost demoPost = (DemoPost) bundle.getSerializable("postItem");
            int position = bundle.getInt("position");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mImageView.setTransitionName(transitionName);
            }

            if (demoPost != null) {
                ArrayList<DemoPost.PostMedia> postMedia = demoPost.postMedia;
                DemoPost.PostMedia movieImage = postMedia.get(position);
                if (!TextUtils.isEmpty(movieImage.MediaUrl)) {
                    Glide.with(getActivity()).load(movieImage.MediaUrl).apply(new RequestOptions().transforms(new CenterCrop())).into(mImageView);

                } else {
                    mImageView.setImageDrawable(null);
                }

                mTextView2.setText(demoPost.postAuthor);
                mTextView3.setText(demoPost.postTitle);
                mTextView1.setText(getTimeStamp(convertMillisecondToDate(demoPost.postDateTime)));
                mTextViewTime.setText(getTimeStamp(convertMillisecondToDate(demoPost.postDateTime)));
                mTextViewDescription.setText(demoPost.postDescription);

                String contentWithHtml = demoPost.postContent;
                String onlyContent=contentWithHtml.substring(0,contentWithHtml.indexOf("<")-1);
                String onlyHtml = contentWithHtml.substring(contentWithHtml.indexOf("<"),contentWithHtml.length());
                mTvContent.setText(onlyContent);

               /* if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                    mTvContentHtml.setText(Html.fromHtml(onlyHtml,Html.FROM_HTML_MODE_COMPACT));
                }else {
                    mTvContentHtml.setText(Html.fromHtml(onlyHtml));
                }*/

                String htmlString = "<html><body>"+onlyHtml+"</body></html>";
                mWebview.getSettings().setJavaScriptEnabled(true);
                mWebview.loadDataWithBaseURL("", htmlString, "text/html", "UTF-8", "");
            }
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
