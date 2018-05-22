package com.teaching.android.miprimeraapp.webview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.*;
import android.support.v7.widget.Toolbar;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.teaching.android.miprimeraapp.Interactors.FilmsInteractor;
import com.teaching.android.miprimeraapp.Model.FilmModel;
import com.teaching.android.miprimeraapp.R;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView webView = findViewById(R.id.activity_web_view);
        //Para forzar que el contenido se abra en nuestra activity WebView
        // webView.setWebViewClient(new WebViewClient());
        int stringResourceId = getIntent().getIntExtra("url",0);
        String url = getString(stringResourceId);
        webView.loadUrl(url);

    }
}
