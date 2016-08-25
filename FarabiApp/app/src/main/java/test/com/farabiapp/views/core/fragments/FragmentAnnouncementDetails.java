package test.com.farabiapp.views.core.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import test.com.farabiapp.R;
import test.com.farabiapp.views.core.CoreActivity;

/**
 * Created by sathya on 25/08/16.
 */
public class FragmentAnnouncementDetails extends AbstractCoreFragment {

    private Context mContext;
    private String htmlContent = "";
    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_announcement_detail, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            htmlContent = bundle.getString("html", "");
        }

        initialize(view);

        loadDetailView();

        return view;
    }

    private void loadDetailView() {
        webView.loadDataWithBaseURL("x-data://base", htmlContent, "text/html", "UTF-8", null);
    }

    private void initialize(View view) {

        if (mContext != null) {
            if (mContext instanceof CoreActivity) {
                ((CoreActivity) mContext).setupToolbar(false, true, true, false);
                ((CoreActivity) mContext).setToolBarTitle("Announcement Details");
            }
        }

        webView = (WebView) view.findViewById(R.id.web_view);

        setUpWebView();
    }

    private void setUpWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mContext = context;
    }

    @Override
    public void onDetach() {

        mContext = null;
        super.onDetach();
    }

}
