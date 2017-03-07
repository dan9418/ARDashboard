package com.catchoom.test;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        final Button button_single_shot = (Button) findViewById(R.id.button_single_shot);
        button_single_shot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launchSingleShot(v);
            }
        });

        final Button button_continuous = (Button) findViewById(R.id.button_continuous);
        button_continuous.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launchContinuous(v);
            }
        });

    }

    private void launchSingleShot(View view) {
        Intent intent = new Intent(this, SingleShotActivity.class);
        startActivity(intent);
    }

    private void launchContinuous(View view) {
        Intent intent = new Intent(this, ContinuousActivity.class);
        startActivity(intent);
    }
}
