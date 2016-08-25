package test.com.farabiapp.api.manager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import test.com.farabiapp.Constants;
import test.com.farabiapp.UIHelper;
import test.com.farabiapp.api.APIException;
import test.com.farabiapp.api.WebUtils;
import test.com.farabiapp.api.model.AnnouncementData;
import test.com.farabiapp.views.core.ICoreFlow;

/**
 * Created by sathya on 24/08/16.
 */
public class DataManager {

    private static DataManager instance;
    private ICoreFlow flowImpl;
    private Context context;

    private DataManager() {
    }

    public static DataManager getInstance(ICoreFlow flowImpl, Context context) {
        if (instance == null) {
            instance = new DataManager();
        }

        instance.flowImpl = flowImpl;
        instance.context = context;

        return instance;
    }

    public void fetchAnnouncementData(final Context context, final String url, final DataCallback listener) {

        new AsyncTask<Void, String, String>() {
            APIException e;

            @Override
            protected void onPreExecute() {
                if (!WebUtils.isNetworkAvailable(context)) {
                    listener.onError();
                    UIHelper.showErrorDialog(context, Constants.NO_INERNET_ERROR_MESSAGE);
                } else {
                    flowImpl.showProgress("Fetchig Announcement Details");
                }
            }

            @Override
            protected String doInBackground(Void... params) {

                String response = "";
                try {
                    response = WebUtils.fetchResponse(context, url);
                } catch (APIException e) {
                    this.e = e;
                }
                return response;
            }

            @Override
            protected void onPostExecute(String response) {
                flowImpl.endProgress();

                if (e == null) {

                    parseResponse(response, listener);

                } else {

                    switch (e.getType()) {
                    case Constants.TYPE_NO_INTERNET:
                        UIHelper.showErrorDialog(context, Constants.NO_INERNET_ERROR_MESSAGE);
                        break;
                    case Constants.TYPE_TIMEOUT:
                        UIHelper.showErrorDialog(context, Constants.SERVER_TIMEDOUT_MESSAGE);
                        break;
                    default:
                        UIHelper.showErrorDialog(context, e.getErrorMessage());
                        break;
                    }
                }

            }

        }.execute();

    }

    private void parseResponse(String response, DataCallback listener) {
        if (response != null) {

            try {

                Gson gson = new Gson();

                ArrayList<AnnouncementData> dataArrayList = new ArrayList<>();
                if (!TextUtils.isEmpty(response)) {
                    try {
                        Type collectionType = new TypeToken<Collection<AnnouncementData>>() {
                        }.getType();
                        Collection<AnnouncementData> models = gson.fromJson(response, collectionType);
                        dataArrayList.addAll(models);
                    } catch (JsonSyntaxException err) {
                        Log.e(Constants.LOG_TAG, err.toString());
                    }
                }

                //Fix
                for(int i = 0; i< dataArrayList.size(); i++){
                    String imgUrl = dataArrayList.get(i).getAnnouncementImageModel().getImageUrl();
                    dataArrayList.get(i).getAnnouncementImageModel().setImageUrl(replaceUrlPrefix(imgUrl));
                }


                if (dataArrayList != null) {
                    listener.onCompleted(dataArrayList);
                } else {
                    listener.onError();
                }

            } catch (IllegalStateException | JsonSyntaxException jError) {
                listener.onError();
                Log.e(Constants.LOG_TAG, "Error while parseing announcement data " + jError);
            }

        }
    }

    public String replaceUrlPrefix(String imageUrl) {
        imageUrl = imageUrl.replaceFirst("HTTP://", "http://");
        return imageUrl;
    }

    public interface DataCallback<AnnouncementData> {
        void onCompleted(ArrayList<AnnouncementData> modelArrayList);

        void onError();
    }

}
