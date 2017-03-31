package com.catchoom.test;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.catchoom.test.database.Communication;
import com.craftar.CraftARBoundingBox;
import com.craftar.CraftARCamera;
import com.craftar.CraftARError;
import com.craftar.CraftAROnDeviceIR;
import com.craftar.CraftARResult;
import com.craftar.CraftARSDK;
import com.craftar.CraftARSearchResponseHandler;
import com.craftar.CraftARActivity;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class RecognitionActivity extends CraftARActivity implements CraftARSearchResponseHandler {

    // Singleton classes
    CraftAROnDeviceIR mOnDeviceIR;
    CraftARSDK mCraftARSDK;
    CraftARCamera mCamera;
    // Regular classes
    TrackingBox trackingBox;
    Communication databaseLink;

    @Override
    public void onPostCreate() {
        View mainLayout = getLayoutInflater().inflate(R.layout.camera_overlay, null);
        setContentView(mainLayout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Establish a connection to the database simulation
            databaseLink = new Communication(Global.SWITCHGEAR_ADDRESS, Global.SWITCHGEAR_PORT);


        // Initialize recognition/tracking components
        initializeCraftAR();
        initializeTrackingBox();

        // Always stop finding when camera is initially opened
        if(mCraftARSDK.isFinding()) {
            mCraftARSDK.stopFinder();
        }

        initializeUI();
        restart();

    }

    // Button listener methods

    private void capture() {
        mCraftARSDK.stopFinder();
        mCraftARSDK.singleShotSearch();
    }

    private void restart() {
        mCraftARSDK.stopFinder();
        mCraftARSDK.getCamera().restartCapture();
        mCraftARSDK.startFinder();
    }

    // UI initialization method

    private void initializeUI() {
        /// Set onClick listeners
        final Button captureButton = (Button) findViewById(R.id.capture_button);
        captureButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                capture();
            }
        });

        final Button restartButton = (Button) findViewById(R.id.restart_button);
        restartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                restart();
            }
        });


        final View cameraLayout = findViewById(R.id.camera_overlay);
        cameraLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getApplicationContext(), "Focusing...", Toast.LENGTH_SHORT).show();
                mCamera.triggerAutoFocus();
                return true;
            }
        });
    }

    // Recognition/tracking initialization methods

    private void initializeCraftAR() {
        //Obtain an instance of the CraftARSDK (which manages the camera interaction).
        //Note we already called CraftARSDK.init() in the Splash Screen, so we don't have to do it again
        mCraftARSDK = CraftARSDK.Instance();
        mCraftARSDK.startCapture(this);

        //Get the instance to the OnDeviceIR singleton (it has already been initialized in the SplashScreenActivity, and the collections are already loaded).
        mOnDeviceIR = CraftAROnDeviceIR.Instance();

        //Tell the SDK that the OnDeviceIR who manage the calls to singleShotSearch() and startFinding().
        //In this case, as we are using on-device-image-recognition, we will tell the SDK that the OnDeviceIR singleton will manage this call.
        mCraftARSDK.setSearchController(mOnDeviceIR.getSearchController());

        //Tell the SDK that we want to receive the search responses in this class.
        mOnDeviceIR.setCraftARSearchResponseHandler(this);

        //Obtain the reference to the camera, to be able to restart the camera, trigger focus, etc...
        //Note that if you use capture mode, you will always have to obtain the reference to the camera to restart it after you take the snapshot.
        mCamera = mCraftARSDK.getCamera();
        mCamera.setAutoFocusOnTouch(true);
    }

    public void initializeTrackingBox() {
        trackingBox = new TrackingBox(
                (RelativeLayout) findViewById(R.id.camera_overlay),
                (ImageView) findViewById(R.id.overlay_body),
                (TextView) findViewById(R.id.overlay_header),
                (TextView) findViewById(R.id.overlay_text)
        );
    }

    // Search response method

    public void searchResults(ArrayList<CraftARResult> results, long searchTimeMillis, int requestCode) {

        if(results.size() > 0){

            // By getting index 0, we retrieve the top result from the SDK
            // You may also get other recognitions from the results, if desired
            CraftARResult result = results.get(0);
            CraftARBoundingBox box = result.getBoundingBox();

            String itemName = result.getItem().getItemName();
            String itemText = "N/A";
            int itemScore = result.getScore();

            Log.d(TAG, "Found :" + itemName);
            trackingBox.setHeaderText(itemName, itemScore);
            trackingBox.assignPosition(box);

            try {
                itemText = databaseLink.getInfo(itemName).toString();
                Log.d(TAG, itemText);
            }
            catch (Exception e) {
                Log.e(TAG, "Error getting info from database: " + e.getMessage());
            }

            trackingBox.setDescriptionText(itemText);
        }
        else {
            trackingBox.reset();
            Log.e(TAG, "Nothing found");
        }

    }

    // CraftARSearchResponseHandler methods

    @Override
    public void searchFailed(CraftARError error, int requestCode) {
        Log.e(TAG, "Search failed( "+error.getErrorCode()+"): "+error.getErrorMessage());
    }

    @Override
    public void onCameraOpenFailed(){
        Log.e(TAG, "Camera failed to open");
        Toast.makeText(getApplicationContext(), "Camera failed to open", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPreviewStarted(int width, int height) {
        Log.d(TAG, "Preview started.");
    }

}
