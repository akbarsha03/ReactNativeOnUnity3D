package com.akbarsha03.reactmodule;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.widget.Toast;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;

public class ReactNativeActivity extends Activity implements DefaultHardwareBackBtnHandler {

    private final int OVERLAY_PERMISSION_REQ_CODE = 1;  // Choose any value
    private ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;

    public static void launchReactActivity(Activity activity) {
        activity.startActivity(new Intent(activity, ReactNativeActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
            }
        }

        mReactRootView = new ReactRootView(this);
        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setBundleAssetName("index.android.bundle")
                .setJSMainModulePath("index")
                .addPackage(new MainReactPackage())
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();
        // The string here (e.g. "MyReactNativeApp") has to match
        // the string in AppRegistry.registerComponent() in index.js
        mReactRootView.startReactApplication(mReactInstanceManager, "sampleReactApp", null);

        setContentView(mReactRootView);
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                if (!Settings.canDrawOverlays(this))
                    Toast.makeText(this, "Grant SYSTEM ALERT WINDOW Permission", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mReactInstanceManager != null) mReactInstanceManager.onHostPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mReactInstanceManager != null) mReactInstanceManager.onHostResume(this, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReactInstanceManager != null) mReactInstanceManager.onHostDestroy(this);
        if (mReactRootView != null) mReactRootView.unmountReactApplication();
    }

    @Override
    public void onBackPressed() {
        if (mReactInstanceManager != null) mReactInstanceManager.onBackPressed();
        else super.onBackPressed();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && mReactInstanceManager != null) {
            mReactInstanceManager.showDevOptionsDialog();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
