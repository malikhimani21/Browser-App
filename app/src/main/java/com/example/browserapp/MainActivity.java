package com.example.browserapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextUrl;
    WebView webView;
    Button go, back, forward, refresh, history;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        editTextUrl = findViewById(R.id.editTextUrl);
        webView = findViewById(R.id.webView);
        go = findViewById(R.id.buttonGo);
        back = findViewById(R.id.buttonBack);
        forward = findViewById(R.id.buttonForward);
        refresh = findViewById(R.id.buttonRefresh);
        history = findViewById(R.id.buttonHistory);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        go.setOnClickListener(this);
        back.setOnClickListener(this);
        forward.setOnClickListener(this);
        refresh.setOnClickListener(this);
        history.setOnClickListener(this);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewHandler());

        try {
            webView.loadUrl("https://www.google.co.in/");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            progressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonGo:
                String stringUrl = editTextUrl.getText().toString().trim();
                webView.loadUrl("https:www." + stringUrl);

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(editTextUrl.getWindowToken(), 0);
                break;

            case R.id.buttonBack:
                if (webView.canGoBack()) {
                    webView.goBack();
                }
                Toast.makeText(getApplicationContext(), "Back", Toast.LENGTH_LONG).show();
                break;

            case R.id.buttonForward:
                if (webView.canGoForward()) {
                    webView.goForward();
                }
                Toast.makeText(getApplicationContext(), "Forwarding", Toast.LENGTH_LONG).show();
                break;

            case R.id.buttonRefresh:
                webView.reload();
                Toast.makeText(getApplicationContext(), "Refreshing Page", Toast.LENGTH_LONG).show();
                break;

            case R.id.buttonHistory:
                webView.clearHistory();
                Toast.makeText(getApplicationContext(), "History Cleared", Toast.LENGTH_LONG).show();
                break;
        }
    }

    public class WebViewHandler extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String string) {
            view.loadUrl(string);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Please confirm");
        builder.setMessage("Do you want to exit...?");
        builder.setCancelable(true);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // MainActivity.super.onBackPressed();
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "You're online", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
