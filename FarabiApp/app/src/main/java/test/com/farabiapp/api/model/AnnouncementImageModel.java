package test.com.farabiapp.api.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sathya on 24/08/16.
 */
public class AnnouncementImageModel implements Serializable {

    @SerializedName("Value")
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
