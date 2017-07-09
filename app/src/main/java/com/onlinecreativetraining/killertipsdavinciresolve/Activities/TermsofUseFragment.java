package com.onlinecreativetraining.killertipsdavinciresolve.Activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import com.onlinecreativetraining.killertipsdavinciresolve.R;

public class TermsofUseFragment extends Fragment{

    public final static int TERMS_OF_USE = 1;
    public final static int TERMS_OF_PRIVACY_POLICY = 2;
    public final static int TERMS_OF_REFUND_POLICY = 3;
    View view;
    int pageType;

    public static TermsofUseFragment newInstance(int pageType){
        TermsofUseFragment fragment = new TermsofUseFragment();
        fragment.pageType = pageType;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainActivity.searchView.setVisibility(View.GONE);
        MainActivity.txtTitle.setVisibility(View.VISIBLE);
        MainActivity.footer.setVisibility(View.GONE);
        if(view != null) return view;
        view = inflater.inflate(R.layout.termsofuse_layout, container, false);
        WebView webView = (WebView) view.findViewById(R.id.termsofuse_webview);
        MainActivity.logo_img.setVisibility(View.GONE);

        switch (pageType) {
            case TERMS_OF_USE:
                MainActivity.txtTitle.setText("Terms Of Use");
                webView.loadUrl("file:///android_res/raw/termsofuse.html");
                break;
            case TERMS_OF_PRIVACY_POLICY:
                MainActivity.txtTitle.setText("Privacy Policy");
                webView.loadUrl("file:///android_res/raw/privacy_policy.html");
                break;
            case TERMS_OF_REFUND_POLICY:
                MainActivity.txtTitle.setText("Refund Policy");
                webView.loadUrl("file:///android_res/raw/refund_policy.html");
                break;
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainActivity.logo_img.setVisibility(View.VISIBLE);
        MainActivity.searchView.setVisibility(View.VISIBLE);
        MainActivity.txtTitle.setVisibility(View.GONE);
        MainActivity.footer.setVisibility(View.VISIBLE);
    }
}
