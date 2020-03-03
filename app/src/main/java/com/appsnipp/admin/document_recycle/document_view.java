package com.appsnipp.admin.document_recycle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.appsnipp.admin.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.util.List;

public class document_view extends AppCompatActivity  {
PDFView pdfView;
    private int pageNumber = 0;

    private String pdfFileName;

    public ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_view);
        Intent i=getIntent();
        String s=i.getStringExtra("dec_name");

        WebView webView = (WebView) findViewById(R.id.pdfview);
      webView.setWebViewClient(new WebViewClient());
      webView.loadUrl("http://docs.google.com/gview?embedded=true&url="+s);
      WebSettings se=webView.getSettings();
       se.setJavaScriptEnabled(true);
    }
}
