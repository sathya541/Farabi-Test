package test.com.farabiapp.views.core;

import java.io.Serializable;

/**
 * Created by sathya on 24/08/16.
 */
public interface ICoreFlow extends Serializable {

    public void onGetStarted();

    public void showProgress(String text);

    public void endProgress();

    public void onAnnouncementSelected(String htmlContent);

    void showToolbar();

    void hideToolbar();

}
