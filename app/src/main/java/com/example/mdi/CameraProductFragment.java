package com.example.mdi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import static android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK;

public class CameraProductFragment extends Fragment
        implements ActivityCompat.OnRequestPermissionsResultCallback {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    String[] REQUIRED_PERMISSIONS = {Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int CAMERA_FACING = CAMERA_FACING_BACK;

    private SurfaceView surfaceView;
    private CameraPreview mCameraPreview;
    private Button btn;

    public CameraProductFragment() {
    }

    public static CameraProductFragment newInstance(String param1, String param2) {
        CameraProductFragment fragment = new CameraProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_camera_product, container, false);

        surfaceView = v.findViewById(R.id.camera_preview_main);

        surfaceView.setVisibility(View.GONE);

        btn = (Button)v.findViewById(R.id.camera_button);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                    mCameraPreview.takePicture();

                    Intent intent = new Intent(getActivity(), ResultCameraActivity.class);
                    startActivity(intent);
            }
        });

        if(getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            int cameraPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
            int writeExternalStoragePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if(cameraPermission == PackageManager.PERMISSION_GRANTED
                && writeExternalStoragePermission == PackageManager.PERMISSION_GRANTED) {
                startCamera();
                FrameLayout surface = (FrameLayout)v.findViewById(R.id.fl);
                surface.addView(new DrawON(this.getContext()));
            } else {
                if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[1])) {

                    Snackbar.make(v, "이 앱을 실행하려면 카메라와 외부 저장소 접근 권한이 필요합니다.", Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {
                        @Override
                        public void onClick(View view){
                            ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS, Common.PERMISSION_REQUEST);
                        }
                    }).show();
                } else {
                    ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS, Common.PERMISSION_REQUEST);
                }
            }
        } else {
            final Snackbar snackbar = Snackbar.make(v, "디바이스가 카메라를 지원하지 않습니다.", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("확인", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar.dismiss();
                }
            });
            snackbar.show();
        }
        return v;
    }

    void startCamera() {
        mCameraPreview = new CameraPreview(getContext(), this , Camera.CameraInfo.CAMERA_FACING_BACK, surfaceView);
    }
}

