package com.example.faiz.quirkybytetask.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.faiz.quirkybytetask.R;

/**
 * Created by faiz on 26/6/18.
 */

public class FragmentWebView extends Fragment {
    ProgressBar progressBar;
    private WebView webView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        Bundle b = getArguments();
        String url = b.getString("postlink");

        webView = (WebView) view.findViewById(R.id.webview);
        progressBar.setVisibility(View.VISIBLE);
        WebSettings webSetting = webView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDisplayZoomControls(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });
        return view;
    }
}
