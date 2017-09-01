package com.huxin.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;

import com.afollestad.materialdialogs.MaterialDialog;
import com.huxin.view.HxPregressDialog;


public class DialogBaseUtils {
    /**
     * 只有确定的提示的dialog
     *
     * @param context
     * @param title
     * @param content
     */
    public static void showDialogTip(Context context, String title, String content) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveColor(Color.BLUE)
                .positiveText(android.R.string.yes)
                .show();

    }

    public static void showPremissionTip(Context context, String title, String content, MaterialDialog.SingleButtonCallback callback) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveColor(Color.BLUE)
                .positiveText(android.R.string.yes)
                .onPositive(callback)
                .cancelable(false)
                .show();

    }

    public static void showDialogTip(Context context, int titleRes, int contentRes) {
        new MaterialDialog.Builder(context)
                .title(titleRes)
                .content(contentRes)
                .positiveColor(Color.BLUE)
                .positiveText(android.R.string.yes)
                .show();

    }

    /**
     * @param context
     * @param title    不需要titll传null就可以
     * @param content
     * @param callback callback，确定和取消可以是同一个，返回中action进行判断据可以了
     */
    public static void showBasicDialog(Context context, String title, String content, MaterialDialog.SingleButtonCallback callback) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .negativeColor(Color.GRAY)
                .positiveColor(Color.BLUE)
                .positiveText("同意")
                .negativeText("取消")
                .onPositive(callback)
                .show();
    }

    public static void showBasicDialog(Context context, String title, String content, String posText, String negText, MaterialDialog.SingleButtonCallback callback) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .negativeColor(Color.GRAY)
                .positiveColor(Color.BLUE)
                .positiveText(posText)
                .negativeText(negText)
                .onPositive(callback)
                .onNegative(callback)
                .show();
    }

    public static void showBasicDialog(Context context, int title, int content, MaterialDialog.SingleButtonCallback callback) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .negativeColor(Color.GRAY)
                .positiveColor(Color.BLUE)
                .positiveText("同意")
                .negativeText("取消")
                .onPositive(callback)
                .show();
    }

    /**
     * 等待对话框
     *
     * @param context
     * @return
     */
    public static Dialog createProgressDialog(Context context) {
        return new HxPregressDialog.Builder(context)
                .create();

    }


    /**
     * 不能取消得等待对话框
     *
     * @param context
     * @return
     */
    public static Dialog createCanntCancelProgressDialog(Context context) {

        return new HxPregressDialog.Builder(context)
                .setCanCancelAble(false)
                .create();

    }
}
