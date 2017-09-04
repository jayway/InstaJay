package com.jayway.instajay;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class NavigationUtil {

    public static final String MAIN_ACTIVITY_URL = "https://www.jayway.com";
    public static final String MAIN_ACTIVITY_EVENTS_URL = "https://www.jayway.com/events";

    public void startMainActivity() {


    }

    public static void startAppLink(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setPackage(context.getPackageName());
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        context.startActivity(intent);
    }
}
