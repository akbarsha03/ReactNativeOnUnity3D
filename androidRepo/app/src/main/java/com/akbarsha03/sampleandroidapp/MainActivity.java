package com.akbarsha03.sampleandroidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.akbarsha03.reactmodule.ReactNativeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.launchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReactNativeActivity.launchReactActivity(MainActivity.this);
            }
        });
    }
}
