package test.com.farabiapp.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sathya on 24/08/16.
 */
public class AnnouncementData {

    @SerializedName("ANNOUNCEMENT_TITLE")
    private AnnouncementTitleModel announcementTitleModel;

    @SerializedName("ANNOUNCEMENT_IMAGE")
    private AnnouncementImageModel announcementImageModel;

    @SerializedName("ANNOUNCEMENT_HTML")
    private AnnouncementHtmlModel announcementHtmlModel;

    public AnnouncementTitleModel getAnnouncementTitleModel() {
        return announcementTitleModel;
    }

    public void setAnnouncementTitleModel(AnnouncementTitleModel announcementTitleModel) {
        this.announcementTitleModel = announcementTitleModel;
    }

    public AnnouncementImageModel getAnnouncementImageModel() {
        return announcementImageModel;
    }

    public void setAnnouncementImageModel(AnnouncementImageModel announcementImageModel) {
        this.announcementImageModel = announcementImageModel;
    }

    public AnnouncementHtmlModel getAnnouncementHtmlModel() {
        return announcementHtmlModel;
    }

    public void setAnnouncementHtmlModel(AnnouncementHtmlModel announcementHtmlModel) {
        this.announcementHtmlModel = announcementHtmlModel;
    }
}
