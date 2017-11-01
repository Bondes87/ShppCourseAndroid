package com.dbondarenko.shpp.cookislands.fragments;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.dbondarenko.shpp.cookislands.Constants;
import com.dbondarenko.shpp.cookislands.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PageFragment extends Fragment {

    private static final String LOG_TAG = PageFragment.class.getSimpleName();

    @BindView(R.id.webViewPage)
    WebView webViewPage;
    @BindView(R.id.progressBarPageLoading)
    ProgressBar progressBarPageLoading;

    private String pageUrl;

    public static PageFragment newInstance(String pageUrl) {
        Log.d(LOG_TAG, "newInstance()");
        PageFragment pageFragment = new PageFragment();
        Bundle arguments = new Bundle();
        arguments.putString(Constants.KEY_PAGE_URL, pageUrl);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        setRetainInstance(true);
        pageUrl = getArguments().getString(Constants.KEY_PAGE_URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView()");
        View viewContent = inflater.inflate(R.layout.fragment_page, container, false);
        ButterKnife.bind(this, viewContent);
        webViewPage.getSettings().setUserAgentString(Constants.USER_AGENT_STRING);
        webViewPage.loadUrl(pageUrl);
        webViewPage.setWebViewClient(getWebViewClient());
        return viewContent;
    }

    private WebViewClient getWebViewClient() {
        return new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return !pageUrl.equalsIgnoreCase(url);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return !pageUrl.equalsIgnoreCase(request.getUrl().toString());
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBarPageLoading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBarPageLoading.setVisibility(View.GONE);
            }
        };
    }
}