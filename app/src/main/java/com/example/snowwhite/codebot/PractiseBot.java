package com.example.snowwhite.codebot;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import static android.R.attr.button;

public class PractiseBot extends FragmentActivity {
    public static final String TAG = "CodeBot";
    private final Random random = new Random();
    String session_id = Long.toHexString(random.nextLong()) + Long.toHexString(random.nextLong())
            + Long.toHexString(random.nextLong());
    // EIYRHFKXVNNJHIV2GR4WDLW3FOAGJTRL
    // IWFL3ZGUYTQIL4KJAWY5GRLF7S2JZDIC
    private final String WIT_TOKEN = "MQB36LQOLR4DMQ5DYPHFSQNPH2FXQHOI";

    private RecyclerView mRecyclerView;
    private Button mButtonSend;
    private Button nxtTutBtn;
    private EditText mEditTextMessage;
    private ImageView mImageView;
    int responseCount = 0;
    int tutCount = 0;

    private ChatMessageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practise_bot);

        Intent intent = getIntent();
        tutCount = intent.getExtras().getInt("tutorial_next");

        AndroidNetworking.initialize(getApplicationContext());

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mButtonSend = (Button) findViewById(R.id.btn_send);
        nxtTutBtn = (Button) findViewById(R.id.nextTutBtn);
        mEditTextMessage = (EditText) findViewById(R.id.et_message);
        mImageView = (ImageView) findViewById(R.id.iv_image);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new ChatMessageAdapter(this, new ArrayList<ChatMessage>());
        mRecyclerView.setAdapter(mAdapter);

        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = mEditTextMessage.getText().toString();
                if (TextUtils.isEmpty(message)) {
                    return;
                }
                sendMessage(message);
                mEditTextMessage.setText("");
            }
        });

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage("python");
            }
        });

        nxtTutBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                //If the practise is not completed then user cannot go to the next tutorial
                AlertDialog.Builder builder = new AlertDialog.Builder(PractiseBot.this);
                if (responseCount != 0) {
                    Log.v("sendEmailButton_onClick", "Invalid input provided");
                    builder.setMessage("You have not completed the practise. Please retry!").show();
                    return;
                }

                else {
                    Intent activityChangeIntent = new Intent(PractiseBot.this, StudyInfo.class);
                    activityChangeIntent.putExtra("tutorial_next",tutCount);
                    PractiseBot.this.startActivity(activityChangeIntent);
                }


            }
        });
    }

    private void displayMessage(String message, boolean ownMessage, ChatMessage.MessageType type) {
        ChatMessage chatMessage = new ChatMessage(message, ownMessage, type);
        mAdapter.add(chatMessage);
        mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    private void setupChoice(String message, String... options) {
        ChatMessage chatMessage = new ChatMessage(message, false, ChatMessage.MessageType.CHOICE);
        chatMessage.setOptions(options);
        mAdapter.add(chatMessage);
        mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    private void chatToWit(String input) {
        String session_id = Long.toHexString(random.nextLong()) + Long.toHexString(random.nextLong())
                + Long.toHexString(random.nextLong());

        AndroidNetworking.post(String.format("https://api.wit.ai/converse?q=%s&session_id=%s", input, session_id))

                .addHeaders("Authorization", "Bearer " + WIT_TOKEN)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        responseCount = responseCount + 1;
                        Log.d(TAG, String.format("onResponse: %s", response));
                        try {
                            Log.d(TAG, "onResponse message: " + response.getString("msg"));
                            displayMessage(response.getString("msg"), false, ChatMessage.MessageType.NORMAL);
                        } catch (JSONException e) {
                            Log.e(TAG, "onResponse: ", e);
                            setupChoice("What you want?", "python", "java");
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, "onError", error);
                    }
                });
    }

    private void sendMessage(String message) {
        Log.d("This is it:", message);
        chatToWit(message);
        displayMessage(message, true, ChatMessage.MessageType.NORMAL);
    }

    public void onItemClicked(View view, LinearLayout options) {
        Log.d(TAG, String.format("onItemClicked: %s", ((Button) (view)).getText()));
        sendMessage(((Button) view).getText().toString());
        options.removeAllViews();
    }
}
