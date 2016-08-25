package test.com.farabiapp.api;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import test.com.farabiapp.Constants;

/**
 * Created by sathya on 24/08/16.
 */
public class WebUtils {

    private static final int TIMEOUT_CONNECTION = 25000;
    private static final int TIMEOUT_READ = 150000;

    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } else {
            return false;
        }
    }

    public static String fetchResponse(Context context, String url) throws APIException {

        String response = null;

        if (!isNetworkAvailable(context)) {
            return response;
        }

        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        builder.url(url);

        Request request = builder.build();
        try {

            Response responseObject = client.newCall(request).execute();
            response = responseObject.body().string();

        } catch (SocketTimeoutException e) {
            Log.e(Constants.LOG_TAG, "Timed out", e);
            throw new APIException(Constants.SERVER_TIMEDOUT_MESSAGE, Constants.TYPE_TIMEOUT);
        } catch (javax.net.ssl.SSLException e) {
            Log.e(Constants.LOG_TAG, "Interrupted.", e);
            throw new APIException(Constants.SERVER_TIMEDOUT_MESSAGE, Constants.TYPE_TIMEOUT);
        } catch (ConnectException e) {
            Log.e(Constants.LOG_TAG, "IOException during secure post.", e);
            throw new APIException(Constants.NO_INTERNET_MESSAGE, Constants.TYPE_NO_INTERNET);
        } catch (IOException e) {
            Log.e(Constants.LOG_TAG, "IOException during secure post.", e);
            throw new APIException(Constants.NO_INTERNET_MESSAGE, Constants.TYPE_NO_INTERNET);
        }

        return response;
    }

}
