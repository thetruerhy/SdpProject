package com.example.crysis_pc.handwritingcapture;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class PreviewActivity extends AppCompatActivity {

    String out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        Bundle extras = getIntent().getExtras();
        TessaractAPI api = TessaractAPI.getInstance();
        Bitmap bmp = (Bitmap) extras.get("data");
        TextView textView = (TextView) findViewById(R.id.preview);
        out = api.Interpret(bmp,getApplicationContext());
        textView.setText(out);

    }

    public void contProc(View view){
        finish();
    }
}
