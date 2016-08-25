package test.com.farabiapp.views.core.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import test.com.farabiapp.R;

/**
 * Created by sathya on 24/08/16.
 */
public class FragmentProgress extends Fragment {

    private TextView progressText;
    private String message;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        progressText = (TextView) view.findViewById(R.id.progress_text);

        updateProgressMessage(message);

        return view;
    }

    public void updateProgressMessage(String message) {
        // Guard against crash.
        if (progressText != null && !TextUtils.isEmpty(message)) {
            progressText.setText(message);
        }
    }

    public void setProgressMessage(String progressMessage) {
        this.message = progressMessage;
    }
}
