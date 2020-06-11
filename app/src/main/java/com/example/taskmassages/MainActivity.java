package com.example.taskmassages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.taskmassages.classes.Massage;
import com.example.taskmassages.settings.MySharePreferences;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.taskmassages.settings.Keys.KEY_PHONE;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private TextView addTask;
    private ListView taskToDoListView;
    ArrayList<Massage> arrayListOfTask = new ArrayList<>();
    AdapterForTaskList adapterForTaskList;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    MySharePreferences msp;
    private String phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        msp=new MySharePreferences(this);
        adapterForTaskList=new AdapterForTaskList(this,arrayListOfTask);
        taskToDoListView.setAdapter(adapterForTaskList);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        phoneNumber=msp.getString(KEY_PHONE,"0506332027");
        DatabaseReference myRef =rootRef.child(phoneNumber);
        myRef.addValueEventListener(addValueEventListener);
        addTask.setOnClickListener(goToAddNewTask);

        readFromMongo();/// put in button or other option
        readFromSQL();// put in button or other option

    }

    private void readFromSQL() {
        arrayListOfTask.clear();

        //read from SQL phoneNumner is the key
    }

    private void readFromMongo() {
        arrayListOfTask.clear();
        //read from mongo
        //read from mongo phoneNumner is the key

    }

    ValueEventListener addValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // This method is called once with the initial value and again
            // whenever data at this location is updated.
            arrayListOfTask.clear();
            for(DataSnapshot ds : dataSnapshot.getChildren()) {
                Massage arrival = ds.getValue(Massage.class);
                arrayListOfTask.add(arrival);
            }
            taskToDoListView.setAdapter(adapterForTaskList);

        }

        @Override
        public void onCancelled(DatabaseError error) {
            // Failed to read value
            Log.w("TAG", "Failed to read value.", error.toException());
        }
    };



    View.OnClickListener goToAddNewTask = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(MainActivity.this, CreateNewTask.class);
            startActivity(intent);
        }


    };

    private void findView() {
        taskToDoListView=findViewById(R.id.mainActivity_taskList_listView);
        addTask=findViewById(R.id.mainActivity_addNewTask_txt);
    }





    }

