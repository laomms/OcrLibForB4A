package com.tuts.prakash.simpleocr;

import android.app.Activity;
import android.Manifest;
import android.os.Environment;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.view.Display;
import android.view.ViewGroup;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.widget.TextView;
import android.graphics.Point;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;
import java.util.concurrent.atomic.AtomicBoolean;
import anywheresoftware.b4a.BA;
import java.io.IOException;


public class MainActivity extends AppCompatActivity  {

    private static final String TAG = "MainActivity";
	static AtomicBoolean surfaceExists = new AtomicBoolean(false);
	private static final int CAMERA_REQUEST_ID = 2122;
    public static final String mTextBlock = "String";


	private SurfaceHolder mSurfaceHolder;

	Camera mCamera;
    Context mContext;
    SurfaceView mCameraView;
    TextView mTextView;
    CameraSource mCameraSource;
	CameraInfo mCameraInfo;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		mTextView = (TextView)findViewById(R.id.text_view);
        mCameraView = (SurfaceView)findViewById(R.id.surfaceView);
		

        startCameraSource();
    }
   
      

    private void startCameraSource() {
        TextRecognizer textRecognizer = new TextRecognizer.Builder(this).build();
        if (textRecognizer.isOperational()) {
            mCameraSource = new CameraSource.Builder(this, textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            mCameraView.getHolder().addCallback(new SurfaceHolder.Callback() {

                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {
                    try {
                        if (ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA},CAMERA_REQUEST_ID);
                            return;
                        }
                        mCameraSource.start(mCameraView.getHolder());
						
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)  {
                    
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                    mCameraSource.stop();
                }
            });


            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {

                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {
                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    mTextView.post(new Runnable() {
                        @Override
                        public void run() {
                            if (items.size() == 0) {
                                mTextView.setText("");
                                mTextView.setVisibility(View.INVISIBLE);
                            } else {
                                StringBuilder stringBuilder = new StringBuilder();
								TextBlock text = null;
                                for (int i = 0; i < items.size(); i++) {
                                    if ((i < items.size()) && (i < 10)) {
                                        String str = ((TextBlock)items.valueAt(i)).getValue().replaceAll("[^0-9]", "");
                                        Log.d("tag", "Value: " + str);
                                        if ((str.length() == 54) || (str.length() == 63)) {
                                            BA.Log("Value: " + str);
              
                                            //MainActivity.insertExt = str;
                                            Intent mIntent = new Intent();
			                                //mIntent.putExtra(mTextBlock, text.getValue());
                                            mIntent.putExtra(mTextBlock, str);
                                            MainActivity.this.setResult(0, mIntent);
                                            items.clear();
                                            finish();
                                        }
                                    }
                                    else
                                    {
                                    return;
                                    }
                                }
                                mTextView.setText(stringBuilder.toString());
                                mTextView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
            });
        } else {
            Log.d(getClass().getSimpleName(), "Text Recognizer isn't ready yet");
        }
    }

}
