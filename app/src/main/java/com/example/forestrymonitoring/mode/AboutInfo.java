package com.example.forestrymonitoring.mode;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

import com.example.forestrymonitoring.R;

/**
 * Created by 吐槽星人 on 2017/10/22 0022.
 */

public class AboutInfo {
    // 绘制关于界面Dialog
    public static void displayAboutDialog(Context context) {
        final int paddingSizeDp = 5;
        final float scale = context.getResources().getDisplayMetrics().density;
        final int dpAsPixels = (int) (paddingSizeDp * scale + 0.5f);

        final TextView textView = new TextView(context);
        final SpannableString text = new SpannableString(context.getString(R.string.aboutText));

        textView.setText(text);
        textView.setAutoLinkMask(Activity.RESULT_OK);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);

        Linkify.addLinks(text, Linkify.ALL);
        new AlertDialog.Builder(context)//AlertDialog
                .setTitle(R.string.menu_about)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, null)
                .setView(textView)
                .show();
    }
}
