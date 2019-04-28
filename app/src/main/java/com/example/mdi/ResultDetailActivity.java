package com.example.mdi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;

public class ResultDetailActivity extends AppCompatActivity {

    TextView detailName;
    TextView detailType;
    TextView detailColor;
    TextView detailShape;
    TextView detailFrontText;
    TextView detailBackText;
    ImageView detailImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_detail);

        detailName = (TextView)findViewById(R.id.detailName);
        detailType = (TextView)findViewById(R.id.detailType);
        detailColor = (TextView)findViewById(R.id.detailColor);
        detailShape = (TextView)findViewById(R.id.detailShape);
        detailFrontText = (TextView)findViewById(R.id.detailFrontText);
        detailBackText = (TextView)findViewById(R.id.detailBackText);
        detailImage = (ImageView)findViewById(R.id.detailImage);

        Intent intent = getIntent();

        String drugId = intent.getStringExtra("drugId");
        String drugName = intent.getStringExtra("drugName");
        String drugImage = intent.getStringExtra("drugImage");
        String drugType = intent.getStringExtra("drugType");
        String drugShape = intent.getStringExtra("drugShape");
        String drugColor = intent.getStringExtra("drugColor");
        String drugFrontText = intent.getStringExtra("drugFrontText");
        String drugBackText = intent.getStringExtra("drugBackText");

        Drug drug = new Drug(drugId, drugName, drugImage, drugType, drugShape, drugColor, drugFrontText, drugBackText);

        setThread(drug);
    }

    public void setThread(final Drug drug) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(drug.getDrug_image());

                    URLConnection urlConnection = url.openConnection();
                    urlConnection.connect();
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
                    final Bitmap bitmap = BitmapFactory.decodeStream(bufferedInputStream);
                    bufferedInputStream.close();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            detailImage.setImageBitmap(bitmap);
                            detailName.setText(drug.getDrug_name());
                            detailType.setText(drug.getDrug_type());
                            detailColor.setText(drug.getDrug_color());
                            detailShape.setText(drug.getDrug_shape());
                            detailFrontText.setText(drug.getDrug_frontText());
                            detailBackText.setText(drug.getDrug_backText());
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
