package com.example.mdi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.databinding.DataBindingUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.example.mdi.databinding.ActivityResultCameraBinding;

public class ResultCameraActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;

    private ActivityResultCameraBinding binding;
    private RecyclerView.Adapter recycler;

    String result;
    int selectResultPosition = 0;
    int drugCount = 0;
    DrugAdapter drugAdapter;
    Context context;

    private ArrayList<Drug> drugList = new ArrayList<>();
    private ArrayList<RecyclerItem> counting = new ArrayList<>();

    private String SEARCH_URL = Common.SEARCH_URL;
    private String SEARCH_CAMERA = Common.SEARCH_CAMERA;
    private String REQUEST_URL;
    ImageView image;
    RecyclerView recyclerView;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_camera);
        context = this;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_result_camera);

        REQUEST_URL = SEARCH_URL + SEARCH_CAMERA;
        recyclerView = (RecyclerView) findViewById(R.id.drugCount);
        listview = (ListView) findViewById(R.id.imageListView);
        image = (ImageView) findViewById(R.id.camera_image);

        progressDialog = new ProgressDialog(ResultCameraActivity.this);
        progressDialog.setMessage("Please wait.....");
        progressDialog.show();

        getJSON();



        binding.drugCount.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), binding.drugCount, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        selectResultPosition = position;

                    }

                    @Override
                    public void onLongItemClick(View view, int position) { }
                })
        );

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ResultDetailActivity.class);

                intent.putExtra("drugId", drugList.get(position).getDrug_id());
                intent.putExtra("drugName", drugList.get(position).getDrug_name());
                intent.putExtra("drugImage", drugList.get(position).getDrug_image());
                intent.putExtra("drugType", drugList.get(position).getDrug_type());
                intent.putExtra("drugShape", drugList.get(position).getDrug_shape());
                intent.putExtra("drugColor", drugList.get(position).getDrug_color());
                intent.putExtra("drugFrontText", drugList.get(position).getDrug_frontText());
                intent.putExtra("drugBackText", drugList.get(position).getDrug_backText());

                startActivity(intent);
            }
        });
    }

    private void setRecyclerView() {
        binding.drugCount.setHasFixedSize(true);

        recycler = new RecyclerAdapter(counting);
        binding.drugCount.setAdapter(recycler);

        binding.drugCount.setLayoutManager(new LinearLayoutManager(this));
        setCount();
    }
    private void setCount() {
        counting.clear();

        for(int i = 0; i < 3; i++) {
            counting.add(new RecyclerItem(i));
        }

    }


    private final ResultCameraActivity.MyHandler mHandler = new ResultCameraActivity.MyHandler(this);


    private static class MyHandler extends Handler {
        private final WeakReference<ResultCameraActivity> weakReference;

        public MyHandler(ResultCameraActivity resultCameraActivity) {
            weakReference = new WeakReference<>(resultCameraActivity);
        }

        @Override
        public void handleMessage(Message msg) {

            ResultCameraActivity resultCameraActivity = weakReference.get();

            if (resultCameraActivity != null) {
                switch (msg.what) {

                    case Common.LOAD_SUCCESS:
                        resultCameraActivity.progressDialog.dismiss();

                        String jsonString = (String) msg.obj;
                        break;
                }
            }
        }
    }


    public void getJSON() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(REQUEST_URL);

                    File location = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +"camtest");

                    File file = new File(location, Common.FILE_NAME);

                    Bitmap bitmap = decodeFile(1000, file.getPath());

                    String bitmapString = getStringFromBitmap(bitmap);

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("drugImage", bitmapString);

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("Cache-Control", "no-cache");
                    httpURLConnection.setRequestProperty("Content-Type", "application/json");
                    httpURLConnection.setRequestProperty("Accept", "text/html");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.connect();

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                    bufferedWriter.write(jsonObject.toString());
                    bufferedWriter.flush();
                    bufferedWriter.close();

                    int responseStatusCode = httpURLConnection.getResponseCode();

                    InputStream inputStream;
                    if (responseStatusCode == HttpURLConnection.HTTP_OK) inputStream = httpURLConnection.getInputStream();
                    else inputStream = httpURLConnection.getErrorStream();

                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder sb = new StringBuilder();
                    String line;

                    while((line = bufferedReader.readLine()) != null) sb.append(line);

                    result = sb.toString().trim();
                    Log.d("tag", result);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(jsonParser(result)) {
                    Message message = mHandler.obtainMessage(Common.LOAD_SUCCESS, result);
                    mHandler.sendMessage(message);
                }
            }
        });
        thread.start();
    }

    public boolean jsonParser(String jsonString) {
        if(jsonString == null) return false;
        try {
            JSONArray jsonArray = new JSONArray(result);

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String j_drugId = jsonObject.getString("drugId");
                String j_drugName = jsonObject.getString("drugName");
                String j_drugImage = jsonObject.getString("drugImage");
                String j_drugType = jsonObject.getString("drugType");
                String j_drugShape = jsonObject.getString("drugShape");
                String j_drugColor = jsonObject.getString("drugColor");
                String j_drugFrontText = jsonObject.getString("drugFrontText");
                String j_drugBackText = jsonObject.getString("drugBackText");
                URL url = new URL(j_drugImage);

                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
                Bitmap bitmap = BitmapFactory.decodeStream(bufferedInputStream);
                bufferedInputStream.close();

                Drug drug = new Drug(j_drugId, j_drugName, j_drugImage, bitmap, j_drugType, j_drugShape, j_drugColor, j_drugFrontText, j_drugBackText);
                drugList.add(drug);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        drugAdapter = new DrugAdapter(context, drugList, listview);

                        listview.setAdapter(drugAdapter);
                    }
                });
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    private Bitmap decodeFile(int minImageSize, String filePath) {
        Bitmap bitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inJustDecodeBounds = false;

        int scale = 1;
        if(Math.max(options.outWidth, options.outHeight) > minImageSize) options.inSampleSize = 2;
        else options.inSampleSize = 1;

        bitmap = BitmapFactory.decodeFile(filePath, options);

        return bitmap;
    }

    private String getStringFromBitmap(Bitmap bitmap) {
        String encodedImage;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }
}
