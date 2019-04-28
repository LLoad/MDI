package com.example.mdi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class ResultShapeActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    String result;
    Intent intent = new Intent();
    DrugAdapter drugAdapter;
    Context context;

    private ArrayList<Drug> drugList = new ArrayList<Drug>();

    private String SEARCH_URL = Common.SEARCH_URL;
    private String SEARCH_SHAPE = Common.SEARCH_SHAPE;
    private String PARAM_FRONT = Common.PARAM_FRONT;
    private String PARAM_BACK = Common.PARAM_BACK;
    private String PARAM_TYPE = Common.PARAM_TYPE;
    private String PARAM_SHAPE = Common.PARAM_SHAPE;
    private String PARAM_COLOR = Common.PARAM_COLOR;
    private String REQUEST_URL;
    ListView listview;

    private String intentDrugFront;
    private String intentDrugBack;
    private String intentDrugType;
    private String intentDrugShape;
    private String intentDrugColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_shape);
        context = this;
        intent = getIntent();

        intentDrugFront = intent.getStringExtra("frontText");
        if(intentDrugFront == null) intentDrugFront = "";
        intentDrugBack = intent.getStringExtra("backText");
        if(intentDrugBack == null) intentDrugBack = "";
        intentDrugType = intent.getStringExtra("type");
        intentDrugShape = intent.getStringExtra("shape");
        intentDrugColor = intent.getStringExtra("color");

        try {
            REQUEST_URL = SEARCH_URL + SEARCH_SHAPE + PARAM_TYPE + URLEncoder.encode(intentDrugType, "utf-8")
                    + "&" + PARAM_SHAPE + URLEncoder.encode(intentDrugShape, "utf-8")
                    + "&" + PARAM_COLOR + URLEncoder.encode(intentDrugColor, "utf-8");
                    //+ " AND " + PARAM_FRONT + URLEncoder.encode(intentDrugFront, "utf-8")
                    //+ " AND " + PARAM_BACK + URLEncoder.encode(intentDrugBack, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        listview = (ListView) findViewById(R.id.shapeListView);

        progressDialog = new ProgressDialog(ResultShapeActivity.this);
        progressDialog.setMessage("Please wait.....");
        progressDialog.show();

        getJSON();

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

    private final ResultShapeActivity.MyHandler mHandler = new ResultShapeActivity.MyHandler(this);


    private static class MyHandler extends Handler {
        private final WeakReference<ResultShapeActivity> weakReference;

        public MyHandler(ResultShapeActivity resultShapeActivity) {
            weakReference = new WeakReference<ResultShapeActivity>(resultShapeActivity);
        }

        @Override
        public void handleMessage(Message msg) {

            ResultShapeActivity resultShapeActivity = weakReference.get();

            if (resultShapeActivity != null) {
                switch (msg.what) {

                    case Common.LOAD_SUCCESS:
                        resultShapeActivity.progressDialog.dismiss();

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
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setUseCaches(false);
                    httpURLConnection.setDefaultUseCaches(false);

                    int responseStatusCode = httpURLConnection.getResponseCode();

                    InputStream inputStream;
                    if(responseStatusCode == HttpURLConnection.HTTP_OK) inputStream = httpURLConnection.getInputStream();
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
}
