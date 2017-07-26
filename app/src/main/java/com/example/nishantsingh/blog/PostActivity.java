package com.example.nishantsingh.blog;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PostActivity extends AppCompatActivity {
    private ImageButton imageButton;
    private static final int GALLERY_REQUEST=1;
    private EditText titleField;
    private EditText descField;
    private Button btnSubmit;
    private Uri uriImage=null;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        titleField=(EditText)findViewById(R.id.titleField);
        descField=(EditText)findViewById(R.id.descField);
        btnSubmit=(Button)findViewById(R.id.btnSubmit);
        imageButton=(ImageButton)findViewById(R.id.imageSelect);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent=new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("Image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPosting();
            }
        });
    }

    private void startPosting() {

        String title_val= titleField.getText().toString().trim();
        String desc_val= descField.getText().toString().trim();

        if(!TextUtils.isEmpty(title_val) && !TextUtils.isEmpty(desc_val) && uriImage!=null){

            StorageReference filePath=storageReference.child("Blog_Images").child(uriImage.getLastPathSegment());

            filePath.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    @SuppressWarnings("VisibleForTests") Uri downloadUri = taskSnapshot.getDownloadUrl();
                }
            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==GALLERY_REQUEST && resultCode==RESULT_OK){
            uriImage=data.getData();
            imageButton.setImageURI(uriImage);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}


