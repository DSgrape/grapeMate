package com.example.grape;

import android.util.Log;
import android.view.View;

public class hideNavigationBar {
    public hideNavigationBar(View v) {
        int uiOptions = v.getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.d("x", "Turning immersive mode mode off. ");
        } else {
            Log.d("x", "Turning immersive mode mode on.");
        }
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        //newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        v.setSystemUiVisibility(newUiOptions);
    }
}
