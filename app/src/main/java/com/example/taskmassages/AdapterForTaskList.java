package com.example.taskmassages;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.taskmassages.classes.Massage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AdapterForTaskList extends BaseAdapter {
    private Context context;
    private ArrayList<Massage> arrayList;
    private TextView content, name;
    private ImageView imageView;
    private StorageReference mStorageRef;
    private final int PICK_IMAGE_REQUEST = 22;
    private Uri filePath;
    public AdapterForTaskList(Context context, ArrayList<Massage> arrayList) {
        mStorageRef = FirebaseStorage.getInstance().getReference();
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        filePath = Uri.fromFile(new File("images/"));
        Massage massage = arrayList.get(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.task_from_other_person, parent, false);
        content = convertView.findViewById(R.id.messageBody_textView_taskFromOtherPerson);
        name = convertView.findViewById(R.id.name_textView_taskFromOtherPerson);
        imageView=convertView.findViewById(R.id.avatar_imageView_taskFromOtherPerson);
        StorageReference riversRef = mStorageRef.child("images/").child(massage.getPhotoPath());

        name.setText(" " + arrayList.get(position).getSenderName());
        content.setText(" " +arrayList.get(position).getTextMassage());
        try {
            final File localFile = File.createTempFile("Images", "jpeg");
            riversRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Setting image on image view using Bitmap

                            Glide.with(context).
                                    load(BitmapFactory.decodeFile(localFile.getAbsolutePath())).into(imageView);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle failed download
                    // ...
                }
            });
        } catch (IOException e) {
            // Log the exception
            e.printStackTrace();
        }
        return convertView;
    }
}
