package test.com.farabiapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;

/**
 * Created by sathya on 24/08/16.
 */
public class UIHelper {

    public static AlertDialog showErrorDialog(Context context, String errorMessage) {
        if (context == null) {
            return null;
        }

        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return null;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.popup_theme));
        builder.setTitle("Error").setMessage(errorMessage).setIcon(R.mipmap.ic_action_alert_warning).setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

        return dialog;
    }

    public static void showInfoDialog(Context context, String message, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.popup_theme));

        builder.setTitle(title).setMessage(message).setIcon(R.mipmap.ic_action_action_info_outline).setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }
}
