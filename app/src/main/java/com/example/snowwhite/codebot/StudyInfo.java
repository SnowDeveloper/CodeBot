package com.example.snowwhite.codebot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class StudyInfo extends FragmentActivity {

    String val;
    private int tutCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_info);

        TextView textViewHeading = (TextView) findViewById(R.id.txtViewHeading);
        TextView textViewStudy = (TextView) findViewById(R.id.studyTextView);

        Intent intent = getIntent();
        tutCount = intent.getExtras().getInt("tutorial_next");

        //Based on the count, the specific tutorial gets loaded in the screen
        if (tutCount == 0) {
            String heading = "1.0 - What is Coding?";
            textViewHeading.setText(heading);
            String tut_1_0 = "The easiest way to explain what coding is, is to say that coding is " +
                    "typing in the code. \n\nCoding is like writing, but in a specific computer " +
                    "language. Programming means writing up your own code. It is like writing in " +
                    "foreign language.\n\nA programmer (or developer) is a person who writes a code." +
                    " \n\nA programmer is like a writer, who writes a book. \n\nPrograms are " +
                    "lines of code that is understandable for computers. So programs are books " +
                    "written in a computer language and computer is the reader of this code. ";
            textViewStudy.setText(tut_1_0);
            tutCount = tutCount + 1;
            Log.d("Tutorial Count: ","value: " + tutCount);
        }
        else if (tutCount == 1) {
            String heading = "1.1 - Why programming?";
            textViewHeading.setText(heading);
            String tut_1_1 = "Technology is becoming incredibly important throughout the world. " +
                    "\n\nDemand is increasing on a daily basis for programmers to create that " +
                    "\n\ntechnology. ";
            textViewStudy.setText(tut_1_1);
            tutCount = tutCount + 1;
            Log.d("Tutorial Count: ","value: " + tutCount);
        }
        else if (tutCount == 2) {
            String heading = "1.2 - Why Python?";
            textViewHeading.setText(heading);
            String tut_1_2 = "Python is a programming language that computers understand. \n\nIt is " +
                    "also a program which interprets other programs that are written in Python. " +
                    "\n\nBut we will discuss interpretation in the next chapter. ";
            textViewStudy.setText(tut_1_2);
            tutCount = tutCount + 1;
            Log.d("Tutorial Count: ","value: " + tutCount);
        }
        else if (tutCount == 3) {
            String heading = "1.3 - Print statement";
            textViewHeading.setText(heading);
            String tut_1_3 = "A print statement looks like this: print(\"Hi, my name is Cody!\") " +
                    "\n\nThis code prints the message Hi, my name is Cody! in the console. " +
                    "\n\nIt doesn’t do nothing much besides that but well, it’s something. " +
                    "\n\nBe sure to add the text you want to be printed between ( ) or \" \". " +
                    "\n\nAre you ready to revolutionize the tech industry? ";
            textViewStudy.setText(tut_1_3);
            tutCount = tutCount + 1;
            Log.d("Tutorial Count: ","value: " + tutCount);
        }
        else if (tutCount == 4) {
            String heading = "1.4 - If-Else statement";
            textViewHeading.setText(heading);
            String tut_1_4 = "The if-else statement determines the execution of code based on " +
                    "certain circumstances. \n\nThey are used very frequently in programming for " +
                    "making decisions. \n\nThe else part of the statement is executed only when " +
                    "the if part is not considered to be true. \n" +
                    "\n" +
                    "For example:\n" +
                    "if a == b: \n" +
                    "   print(\"The numbers are equal\") \n" +
                    "else: \n" +
                    "   print(\"The numbers are different\") ";
            textViewStudy.setText(tut_1_4);
            tutCount = tutCount + 1;
            Log.d("Tutorial Count: ","value: " + tutCount);
        }
        else if (tutCount == 5) {
            String heading = "1.5 - Assigning values";
            textViewHeading.setText(heading);
            String tut_1_5 = "A programmer can define a variable in their code and set it to be " +
                    "a specific value. \n\nFor example, you can assign the value of 2 to the " +
                    "variable a by typing a = 2, and now any time you need to access " +
                    "\nthe number 2, you can use the variable a instead. " +
                    "\n\nThere are very pracitcal uses for this that we will go into further as " +
                    "we learn more about programming. ";
            textViewStudy.setText(tut_1_5);
            tutCount = tutCount + 1;
            Log.d("Tutorial Count: ","value: " + tutCount);
        }
        else if (tutCount == 6) {
            String heading = "1.6 - = and ==";
            textViewHeading.setText(heading);
            String tut_1_6 = "Obviously you are accustomed to the equals sign =, but it has a " +
                    "specific use in Python. \n\nThe equals sign is used for assigning a value to " +
                    "a variable. \n\nFor example, n = 3. However, two equals signs do not perform " +
                    "the same action. Two equals signs are used for comparison. \n\nFor example, " +
                    "to determine if variable t is equal to 10, you would write t == 10. ";
            textViewStudy.setText(tut_1_6);
            tutCount = tutCount + 1;
            Log.d("Tutorial Count: ","value: " + tutCount);
        }
        else if (tutCount == 7) {
            String heading = "1.7 - Line order in programming";
            textViewHeading.setText(heading);
            String tut_1_7 = "At the beginning we mentioned that a program is written like a book, " +
                    "but using a programming language instead of a spoken language. \n\nAs such, " +
                    "the order of the lines in a program are important, just are they are in a " +
                    "book. \n\nIt would be very difficult to understand the expected organization " +
                    "of a book if the lines were not in their proper order. \n\nThe same is true" +
                    " for computer programs. \n\nPython programs run from beginning to end, so " +
                    "if you need to use a variable on line 100, you must have declared that " +
                    "variable before that point in the code. ";
            textViewStudy.setText(tut_1_7);
            tutCount = tutCount + 1;
            Log.d("Tutorial Count: ","value: " + tutCount);
        }
        else if (tutCount == 8) {
            String heading = "1.8 - Flashcards";
            textViewHeading.setText(heading);
            String tut_1_8 = "In completing tasks, it is important to note that they are " +
                    "organized logically by difficulty: flashcards, code output, fill-in the" +
                    " blanks, debugging, build it yourself. \n\nFor the best experience, " +
                    "you should complete the first tasks before continuing to the next.";
            textViewStudy.setText(tut_1_8);
            tutCount = tutCount + 1;
            Log.d("Tutorial Count: ","value: " + tutCount);
        }
        else{
            String heading = "completed! \n\nCongratulations!";
            textViewHeading.setText(heading);
            String tut_n = "----- You've now learned Python! Keep Learning More!! -----";
            textViewStudy.setText(tut_n);
        }
    }

    public void startPractiseBot(View view) {
        //On Button click, user is directed to the practise screen where the bot gives practise tests
        Log.d("Practise Bot Page", "Here");

        Intent intent = new Intent(this, PractiseBot.class);
        intent.putExtra("tutorial_next", tutCount);
        startActivity(intent);
    }


}
