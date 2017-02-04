package com.example.snowwhite.codebot;

import android.content.Intent;
import android.os.Handler;
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
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PractiseBot extends FragmentActivity {
    public static final String TAG = "CodeBot";
    private Handler handler;

    private enum State {ASK, ANSWER, HINT}

    private State state = State.ASK;
    private Question currentQuestion;

    private final Random random = new Random();
    String session_id = Long.toHexString(random.nextLong()) + Long.toHexString(random.nextLong())
            + Long.toHexString(random.nextLong());
    // EIYRHFKXVNNJHIV2GR4WDLW3FOAGJTRL
    // IWFL3ZGUYTQIL4KJAWY5GRLF7S2JZDIC
    // MQB36LQOLR4DMQ5DYPHFSQNPH2FXQHOI
    private final String WIT_TOKEN = "MQB36LQOLR4DMQ5DYPHFSQNPH2FXQHOI";

    private RecyclerView mRecyclerView;
    private Button nxtTutBtn;
    int responseCount = 0;
    int tutCount = 0;
    private int topicCounter = 0;
    private int questionCounter = 0;


    private ChatMessageAdapter mAdapter;
    private Questions questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practise_bot);

        handler = new Handler();

        Intent intent = getIntent();
        tutCount = intent.getExtras().getInt(getString(R.string.intent_next_tutorial_id));

        AndroidNetworking.initialize(getApplicationContext());

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        nxtTutBtn = (Button) findViewById(R.id.nextTutBtn);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new ChatMessageAdapter(this, new ArrayList<ChatMessage>());
        mRecyclerView.setAdapter(mAdapter);

        nxtTutBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent activityChangeIntent = new Intent(PractiseBot.this, StudyInfo.class);
                activityChangeIntent.putExtra(getString(R.string.intent_next_tutorial_id), tutCount);
                PractiseBot.this.startActivity(activityChangeIntent);
                //If the practise is not completed then user cannot go to the next tutorial
                // This doesnt work correctly, so currently disabled
//                AlertDialog.Builder builder = new AlertDialog.Builder(PractiseBot.this);
//                if (responseCount != 0) {
//                    builder.setMessage("You have not completed the practise. Please retry!").show();
//                    return;
//                } else {
//                }
            }
        });

        loadQuestions();
        onUserMessage("");
    }

    private void displayMessage(String message, boolean ownMessage, ChatMessage.MessageType type) {
        ChatMessage chatMessage = new ChatMessage(message, ownMessage, type);
        mAdapter.add(chatMessage);
        mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    private void setupChoice(String message, List<String> options) {
        ChatMessage chatMessage = new ChatMessage(message, false, ChatMessage.MessageType.CHOICE);
        chatMessage.setOptions(options);
        mAdapter.add(chatMessage);
        mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

//    private void chatToWit(String input) {
//        AndroidNetworking.post(String.format("https://api.wit.ai/converse?q=%s&session_id=%s", input, session_id))
//
//                .addHeaders("Authorization", "Bearer " + WIT_TOKEN)
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // do anything with response
//                        responseCount = responseCount + 1;
//                        Log.d(TAG, String.format("onResponse: %s", response));
//                        try {
//                            Log.d(TAG, "onResponse message: " + response.getString("msg"));
//                            displayMessage(response.getString("msg"), false, ChatMessage.MessageType.NORMAL);
//                        } catch (JSONException e) {
//                            Log.e(TAG, "onResponse: ", e);
//                            setupChoice("What you want?", "python", "java");
//                        }
//                    }
//
//                    @Override
//                    public void onError(ANError error) {
//                        // handle error
//                        Log.e(TAG, "onError", error);
//                    }
//                });
//    }

    private void sendMessage(final String message) {
//        chatToWit(message);
        displayMessage(message, true, ChatMessage.MessageType.NORMAL);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onUserMessage(message);
            }
        }, 400);
    }

    private void onUserMessage(String message) {
        switch (state) {
            case ASK:
                currentQuestion = getPythonQuestion();
                if (currentQuestion.options != null) {
                    setupChoice(currentQuestion.question, currentQuestion.options);
                } else {
                    displayMessage(currentQuestion.question, false, ChatMessage.MessageType.NORMAL);
                }
                state = State.ANSWER;
                break;
            case ANSWER:
            case HINT:
                if (message.equals(currentQuestion.answer)) {
                    displayMessage("Correct!", false, ChatMessage.MessageType.NORMAL);
                    state = State.ASK;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onUserMessage("");
                        }
                    }, 1000);
                } else if (state == State.ANSWER) {
                    if (currentQuestion.options != null) {
                        setupChoice("Hint: " + currentQuestion.hint, currentQuestion.options);
                    } else {
                        displayMessage("Hint: " + currentQuestion.hint, false, ChatMessage.MessageType.NORMAL);
                    }
                    state = State.HINT;
                } else {
                    displayMessage("The answer was " + currentQuestion.answer, false, ChatMessage.MessageType.NORMAL);
                    state = State.ASK;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onUserMessage("");
                        }
                    }, 400);
                }
                break;
        }
    }

    public void onItemClicked(View view, Button button) {
        Log.d(TAG, String.format("onItemClicked: %s", ((Button) (view)).getText()));
        sendMessage(((Button) view).getText().toString());
        currentQuestion.options.remove(((Button) (view)).getText());
//        options.removeAllViews();
    }

    private void loadQuestions() {
        InputStream jsonFile = getResources().openRawResource(R.raw.questions);

        Gson gson = new Gson();
        questions = gson.fromJson(new BufferedReader(new InputStreamReader(jsonFile)), Questions.class);

        try {
            jsonFile.close();
        } catch (IOException e) {
            Log.e(TAG, "loadQuestions: close", e);
        }
        Log.d(TAG, "loadQuestions: ");
    }

    private Question getPythonQuestion() {
        try {
            Language language = questions.python.get(topicCounter);
            try {
                Question question = language.questions.get(questionCounter);
                questionCounter++;
                return question;
            } catch (IndexOutOfBoundsException e) {
                topicCounter++;
                questionCounter = 0;
                return getPythonQuestion();
            }
        } catch (IndexOutOfBoundsException e) {
            Log.e(TAG, "getPythonQuestion: no more questions left");
            Question question = new Question();
            question.answer = "";
            question.hint = "";
            question.question = "Congrats! \n You've finished the Practice. \n Please click on button -> " +
                    "Go To Next Tutorial ";
            //Re-direct to the next tutorial

            return question;
        }
    }

    private class Questions {
        List<Language> python;
        List<Language> ruby;
    }

    private class Language {
        List<Question> questions;
        String topic, notes;
    }

    private class Question {
        String question, hint, answer;
        List<String> options;
    }
}
