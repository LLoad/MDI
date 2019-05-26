package com.example.mdi;

import java.util.UUID;

public class Common {
    public static final int LOAD_SUCCESS = 101;
    public static final int PERMISSION_REQUEST = 100;
    public static final String SEARCH_URL = "http://192.168.209.77:3000/drugs/";
    public static final String SEARCH_NAME = "drug_list_from_name";
    public static final String SEARCH_SHAPE = "drug_list_from_shape";
    public static final String SEARCH_CAMERA = "drug_list_from_image";
    public static final String SEARCH_CAMERA_SHAPE = "drug_list_from_image_shape";
    public static final String FILE_NAME = "cameraTemp.jpg";
    public static final String uniqueId = UUID.randomUUID().toString();
}
