package com.example.dubaipractical.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.dubaipractical.R;


public class AlertDialogUtility {

    public static void showInternetAlert(Context context) {
        new AlertDialog.Builder(context).setIcon(0).setTitle(Constant.CHECK_INTENET_TITLE).setMessage(Constant.CHECK_INTERNET)
                .setCancelable(true).setNeutralButton("OK", null).show();
    }

    public static void customAlert(Context context, String title, String message, String Positive_text,
                                   String Negative_text,
                                   DialogInterface.OnClickListener PositiveListener,
                                   DialogInterface.OnClickListener NegativeListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(title).setMessage(message).setPositiveButton(Positive_text, PositiveListener).setNegativeButton(Negative_text, NegativeListener);
        AlertDialog dialog = builder.create();
        if( ! dialog.isShowing())
        dialog.show();
    }

    public static void showConfirmAlert(Context context, String msg, DialogInterface.OnClickListener onYesClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(context.getString(R.string.app_name)).setMessage(msg).setPositiveButton("Yes", onYesClick).setNegativeButton("No", null);
        AlertDialog dialog = builder.create();
        if( ! dialog.isShowing()){
            dialog.show();
        }
    }

    public static void showConfirmAlertWithTitle(Context context,String title, String msg, DialogInterface.OnClickListener onYesClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(title).setMessage(msg).setPositiveButton("Ok", onYesClick);
        AlertDialog dialog = builder.create();
        if( ! dialog.isShowing()){
            dialog.show();
        }
    }

    public static void showSingleAlert(Context context, String strMessege, DialogInterface.OnClickListener onYesClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(context.getString(R.string.app_name));
        builder.setMessage(strMessege);
        builder.setPositiveButton("Yes", onYesClick);
        AlertDialog dialog = builder.create();
        if( ! dialog.isShowing()){
            dialog.show();
        }
    }
}
