package com.example.simplelogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.ACTION_OPEN_DOCUMENT;
import static android.content.Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION;
import static com.example.simplelogin.MainActivity.itemList;

public class AddNoteActivity extends AppCompatActivity {

    private EditText noteTitle, noteDesc;
    private AppCompatButton addBtn;
    String title="",description="",img="";

    Button select, previous, next;
    ImageSwitcher imageView;
    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    TextView total;
    ArrayList<Uri> mArrayUri;
    int position = 0;
    boolean clicked=false;
    List<String> imagesEncodedList;
    Uri imguri=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        noteTitle=findViewById(R.id.noteTitle);
        noteDesc=findViewById(R.id.noteDesc);
        addBtn=findViewById(R.id.addBtn);

        select = findViewById(R.id.select);
        total = findViewById(R.id.text);
        imageView = findViewById(R.id.image);
        previous = findViewById(R.id.previous);
        mArrayUri = new ArrayList<Uri>();

        // showing all images in imageswitcher
        imageView.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView1 = new ImageView(getApplicationContext());
                return imageView1;
            }
        });
        next = findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < mArrayUri.size()-1) {
                    // increase the position by 1
                    next.setEnabled(true);
                    position++;
                    imageView.setImageURI(mArrayUri.get(position));

                    if(position==9) {
                        Toast.makeText(AddNoteActivity.this, "Max limit of 10 images reached!", Toast.LENGTH_SHORT).show();
                        next.setEnabled(false);
                        select.setEnabled(false);
                    }
                } else {
                    Toast.makeText(AddNoteActivity.this, "Last Image Already Shown", Toast.LENGTH_SHORT).show();
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position > 0) {
                    // decrease the position by 1
                    position--;
                    imageView.setImageURI(mArrayUri.get(position));
                    next.setEnabled(true);
                }
            }
        });

        imageView = findViewById(R.id.image);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // initialising intent
                clicked=true;
                if(position==0){
                    Intent intent = new Intent();

                    // setting type to select to be image
                    intent.setType("image/*");

                    // allowing multiple image to be selected

                    intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                            | Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                            | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
                }

                if (position < mArrayUri.size()-1) {
                    // increase the position by 1
                    position++;

                    if (position == 9) {
                        Toast.makeText(AddNoteActivity.this, "Max limit of 10 images reached!", Toast.LENGTH_SHORT).show();
                        next.setEnabled(false);
                        select.setEnabled(false);

                    }
                    }
                }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = noteTitle.getText().toString();
                description = noteDesc.getText().toString();
                img=imguri.toString();

                if (title.length() < 5 || title.length() > 10) {
                    noteTitle.setError("Title should be between 5-100 characters");
                    Toast.makeText(AddNoteActivity.this, "Title not in range", Toast.LENGTH_SHORT).show();
                } else if (description.length() < 100 || description.length() > 1000) {
                    noteDesc.setError("Description should be between 100-1000 characters");
                    Toast.makeText(AddNoteActivity.this, "Description not in range", Toast.LENGTH_SHORT).show();
                } else if(clicked==false){
                    Toast.makeText(AddNoteActivity.this, "Add images first!", Toast.LENGTH_SHORT).show();
                } else{
                    itemList.add(new Item(title, description,img));
                    // notifying adapter when new data added.
                    MainActivity.mAdapter.notifyItemInserted(itemList.size());

                    SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

                    // creating a variable for editor to
                    // store data in shared preferences.
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    // creating a new variable for gson.
                    Gson gson = new Gson();

                    // getting data from gson and storing it in a string.
                    String json = gson.toJson(itemList);

                    // below line is to save data in shared
                    // prefs in the form of string.
                    editor.putString("items", json);

                    // below line is to apply changes
                    // and save data in shared prefs.
                    editor.apply();

                    startActivity(new Intent(AddNoteActivity.this, MainActivity.class));
                    Toast.makeText(AddNoteActivity.this, "Note added successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // When an Image is picked
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
            // Get the Image from data
            if (data.getClipData()!=null) {
//                    ClipData mClipData = data.getClipData();
//                    int cout = mClipData.getItemCount();
//                    Log.i("cout:", Integer.toString(cout));
//                    Toast.makeText(this, "cout: " + cout, Toast.LENGTH_SHORT).show();
                    for (int i = 0; i <10; i++) {
                        // adding imageuri in array
                        Uri imageurl = data.getClipData().getItemAt(i).getUri();
                        mArrayUri.add(imageurl);
                    }
                    // setting 1st selected image into image switcher
                    imageView.setImageURI(mArrayUri.get(0));
                    position = 0;

                } else {
                    Uri imageurl = data.getData();
                    mArrayUri.add(imageurl);
                    imguri=mArrayUri.get(0);
                    imageView.setImageURI(mArrayUri.get(0));
                    position = 0;
                }
        } else {
            // show this if no image is selected
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

}