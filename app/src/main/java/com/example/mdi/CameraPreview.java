package com.example.mdi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class CameraPreview extends ViewGroup implements SurfaceHolder.Callback {

    private int mCameraID;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private Camera.CameraInfo mCameraInfo;
    private int mDisplayOrientation;
    private List<Size> mSupportedPreviewSizes;
    private Size mPreviewSize;
    private boolean isPreview = false;

    private Fragment mFragment;

    public CameraPreview(Context context, Fragment fragment, int cameraID, SurfaceView surfaceView) {
        super(context);

        mFragment = fragment;
        mCameraID = cameraID;
        mSurfaceView = surfaceView;

        mSurfaceView.setVisibility(View.VISIBLE);

        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int height = resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec);

        setMeasuredDimension(width, height);

        if(mSupportedPreviewSizes != null) {
            mPreviewSize = getOptimalPreviewSize(mSupportedPreviewSizes, width, height);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera = Camera.open(mCameraID);
        } catch (Exception e) {
            Log.e("","Camera " + mCameraID + " is not available: " + e.getMessage());
        }

        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(mCameraID, cameraInfo);


        mCameraInfo = cameraInfo;
        mDisplayOrientation = mFragment.getActivity().getWindowManager().getDefaultDisplay().getRotation();

        int orientation = (mCameraInfo.orientation + 360) % 360;
        mCamera.setDisplayOrientation(orientation);

        mSupportedPreviewSizes = mCamera.getParameters().getSupportedPreviewSizes();
        requestLayout();

        Camera.Parameters params = mCamera.getParameters();

        List<String> focusModes = params.getSupportedFocusModes();
        if(focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            mCamera.setParameters(params);
        }

        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();

            mCamera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {

                }
            });
            isPreview = true;
            Log.d("", "Camera preview started.");
        } catch (IOException e) {
            Log.d("", "Error setting camera preview: "+ e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(mCamera != null) {
            if (isPreview) mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
            isPreview = false;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(changed && getChildCount() > 0) {
            final View child = getChildAt(0);

            final int width = r - 1;
            final int height = b - t;

            int previewWidth = width;
            int previewHeight = height;
            if(mPreviewSize != null) {
                previewWidth = mPreviewSize.width;
                previewHeight = mPreviewSize.height;
            }
            final int scaledChildWidth = previewWidth * height / previewHeight;
            child.layout((width - scaledChildWidth) / 2, 0,
                    (width+scaledChildWidth) / 2, height);
        }
    }


    private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) w / h;
        if(sizes == null) return null;

        Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        for(Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if(Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if(Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        if(optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for(Size size : sizes) {
                if(Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    public void takePicture(){
        mCamera.takePicture(shutterCallback, rawCallback, jpegCallback);
    }
    Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
        public void onShutter() {

        }
    };

    Camera.PictureCallback rawCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {

        }
    };

    Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {

            int w = camera.getParameters().getPictureSize().width;
            int h = camera.getParameters().getPictureSize().height;
            int orientation = (mCameraInfo.orientation + 360) % 360;

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);

            Matrix matrix = new Matrix();
            matrix.postRotate(orientation);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] currentData = stream.toByteArray();

            new SaveImageTask().execute(currentData);
        }
    };

    private class SaveImageTask extends AsyncTask<byte[], Void, Void> {
        @Override
        protected Void doInBackground(byte[]... data) {
            FileOutputStream outStream = null;

            try {
                File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/camtest");
                if(!path.exists()) path.mkdir();

                String fileName = String.format(Common.FILE_NAME);
                File outputFile = new File(path, fileName);

                outStream = new FileOutputStream(outputFile);
                outStream.write(data[0]);
                outStream.flush();
                outStream.close();

                Log.d("", "onPictureTaken - wrote bytes: " + data.length + " to " + outputFile.getAbsolutePath());

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                mediaScanIntent.setData(Uri.fromFile(outputFile));
                getContext().sendBroadcast(mediaScanIntent);

                try {
                    mCamera.setPreviewDisplay(mSurfaceHolder);
                    mCamera.startPreview();
                    Log.d("", "Camera preview started");
                } catch (Exception e) {
                    Log.d("", "Error starting camera preview: " + e.getMessage());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
