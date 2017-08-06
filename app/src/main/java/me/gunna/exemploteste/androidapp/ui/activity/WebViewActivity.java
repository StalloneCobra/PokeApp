package me.gunna.exemploteste.androidapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import me.gunna.exemploteste.androidapp.R;
import me.gunna.exemploteste.androidapp.databinding.ActivityWebviewBinding;
import me.gunna.exemploteste.androidapp.helper.Utils;

/**
 * Created by root on 05/08/17.
 */

public class WebViewActivity extends BaseActivity<ActivityWebviewBinding> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_webview);
        setupToolbar();
        setupWebView(mViewBinding.webView);
    }

    private void setupToolbar() {
        setUpToolbar(mViewBinding.toolbarBinding.toolbar);
        setTitle(getString(R.string.title_product_list));

        mViewBinding.toolbarBinding.toolbar
                .setNavigationOnClickListener((view) -> onBackPressed()
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.isOnline(this))
            mViewBinding.webView
                .loadUrl("https://www.amazon.com.br/s/ref=nb_sb_noss_2?__mk_pt_BR=%C3%85M%C3%85%C5%BD%C3%95%C3%91&url=search-alias%3Daps&field-keywords=livros&rh=i%3Aaps%2Ck%3Alivros");
        else
            Snackbar.make(mViewBinding.getRoot(),
                    R.string.error_internet,
                    BaseTransientBottomBar.LENGTH_LONG).show();
    }

    private void setupWebView(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view,request,error);
                mViewBinding.webView.reload();
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                mViewBinding.webView.reload();
            }
        });
    }


    public static void start(Context context) {
        Intent   intent = new Intent(context,WebViewActivity.class);
        context.startActivity(intent);
    }
}
