package com.example.snowwhite.codebot;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class PractiseBot extends FragmentActivity {
    public static final String TAG = "CodeBot";
    private final Random random = new Random();
    private final String WIT_TOKEN = "EIYRHFKXVNNJHIV2GR4WDLW3FOAGJTRL";

    private EditText editText;
    private ViewGroup messageHolder;
    private ImageView myMsgBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practise_bot);

        AndroidNetworking.initialize(getApplicationContext());

        editText = (EditText) findViewById(R.id.userInput);
        messageHolder = (ViewGroup) findViewById(R.id.message_holder);
        myMsgBtn = (ImageView) findViewById(R.id.sendUserInput);
        myMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform action on click
                chatToWit(editText.getText().toString());
                Log.d(TAG, String.format("sendUserInput: %s", editText.getText().toString()));
            }
        });

    }

    private void displayBotMessage(String message) {
        TextView textView = new TextView(this);
        textView.setText(message);
        textView.setPadding(16, 26, 16, 16);
        textView.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
        messageHolder.addView(textView);
    }

    private void chatToWit(String input) {
        String session_id = Long.toHexString(random.nextLong()) + Long.toHexString(random.nextLong())
                + Long.toHexString(random.nextLong());
        AndroidNetworking.post(String.format("https://api.wit.ai/converse?q=%s&session_id=%s",
                input, session_id))

                .addHeaders("Authorization", "Bearer " + WIT_TOKEN)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        try {
                            Log.d(TAG, "onResponse message: " + response.getString("msg"));
                            displayBotMessage(response.getString("msg"));
                        } catch (JSONException e) {
                            Log.d(TAG, String.format("onResponse: %s", response));
                            Log.e(TAG, "onResponse: ", e);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.d(TAG, String.format("onError: %s", error));
                    }
                });
    }

}
