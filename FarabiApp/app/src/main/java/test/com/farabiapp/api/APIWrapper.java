package test.com.farabiapp.api;

import android.content.Context;

/**
 * Created by sathya on 24/08/16.
 */
public class APIWrapper {

    public static String fetchAnnouncementList(Context context, String url) throws APIException {
        String response = WebUtils.fetchResponse(context, url);
        return response;
    }

}
