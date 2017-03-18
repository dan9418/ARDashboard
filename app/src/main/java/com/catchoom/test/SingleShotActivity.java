package com.catchoom.test;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class SingleShotActivity extends CraftARActivity implements CraftARSearchResponseHandler {

    //Singleton classes
    CraftAROnDeviceIR mOnDeviceIR;
    CraftARSDK mCraftARSDK;
    CraftARCamera mCamera;
    //Local variables
    TrackingBox trackingBox;
    View mainLayout;

    @Override
    public void onPostCreate() {
        //Set layout
        mainLayout = getLayoutInflater().inflate(R.layout.camera_overlay, null);
        setContentView(mainLayout);

        //Obtain an instance of the CraftARSDK (which manages the camera interaction).
        //Note we already called CraftARSDK.init() in the Splash Screen, so we don't have to do it again
        mCraftARSDK = CraftARSDK.Instance();
        mCraftARSDK.startCapture(this);

        //Get the instance to the OnDeviceIR singleton (it has already been initialized in the SplashScreenActivity, and the collections are already loaded).
        mOnDeviceIR = CraftAROnDeviceIR.Instance();

        //Tell the SDK that the OnDeviceIR who manage the calls to singleShotSearch() and startFinding().
        //In this case, as we are using on-device-image-recognition, we will tell the SDK that the OnDeviceIR singleton will manage this calls.
        mCraftARSDK.setSearchController(mOnDeviceIR.getSearchController());

        //Tell the SDK that we want to receive the search responses in this class.
        mOnDeviceIR.setCraftARSearchResponseHandler(this);

        //Obtain the reference to the camera, to be able to restart the camera, trigger focus etc.
        //Note that if you use single-shot, you will always have to obtain the reference to the camera to restart it after you take the snapshot.
        mCamera = mCraftARSDK.getCamera();

        final Button captureButton = (Button) findViewById(R.id.capture_button);
        captureButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startSingleShot(v);
            }
        });

        trackingBox = new TrackingBox(this);
        addContentView(trackingBox, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));

    }

    //Called on button push
    private void startSingleShot(View view) {
        //Toast.makeText(getApplicationContext(), "Capturing...", Toast.LENGTH_SHORT).show();
        trackingBox.LEFT_SIDE = 0;
        trackingBox.RIGHT_SIDE = 0;
        trackingBox.TOP_SIDE = 0;
        trackingBox.BOTTOM_SIDE = 0;
        mCraftARSDK.singleShotSearch();
    }

    //Works for both Finder and Single Shot
    public void searchResults(ArrayList<CraftARResult> results, long searchTimeMillis, int requestCode) {
        if(results.size() > 0){
            if(mCraftARSDK.isFinding()) {
                mCraftARSDK.stopFinder();
            }
            //for(int i=0; i<results.size(); i++){
            for(int i=0; i<1; i++){
                CraftARResult result = results.get(i);

                int score = result.getScore();
                Log.d(TAG, "Found item :"+result.getItem().getItemName());
                //Toast.makeText(getApplicationContext(), "Found: "+result.getItem().getItemName() + " ("+i+"/"+results.size()+")" + " Score="+score, Toast.LENGTH_SHORT).show();

                CraftARBoundingBox box = result.getBoundingBox();
                RelativeLayout layout = (RelativeLayout)findViewById(R.id.camera_overlay);
                trackingBox.assignBoxPosition(layout, box);
                ((TextView)findViewById(R.id.component_name)).setText(result.getItem().getItemName());
            }
        }
        else {
            Log.e(TAG, "Nothing found");
            //Toast.makeText(getApplicationContext(), "Nothing found", Toast.LENGTH_SHORT).show();
        }
        //mCraftARSDK.getCamera().restartCapture();
    }



    @Override
    public void searchFailed(CraftARError error, int requestCode) {
        Log.e(TAG, "Search failed( "+error.getErrorCode()+"):"+error.getErrorMessage());
        Toast.makeText(getApplicationContext(), "Search Failed", Toast.LENGTH_SHORT).show();
        mCraftARSDK.getCamera().restartCapture();
    }
    @Override
    public void onCameraOpenFailed(){
        Log.e(TAG, "Camera failed to open");
        Toast.makeText(getApplicationContext(), "Camera failed to open", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPreviewStarted(int i, int i1) {
        Log.d(TAG, "Preview started");
    }

    private void startCraftARActivity(){
        Log.d(TAG, "Starting CraftARActivity");
    }

}
