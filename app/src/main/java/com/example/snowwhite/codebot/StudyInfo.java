package com.example.snowwhite.codebot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class StudyInfo extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_info);

        TextView textView = (TextView) findViewById(R.id.studyTextView);
        textView.setText("The easiest way to explain what coding is, is to say that coding is " +
                "typing in the code. \n\nCoding is like writing, but in a specific computer language. " +
                "Programming means writing up your own code. It is like writing in foreign language." +
                "\n\nA programmer (or developer) is a person who writes a code. \n\nA programmer is like " +
                "a writer, who writes a book. \n\nPrograms are lines of code that is understandable " +
                "for computers. So programs are books written in a computer language and computer " +
                "is the reader of this code. ");


    }

    public void startPractiseBot(View view) {
        Log.d("Practise Bot Page", "Here");

        Intent intent = new Intent(this, PractiseBot.class);
        startActivity(intent);
    }


}
