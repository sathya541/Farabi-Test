package test.com.farabiapp.api.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sathya on 24/08/16.
 */
public class AnnouncementTitleModel implements Serializable {

    @SerializedName("Value")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}