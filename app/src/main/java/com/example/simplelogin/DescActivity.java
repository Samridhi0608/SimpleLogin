package com.example.simplelogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DescActivity extends AppCompatActivity {

    TextView newTitle, newDesc;
    ImageView newImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc);

        newTitle=findViewById(R.id.newTitle);
        newDesc=findViewById(R.id.newDesc);
        newImg=findViewById(R.id.newImg);

        Intent intent=getIntent();
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        String title= intent.getStringExtra("newTitle");
        String desc= intent.getStringExtra("newDesc");
        String img= intent.getStringExtra("newImg");

        newTitle.setText(title);
        newDesc.setText(desc);
        newImg.setImageURI(Uri.parse(img));
    }
}