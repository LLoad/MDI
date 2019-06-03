package com.example.mdi;

import android.app.AlertDialog;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class ResultCameraActivity extends AppCompatActivity implements RecyclerAdapter.ItemClickListener {
    private ProgressDialog progressDialog;

    private RecyclerAdapter recyclerAdapter;

    int count = 0;
    int recyclerItem = 0;
    DrugAdapter drugAdapter;
    Context context;
    String front;
    String back;
    boolean click = false;

    private ArrayList<ArrayList<Drug>> drugList = new ArrayList<>();
    private ArrayList<Drug> drugs = new ArrayList<>();

    private ArrayList<Boolean> visiting = new ArrayList<>();

    private String SEARCH_URL = Common.SEARCH_URL;
    private String SEARCH_CAMERA = Common.SEARCH_CAMERA;
    private String SEARCH_CAMERA_SHAPE = Common.SEARCH_CAMERA_SHAPE;
    ImageView imageview;
    RecyclerView recyclerView;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_camera);
        context = this;

        recyclerView = (RecyclerView) findViewById(R.id.recyclerCount);
        listview = (ListView) findViewById(R.id.imageListView);
        imageview = (ImageView) findViewById(R.id.camera_image);

        progressDialog = new ProgressDialog(ResultCameraActivity.this);
        progressDialog.setMessage("Please wait.....");
        progressDialog.show();

        getJSON();


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ResultDetailActivity.class);

                intent.putExtra("drugName", drugList.get(recyclerItem).get(position).getDrug_name());
                intent.putExtra("drugImage", drugList.get(recyclerItem).get(position).getDrug_image());
                intent.putExtra("drugType", drugList.get(recyclerItem).get(position).getDrug_type());
                intent.putExtra("drugShape", drugList.get(recyclerItem).get(position).getDrug_shape());
                intent.putExtra("drugTemper", drugList.get(recyclerItem).get(position).getDrug_temper());
                intent.putExtra("drugFrontColor", drugList.get(recyclerItem).get(position).getDrug_frontColor());
                intent.putExtra("drugBackColor", drugList.get(recyclerItem).get(position).getDrug_backColor());
                intent.putExtra("drugFrontText", drugList.get(recyclerItem).get(position).getDrug_frontText());
                intent.putExtra("drugBackText", drugList.get(recyclerItem).get(position).getDrug_backText());
                intent.putExtra("drugLongSize", drugList.get(recyclerItem).get(position).getDrug_longSize());
                intent.putExtra("drugShortSize", drugList.get(recyclerItem).get(position).getDrug_shortSize());

                startActivity(intent);
            }
        });
    }

    private void setRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        ArrayList<Integer> mCount = new ArrayList<>();
        for(int i = 0; i < count; i++) {
            mCount.add(i);
        }
        recyclerAdapter = new RecyclerAdapter(this, mCount);
        recyclerAdapter.setClickListener(this);
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void setListView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                drugAdapter = new DrugAdapter(context, drugList.get(recyclerItem), listview);
                listview.setAdapter(drugAdapter);

                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) throws InterruptedException {
        if(recyclerItem != position) {
            progressDialog = new ProgressDialog(ResultCameraActivity.this);
            progressDialog.setMessage("Please wait.....");
            progressDialog.show();
            recyclerItem = position;
            if(!visiting.get(recyclerItem)) {
                getJSONToDrugs(recyclerItem);
            }
            else setListView();
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
                        break;
                }
            }
        }
    }

    public void getJSON() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
            String result = "";
                try {
                    URL url = new URL(SEARCH_URL+SEARCH_CAMERA);

                    Thread.sleep(1000);

                    File location = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +"camtest");
                    File file = new File(location, Common.FILE_NAME);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 4;

                    Bitmap bitmap = BitmapFactory.decodeFile(file.getPath(), options);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
                    byte[] ba = byteArrayOutputStream.toByteArray();
                    String imageBase64 = Base64.encodeToString(ba, 0);

                    Log.d("imageBase64", imageBase64);

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("drugImage", imageBase64);

                    jsonObject.accumulate("uid", Common.uniqueId);

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("Cache-Control", "no-cache");
                    httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    httpURLConnection.setRequestProperty("Accept", "text/html");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    bufferedWriter.write(jsonObject.toString());
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    httpURLConnection.connect();

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

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setRecyclerView();
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });
        thread.start();
    }

    public boolean jsonParser(String jsonString) {
        if(jsonString == null) return false;
        try {
            String c = jsonString.substring(jsonString.indexOf("{"), jsonString.lastIndexOf("}")+1);
            c = c.replace("\\", "");
            JSONObject root = new JSONObject(c);

            int dc = root.getInt("drugCount");
            count = dc;

            for(int i = 0; i < count; i++) {
                drugList.add(new ArrayList<Drug>());
                visiting.add(false);
            }

            String img = root.getString("image");
            byte[] image = Base64.decode(img, Base64.DEFAULT);
            final Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    imageview.setImageBitmap(bitmap);
                }
            });

            JSONArray jsonArray = root.getJSONArray("drugs");
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String j_drugShape = jsonObject.getString("ITEMSHAPE");
                String j_drugFrontColor = jsonObject.getString("FRONTCOLOR");
                String j_drugBackColor = jsonObject.getString("BACKCOLOR");
                String j_drugRatio = jsonObject.getString("RATIO");

                Drug drug = new Drug(j_drugShape, j_drugFrontColor, j_drugBackColor, j_drugRatio);
                drugs.add(drug);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void getJSONToDrugs(final int drugIndex) {
        Thread thread = new Thread(new Runnable() {
            String drugsJson;
            @Override
            public void run() {
                try {
                    URL url = new URL(SEARCH_URL+SEARCH_CAMERA_SHAPE);

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("drugShape", drugs.get(drugIndex).getDrug_shape());
                    jsonObject.accumulate("drugFrontColor", drugs.get(drugIndex).getDrug_frontColor());
                    jsonObject.accumulate("drugBackColor", drugs.get(drugIndex).getDrug_backColor());
                    jsonObject.accumulate("drugFrontText", "");
                    jsonObject.accumulate("drugBackText", "");
                    jsonObject.accumulate("drugRatio", drugs.get(drugIndex).getDrug_ratio());

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("Cache-Control", "no-cache");
                    httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    httpURLConnection.setRequestProperty("Accept", "text/html");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    bufferedWriter.write(jsonObject.toString());
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    httpURLConnection.connect();

                    int responseStatusCode = httpURLConnection.getResponseCode();

                    InputStream inputStream;
                    if (responseStatusCode == HttpURLConnection.HTTP_OK) inputStream = httpURLConnection.getInputStream();
                    else inputStream = httpURLConnection.getErrorStream();

                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder sb = new StringBuilder();
                    String line;

                    while((line = bufferedReader.readLine()) != null) sb.append(line);

                    drugsJson = sb.toString().trim();
                    Log.d("tag", drugsJson);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(jsonParserToDrug(drugsJson)) {
                    Message message = mHandler.obtainMessage(Common.LOAD_SUCCESS, drugsJson);
                    mHandler.sendMessage(message);
                }
            }
        });
        thread.start();
    }

    public boolean jsonParserToDrug(String jsonString) {
        if (jsonString == null) return false;

        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
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
                drugList.get(recyclerItem).add(drug);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setListView();
                    Message message = mHandler.obtainMessage(Common.LOAD_SUCCESS, "");
                    mHandler.sendMessage(message);
                }
            });
            visiting.set(recyclerItem, true);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_drug_text, null);
        builder.setView(view);
        Button OK = (Button)view.findViewById(R.id.buttonOk);
        Button cancel = (Button)view.findViewById(R.id.buttonCancel);
        final EditText frontText = (EditText)view.findViewById(R.id.editTextFrontText);
        final EditText backText = (EditText)view.findViewById(R.id.editTextBackText);

        final AlertDialog dialog = builder.create();
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    front = frontText.getText().toString();
                    back = backText.getText().toString();
                    dialog.dismiss();
                    frontText.setText(""); backText.setText("");
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                front = "";
                back = "";
                dialog.dismiss();
                frontText.setText(""); backText.setText("");
            }
        });
        dialog.show();
    }
}
