package com.example.snowwhite.codebot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class StudyInfo extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_info);
    }

    public void startPractiseBot(View view) {
        Log.d("Practise Bot Page", "Here");

        Intent intent = new Intent(this, PractiseBot.class);
        startActivity(intent);
    }


}
