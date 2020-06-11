package com.example.taskmassages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.taskmassages.classes.Massage;
import com.example.taskmassages.settings.MySharePreferences;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import static com.example.taskmassages.settings.Keys.KEY_NAME;
import static com.example.taskmassages.settings.Keys.KEY_PHONE;

public class CreateNewTask extends AppCompatActivity {

    private EditText phoneNumberEditText;
    private EditText taskMassageEditText;
    private Button sentTaskBtn;
    private Button addImageBtn;
    private final int PICK_IMAGE_REQUEST = 22;
    private Uri filePath;
    private ImageView showImage;
    private StorageReference storageReference;
    String urlPath = UUID.randomUUID().toString();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    private FirebaseAuth mAuth;
    private MySharePreferences msp;
   private String phoneNumber;
   private String senderName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_task);
        findView();
        msp=new MySharePreferences(this);
        sentTaskBtn.setOnClickListener(sendTask);
        mAuth = FirebaseAuth.getInstance();
        addImageBtn.setOnClickListener(selectImage);




    }

    private void SelectImage() {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST);
    }

    View.OnClickListener selectImage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SelectImage();
        }
    };


    View.OnClickListener sendTask = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FirebaseUser currentUser = mAuth.getCurrentUser();

            if(!phoneNumberEditText.getText().toString().equals("")&&!taskMassageEditText.getText().toString().equals("")) {
                uploadImage();

               phoneNumber= msp.getString(KEY_PHONE,"0506332027");
                senderName=msp.getString(KEY_NAME,"Ofer");
                Massage temp=new Massage(taskMassageEditText.getText().toString(), System.currentTimeMillis(),
                        phoneNumberEditText.getText().toString(),senderName,
                        phoneNumber, urlPath);

                myRef.child(phoneNumberEditText.getText().toString()).push().setValue(temp);
                taskMassageEditText.setText("");
                sendMassageMongo();
                sendMassageSQL();
            }

        }

        public void sendMassageMongo() {
            //ToDO:
            Massage temp=new Massage(taskMassageEditText.getText().toString(), System.currentTimeMillis(),
                    phoneNumberEditText.getText().toString(),senderName,
                    phoneNumber, urlPath);

            //add massage to mongo by key of phoneNumber


        }
        public void sendMassageSQL() {
            //ToDO:
            Massage temp=new Massage(taskMassageEditText.getText().toString(), System.currentTimeMillis(),
                    phoneNumberEditText.getText().toString(),senderName,
                    phoneNumber, urlPath);

            //add massage to sql by key of phoneNumber


        }


    };

    private void findView() {
        phoneNumberEditText=findViewById(R.id.createNewTask_phoneNumber_editText);
        taskMassageEditText=findViewById(R.id.createNewTask_massage_editText);
        sentTaskBtn=findViewById(R.id.createNewTask_sendTask_btn);
        addImageBtn=findViewById(R.id.createNewTask_addImage_btn);
        showImage=findViewById(R.id.imageView_createNewTask);
        storageReference = FirebaseStorage.getInstance().getReference();
    }


    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                showImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }




    // UploadImage method
    private void uploadImage() {
        if (filePath != null) {
            // Code for showing progressDialog while uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref = storageReference.child("images/").child(urlPath);
            Log.i("123123132", " " + ref);
            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Log.i("111111111", "succes");

                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Error, Image not uploaded
                            Log.i("111111111", "fail");

                            progressDialog.dismiss();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int) progress + "%");
                                    Log.i("111111111", "uploading");
                                }
                            });
        }
    }
}
