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
import java.io.BufferedWriter;
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

public class ResultShapeActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    String result;
    Intent intent = new Intent();
    DrugAdapter drugAdapter;
    Context context;

    private ArrayList<Drug> drugList = new ArrayList<Drug>();

    private String SEARCH_URL = Common.SEARCH_URL;
    private String SEARCH_SHAPE = Common.SEARCH_SHAPE;
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

        listview = (ListView) findViewById(R.id.shapeListView);

        progressDialog = new ProgressDialog(ResultShapeActivity.this);
        progressDialog.setMessage("Please wait.....");
        progressDialog.show();

        getJSON();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ResultDetailActivity.class);

                intent.putExtra("drugName", drugList.get(position).getDrug_name());
                intent.putExtra("drugImage", drugList.get(position).getDrug_image());
                intent.putExtra("drugType", drugList.get(position).getDrug_type());
                intent.putExtra("drugShape", drugList.get(position).getDrug_shape());
                intent.putExtra("drugFrontColor", drugList.get(position).getDrug_frontColor());
                intent.putExtra("drugBackColor", drugList.get(position).getDrug_backColor());
                intent.putExtra("drugFrontText", drugList.get(position).getDrug_frontText());
                intent.putExtra("drugBackText", drugList.get(position).getDrug_backText());
                intent.putExtra("drugLongSize", drugList.get(position).getDrug_longSize());
                intent.putExtra("drugShortSize", drugList.get(position).getDrug_shortSize());
                intent.putExtra("drugTemper", drugList.get(position).getDrug_temper());

                startActivity(intent);
            }
        });
    }

    private final ResultShapeActivity.MyHandler mHandler = new ResultShapeActivity.MyHandler(this);


    private static class MyHandler extends Handler {
        private final WeakReference<ResultShapeActivity> weakReference;

        public MyHandler(ResultShapeActivity resultShapeActivity) {
            weakReference = new WeakReference<>(resultShapeActivity);
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
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("drugType", intentDrugType);
                    jsonObject.accumulate("drugShape", intentDrugShape);
                    jsonObject.accumulate("drugColor", intentDrugColor);
                    jsonObject.accumulate("drugFrontText", intentDrugFront);
                    jsonObject.accumulate("drugBackText", intentDrugBack);

                    URL url = new URL(SEARCH_URL + SEARCH_SHAPE);
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
                String j_drugName = jsonObject.getString("ITEMNAME");
                String j_drugImage = jsonObject.getString("LARGEIMG");
                String j_drugType = jsonObject.getString("REFINING");
                String j_drugShape = jsonObject.getString("ITEMSHAPE");
                String j_drugTemper = jsonObject.getString("TEMPER");
                String j_drugFrontColor = jsonObject.getString("FRONTCOLOR");
                String j_drugBackColor = jsonObject.getString("BACKCOLOR");
                String j_drugFrontMark = jsonObject.getString("FRONTMARK");
                String j_drugBackMark = jsonObject.getString("BACKMARK");
                String j_drugFrontContent = jsonObject.getString("FRONTCONTENT");
                String j_drugBackContent = jsonObject.getString("BACKCONTENT");
                String j_drugFrontCode = jsonObject.getString("FRONTCODE");
                String j_drugBackCode = jsonObject.getString("BACKCODE");
                String j_drugLongSize = jsonObject.getString("LONGSIZE");
                String j_drugShortSize = jsonObject.getString("SHORTSIZE");

                String j_drugFrontText = j_drugFrontMark + j_drugFrontContent + j_drugFrontCode;
                String j_drugBackText = j_drugBackMark + j_drugBackContent + j_drugBackCode;

                URL url = new URL(j_drugImage);

                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
                Bitmap bitmap = BitmapFactory.decodeStream(bufferedInputStream);
                bufferedInputStream.close();

                Drug drug = new Drug(j_drugName, j_drugImage, bitmap, j_drugType, j_drugShape, j_drugTemper, j_drugFrontColor,
                        j_drugBackColor, j_drugFrontText, j_drugBackText, j_drugLongSize, j_drugShortSize);
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
