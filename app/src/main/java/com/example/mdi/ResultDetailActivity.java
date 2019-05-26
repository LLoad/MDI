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
    TextView detailTemper;
    TextView detailFrontColor;
    TextView detailBackColor;
    TextView detailShape;
    TextView detailFrontText;
    TextView detailBackText;
    TextView detailLongSize;
    TextView detailShortSize;
    ImageView detailImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_detail);

        detailName = (TextView)findViewById(R.id.detailName);
        detailType = (TextView)findViewById(R.id.detailType);
        detailTemper = (TextView)findViewById(R.id.detailTemper);
        detailShape = (TextView)findViewById(R.id.detailShape);
        detailFrontColor = (TextView)findViewById(R.id.detailFrontColor);
        detailBackColor = (TextView)findViewById(R.id.detailBackColor);
        detailFrontText = (TextView)findViewById(R.id.detailFrontText);
        detailBackText = (TextView)findViewById(R.id.detailBackText);
        detailLongSize = (TextView)findViewById(R.id.detailLongSize);
        detailShortSize = (TextView)findViewById(R.id.detailShortSize);
        detailImage = (ImageView)findViewById(R.id.detailImage);

        Intent intent = getIntent();

        String drugName = intent.getStringExtra("drugName");
        String drugImage = intent.getStringExtra("drugImage");
        String drugType = intent.getStringExtra("drugType");
        String drugTemper = intent.getStringExtra("drugTemper");
        String drugShape = intent.getStringExtra("drugShape");
        String drugFrontColor = intent.getStringExtra("drugFrontColor");
        String drugBackColor = intent.getStringExtra("drugBackColor");
        String drugFrontText = intent.getStringExtra("drugFrontText");
        String drugBackText = intent.getStringExtra("drugBackText");
        String drugLongSize = intent.getStringExtra("drugLongSize");
        String drugShortSize = intent.getStringExtra("drugShortSize");

        Drug drug = new Drug(drugName, drugImage, drugType,drugShape, drugTemper, drugFrontColor, drugBackColor, drugFrontText, drugBackText, drugLongSize, drugShortSize);

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
                            detailFrontColor.setText(drug.getDrug_frontColor());
                            detailBackColor.setText(drug.getDrug_backColor());
                            detailShape.setText(drug.getDrug_shape());
                            detailTemper.setText(drug.getDrug_temper());
                            detailFrontText.setText(drug.getDrug_frontText());
                            detailBackText.setText(drug.getDrug_backText());
                            detailLongSize.setText(drug.getDrug_longSize());
                            detailShortSize.setText(drug.getDrug_shortSize());
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
