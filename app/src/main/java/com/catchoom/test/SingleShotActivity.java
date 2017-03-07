package com.catchoom.test;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.craftar.CraftARBoundingBox;
import com.craftar.CraftARError;
import com.craftar.CraftAROnDeviceCollection;
import com.craftar.CraftAROnDeviceCollectionManager;
import com.craftar.CraftAROnDeviceIR;
import com.craftar.CraftARResult;
import com.craftar.CraftARSDK;
import com.craftar.CraftARSearchResponseHandler;
import com.craftar.CraftARActivity;
import com.craftar.ImageRecognition;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class SingleShotActivity extends CraftARActivity implements CraftARSearchResponseHandler, ImageRecognition.SetOnDeviceCollectionListener, CraftAROnDeviceCollectionManager.AddCollectionListener, CraftAROnDeviceCollectionManager.SyncCollectionListener {

    CraftARSDK mCraftARSDK;
    CraftAROnDeviceIR mOnDeviceIR;
    TrackingBox trackingBox;
    private String TOKEN = "d87a91ed431f45bc";

    @Override
    public void onPostCreate() {
        //Set layout
        View mainLayout = getLayoutInflater().inflate(R.layout.activity_single_shot, null);
        setContentView(mainLayout);

        // Obtain an instance and initialize the CraftARSDK (which manages the camera interaction).
        CraftARSDK.Instance().init(getApplicationContext());
        mCraftARSDK = CraftARSDK.Instance();
        mCraftARSDK.startCapture(this);

        //Add collection
        CraftAROnDeviceCollectionManager collectionManager = CraftAROnDeviceCollectionManager.Instance();
        CraftAROnDeviceCollection collection = collectionManager.get(TOKEN);
        if(collection != null) {
            collection.sync(this);
        } else{
            collectionManager.addCollection("database.zip", this);
        }

        // Get the instance to the OnDeviceIR singleton
        Context context = getApplicationContext();
        mCraftARSDK.init(context);
        mOnDeviceIR = CraftAROnDeviceIR.Instance();
        mOnDeviceIR.setCollection(collection, true, this);

        // Tell the SDK who manage the calls to singleShotSearch() and startFinding().
        // In this case, as we are using on-device-image-recognition, we will tell the
        // SDK that the OnDeviceIR singleton will manage this calls.
        mCraftARSDK.setSearchController(mOnDeviceIR.getSearchController());

        // Tell the SDK that we want to receive the search responses in this class.
        mOnDeviceIR.setCraftARSearchResponseHandler(this);

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
        Toast.makeText(getApplicationContext(), "Capturing...", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), "Found: "+result.getItem().getItemName() +
                        " ("+i+"/"+results.size()+")" +
                        " Score="+score, Toast.LENGTH_SHORT).show();

                CraftARBoundingBox box = result.getBoundingBox();
                RelativeLayout layout = (RelativeLayout)findViewById(R.id.activity_single_shot);
                assignBoxPosition(layout, box);

            }
        }
        else {
            Log.e(TAG, "Nothing found");
            Toast.makeText(getApplicationContext(), "Nothing found", Toast.LENGTH_SHORT).show();
        }
        //mCraftARSDK.getCamera().restartCapture();
    }

    public void assignBoxPosition(RelativeLayout layout, CraftARBoundingBox box) {
        int w = layout.getWidth();
        int h = layout.getHeight();

        trackingBox.TOP_SIDE = box.TLy * h;
        trackingBox.BOTTOM_SIDE = box.BLy * h;
        trackingBox.LEFT_SIDE = box.TLx * w;
        trackingBox.RIGHT_SIDE = box.TRx * w;
        trackingBox.invalidate();


        Log.d(TAG, "[" + w + "," + h + "]");
        Log.d(TAG, "TL(" + box.TLx + "," + box.TLy + ") TR(" + box.TRx + "," + box.TRx + "), BL(" + box.BLx + "," + box.BLx + ") BR(" + box.BRx + "," + box.BRx + ")");
        //Top Left
        assignPosition((TextView) findViewById(R.id.topLeft), (int) (w * box.TLx), (int) (h * box.TLy));
        //Top Right
        assignPosition((TextView) findViewById(R.id.topRight), (int) (w * box.TRx), (int) (h * box.TRy));
        //Bottom Left
        assignPosition((TextView) findViewById(R.id.bottomLeft), (int) (w * box.BLx), (int) (h * box.BLy));
        //Bottom Right
        assignPosition((TextView) findViewById(R.id.bottomRight), (int) (w * box.BRx), (int) (h * box.BRy));
    }

    public void assignPosition(TextView tv, int x, int y) {
        RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) tv.getLayoutParams();
        p.leftMargin = x;
        p.topMargin = y;
        tv.setLayoutParams(p);
        tv.setTextColor(Color.RED);
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
        Log.d(TAG, "addCollectionProgress "+progress);
    }

    @Override
    public void syncSuccessful(CraftAROnDeviceCollection collection) {
        Log.d(TAG, "Sync successful");
        startCraftARActivity();
    }

    @Override
    public void syncFinishedWithErrors(CraftAROnDeviceCollection craftAROnDeviceCollection, int i, int i1) {
        Log.d(TAG, "Sync finished with errors");
    }

    @Override
    public void syncProgress(CraftAROnDeviceCollection collection, float progress) {
        Log.e(TAG, "syncProgress : "+progress);

    }

    @Override
    public void syncFailed(CraftAROnDeviceCollection collection, CraftARError error) {
        Log.e(TAG, "syncFailed : "+error.getErrorMessage());
    }

    private void startCraftARActivity(){
        Log.d(TAG, "Starting CraftARActivity");
    }

    @Override
    public void setCollectionProgress(double v) {
        Log.d(TAG, "Collection progress: " + v);
    }

    @Override
    public void collectionReady() {
        Log.d(TAG, "Collection ready");
    }

    @Override
    public void setCollectionFailed(CraftARError craftARError) {
        Log.d(TAG, "Setting connection failed..");
    }

}
