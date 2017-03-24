package com.catchoom.test;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.craftar.CraftARError;
import com.craftar.CraftAROnDeviceCollection;
import com.craftar.CraftAROnDeviceCollectionManager;
import com.craftar.CraftAROnDeviceIR;
import com.craftar.CraftARSDK;
import com.craftar.ImageRecognition;

import static android.content.ContentValues.TAG;

public class SplashScreen extends AppCompatActivity implements ImageRecognition.SetOnDeviceCollectionListener, CraftAROnDeviceCollectionManager.AddCollectionListener, CraftAROnDeviceCollectionManager.SyncCollectionListener {

    CraftAROnDeviceIR mCraftAROnDeviceIR;
    CraftAROnDeviceCollectionManager mCollectionManager;
    private String TOKEN = "d87a91ed431f45bc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        final ImageButton button_single_shot = (ImageButton) findViewById(R.id.capture_button);
        button_single_shot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launchSingleShot(v);
            }
        });

        final ImageButton button_continuous = (ImageButton) findViewById(R.id.continuous_button);
        button_continuous.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launchContinuous(v);
            }
        });

        final ImageButton button_help = (ImageButton) findViewById(R.id.help_button);
        button_help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launchHelp(v);
            }
        });

        CraftARSDK.Instance().init(getApplicationContext());

        //Initialize the Collection Manager
        mCollectionManager = CraftAROnDeviceCollectionManager.Instance();

        //Initialize the Offline IR Module
        mCraftAROnDeviceIR = CraftAROnDeviceIR.Instance();

        //Add collection
        CraftAROnDeviceCollection collection = mCollectionManager.get(TOKEN);
        if(collection != null) {
            collection.sync(this);
        } else{
            mCollectionManager.addCollection("database.zip", this);
        }
        mCraftAROnDeviceIR.setCollection(collection, true, this);
    }

    private void launchSingleShot(View view) {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivity(intent);
    }

    private void launchContinuous(View view) {
        Intent intent = new Intent(this, ContinuousActivity.class);
        startActivity(intent);
    }

    private void launchHelp(View view) {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    @Override
    public void collectionAdded(CraftAROnDeviceCollection collection) {
        Log.e(TAG, "Collection added");
        collection.sync(this);
    }

    @Override
    public void addCollectionFailed(CraftARError error) {
        Toast.makeText(getApplicationContext(), "AddCollection failed: "+
                error.getErrorMessage(), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void addCollectionProgress(float progress) {
        Log.d(TAG, "addCollectionProgress "+ progress);
    }

    @Override
    public void syncSuccessful(CraftAROnDeviceCollection craftAROnDeviceCollection) {
        Log.d(TAG, "Sync successful");
    }

    @Override
    public void syncFinishedWithErrors(CraftAROnDeviceCollection craftAROnDeviceCollection, int i, int i1) {
        Log.d(TAG, "Sync finished with errors");
    }

    @Override
    public void syncProgress(CraftAROnDeviceCollection craftAROnDeviceCollection, float progress) {
        Log.d(TAG, "syncProgress : "+progress);
    }

    @Override
    public void syncFailed(CraftAROnDeviceCollection craftAROnDeviceCollection, CraftARError error) {
        Log.e(TAG, "syncFailed : "+error.getErrorMessage());
    }

    @Override
    public void setCollectionProgress(double progress) {
        Log.d(TAG, "Collection progress: " + progress);
    }

    @Override
    public void collectionReady() {
        Log.d(TAG, "Collection ready");
    }

    @Override
    public void setCollectionFailed(CraftARError error) {
        Log.d(TAG, "Setting connection failed..");
    }
}
