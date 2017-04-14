package com.catchoom.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        final Button button_back = (Button) findViewById(R.id.back_button);
        button_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goBack(v);
            }
        });
    }

    private void goBack(View view) {
        finish();
    }

}
