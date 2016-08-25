package test.com.farabiapp.api.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sathya on 24/08/16.
 */
public class AnnouncementHtmlModel implements Serializable {

    @SerializedName("Value")
    private String html;

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}