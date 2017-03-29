package com.catchoom.test;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.craftar.CraftARError;
import com.craftar.CraftAROnDeviceCollection;
import com.craftar.CraftAROnDeviceCollectionManager;
import com.craftar.CraftAROnDeviceIR;
import com.craftar.CraftARSDK;
import com.craftar.ImageRecognition;

import static android.content.ContentValues.TAG;

public class SplashScreenActivity extends AppCompatActivity implements ImageRecognition.SetOnDeviceCollectionListener, CraftAROnDeviceCollectionManager.AddCollectionListener, CraftAROnDeviceCollectionManager.SyncCollectionListener {

    CraftAROnDeviceIR mCraftAROnDeviceIR;
    CraftAROnDeviceCollectionManager mCollectionManager;
    private String TOKEN = "d87a91ed431f45bc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        final Button button_single_shot = (Button) findViewById(R.id.capture_button);
        button_single_shot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launchCapture(v);
            }
        });

        final Button button_continuous = (Button) findViewById(R.id.continuous_button);
        button_continuous.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launchContinuous(v);
            }
        });

        final Button button_help = (Button) findViewById(R.id.help_button);
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

    private void launchCapture(View view) {
        Intent intent = new Intent(this, RecognitionActivity.class);
        intent.putExtra("MODE", Global.CAMERA_MODE.CAPTURE.toString());
        startActivity(intent);
    }

    private void launchContinuous(View view) {
        Intent intent = new Intent(this, RecognitionActivity.class);
        intent.putExtra("MODE", Global.CAMERA_MODE.CONTINOUS.toString());
        startActivity(intent);
    }

    private void launchHelp(View view) {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    @Override
    public void collectionAdded(CraftAROnDeviceCollection collection) {
        Log.d(TAG, "Collection added");
        collection.sync(this);
    }

    @Override
    public void addCollectionFailed(CraftARError error) {
        Toast.makeText(getApplicationContext(), "Add collection failed: "+ error.getErrorMessage(), Toast.LENGTH_SHORT).show();
        Log.e(TAG, "Add collection failed: "+ error.getErrorMessage());
        finish();
    }

    @Override
    public void addCollectionProgress(float progress) {
        Log.d(TAG, "Add collection progress "+ progress);
    }

    @Override
    public void syncSuccessful(CraftAROnDeviceCollection craftAROnDeviceCollection) {
        Log.d(TAG, "Sync successful");
    }

    @Override
    public void syncFinishedWithErrors(CraftAROnDeviceCollection craftAROnDeviceCollection, int itemsToSync, int syncErrors) {
        Log.e(TAG, "Sync finished with errors");
    }

    @Override
    public void syncProgress(CraftAROnDeviceCollection craftAROnDeviceCollection, float progress) {
        Log.d(TAG, "Sync progress : "+progress);
    }

    @Override
    public void syncFailed(CraftAROnDeviceCollection craftAROnDeviceCollection, CraftARError error) {
        Log.e(TAG, "Sync failed : "+error.getErrorMessage());
    }

    @Override
    public void setCollectionProgress(double progress) {
        Log.d(TAG, "Set collection progress: " + progress);
    }

    @Override
    public void collectionReady() {
        Log.d(TAG, "Collection ready.");
    }

    @Override
    public void setCollectionFailed(CraftARError error) {
        Log.e(TAG, "Setting connection failed : " + error.getErrorMessage());
    }
}
