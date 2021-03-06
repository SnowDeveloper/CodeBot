package com.example.snowwhite.codebot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "CodeBot";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Here");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startLearning(View view) {
        //Leading to the Tutorial page
        Log.d(TAG, "Here");

        Intent intent = new Intent(this, StudyInfo.class);
        intent.putExtra(getString(R.string.intent_next_tutorial_id), 0);
        startActivity(intent);
    }

}
