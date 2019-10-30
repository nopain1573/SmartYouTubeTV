package com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers;

import android.app.Activity;
import android.webkit.CookieManager;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.SmartYouTubeTV4K;
import com.liskovsoft.smartyoutubetv.flavors.webview.SmartYouTubeTV1080Activity;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.AppStateWatcherBase.StateHandler;
import org.xwalk.core.XWalkCookieManager;

public class ForceOldUIHandler extends StateHandler {
    private static final String NEW_UI_COOKIE = "VISITOR_INFO1_LIVE=cp3UVuEA3l4; path=/; domain=.youtube.com; expires=Sat, 25-Apr-2020 15:42:26 GMT; httponly";
    private static final String OLD_UI_COOKIE = "VISITOR_INFO1_LIVE=ErVksiAQ6pg; path=/; domain=.youtube.com; expires=Sat, 25-Apr-2020 15:42:26 GMT; httponly";
    private static final String COOKIE_URL = "https://www.youtube.com";
    private static final String TAG = ForceOldUIHandler.class.getSimpleName();
    private final boolean mUseNewUI;
    private final Activity mContext;

    public ForceOldUIHandler(Activity context) {
        mContext = context;
        mUseNewUI = CommonApplication.getPreferences().getUseNewUI();
    }

    @Override
    public void onInit() {
        String cookie = OLD_UI_COOKIE;

        if (mUseNewUI) {
            cookie = NEW_UI_COOKIE;
        }

        try {
            if (mContext instanceof SmartYouTubeTV1080Activity || mContext instanceof SmartYouTubeTV4K) {
                CookieManager.getInstance().setCookie(COOKIE_URL, cookie);
            } else {
                XWalkCookieManager cookieManager = new XWalkCookieManager();
                cookieManager.setCookie(COOKIE_URL, cookie);
            }
        } catch (Exception e) {
            // WebView not installed?
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }
    }
}
