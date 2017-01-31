package com.example.snowwhite.codebot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Main Page", "Here");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startLearning(View view) {
        Log.d("StudyInfo Page", "Here");

        Intent intent = new Intent(this, StudyInfo.class);
        startActivity(intent);
    }

}
