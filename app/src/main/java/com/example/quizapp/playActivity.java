package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class playActivity extends AppCompatActivity {
    String[] question_list = new String[10];
    String[] choose_list = {
            "Java", "Python", "C++", "Swift",
            "Eclipse", "Android Studio", "Xcode", "Visual Studio",
            ".xml", ".java", ".apk", ".html",
            "It contains metadata about the app.", "It's the source code of the app.", "It's the app's icon.", "It's used for in-app purchases.",
            "A user's action in the app.", "A code library.", "A screensaver.", "A UI component.",
            "A physical Android device.", "A debugging tool.", "A virtual device for testing Android apps.", "A code optimization tool.",
            "It's a version control system.", "It's a database system.", "It's a programming language.", "It's a build automation tool.",
            "Using HTML and CSS.", "Through XML layout files.", "Writing code in binary.", "Using a drag-and-drop interface.",
            "They are used for sending emails.", "They are used for app notifications.", "They are used for inter-component communication.", "They are used for password encryption.",
            "It's an Android application key.", "It's an Android packaging kit.", "It's an Android program kernel.", "It's an Android framework.",
    };

    String[] correct_list = new String[10];

    TextView cpt_question , text_question;
    Button btn_choose1 , btn_choose2 , btn_choose3 , btn_choose4 , btn_next;

    int currentQuestion =  0  ;
    int scorePlayer =  0  ;
    boolean isclickBtn = false;
    String valueChoose = "";
    Button btn_click;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        // Create an instance of the QuizDataHandler
        QuizDataHandler quizDataHandler = new QuizDataHandler(this);

        // Open the database
        quizDataHandler.open();

        // Retrieve questions and answers
        Cursor cursor = quizDataHandler.getAllQuestionsAndAnswers();

        int index = 0;

        if (cursor != null) {
            while (cursor.moveToNext() && index < 10) {
                int questionIndex = cursor.getColumnIndex("question");
                int answerIndex = cursor.getColumnIndex("answer");

                if (questionIndex != -1 && answerIndex != -1) {
                    question_list[index] = cursor.getString(questionIndex);
                    correct_list[index] = cursor.getString(answerIndex);
                    index++;
                } else {
                    // Handle the case where a column is not found
                    Log.e("Column Not Found", "Question or Answer column not found in the cursor");
                }
            }

            // Close the database
            quizDataHandler.close();


            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_play);
            cpt_question = findViewById(R.id.cpt_question);
            text_question = findViewById(R.id.text_question);

            btn_choose1 = findViewById(R.id.btn_choose1);
            btn_choose2 = findViewById(R.id.btn_choose2);
            btn_choose3 = findViewById(R.id.btn_choose3);
            btn_choose4 = findViewById(R.id.btn_choose4);
            btn_next = findViewById(R.id.btn_next);
        }
        findViewById(R.id.image_back).setOnClickListener(
                a-> finish()
        );



        remplirData();
        btn_next.setOnClickListener(
                view -> {
                        if (isclickBtn){
                            isclickBtn = false;

                            if(!valueChoose.equals(correct_list[currentQuestion])){
                                Toast.makeText(playActivity.this , "Incorrect",Toast.LENGTH_LONG).show();
                                btn_click.setBackgroundResource(R.drawable.background_btn_erreur);

                            }else {
                                Toast.makeText(playActivity.this , "Correct",Toast.LENGTH_LONG).show();
                                btn_click.setBackgroundResource(R.drawable.background_btn_correct);

                                scorePlayer++;
                            }
                            new Handler().postDelayed(() -> {
                                if(currentQuestion!=question_list.length-1){
                                    currentQuestion = currentQuestion + 1;
                                    remplirData();
                                    valueChoose = "";
                                    btn_choose1.setBackgroundResource(R.drawable.background_btn_choose);
                                    btn_choose2.setBackgroundResource(R.drawable.background_btn_choose);
                                    btn_choose3.setBackgroundResource(R.drawable.background_btn_choose);
                                    btn_choose4.setBackgroundResource(R.drawable.background_btn_choose);

                                }else {
                                    Intent intent  = new Intent(playActivity.this , ResulteActivity.class);
                                    intent.putExtra("result" , scorePlayer);
                                    startActivity(intent);
                                    finish();
                                }

                            },2000);

                        }else {
                            Toast.makeText(playActivity.this ,  "Please select an answer",Toast.LENGTH_LONG).show();
                        }
                }
        );


    }

    void remplirData(){
        cpt_question.setText((currentQuestion+1) + "/" + question_list.length);
        text_question.setText(question_list[currentQuestion]);

        btn_choose1.setText(choose_list[4 * currentQuestion]);
        btn_choose2.setText(choose_list[4 * currentQuestion+1]);
        btn_choose3.setText(choose_list[4 * currentQuestion+2]);
        btn_choose4.setText(choose_list[4 * currentQuestion+3]);

    }

    public void ClickChoose(View view) {
        btn_click = (Button)view;

        if (isclickBtn) {
            btn_choose1.setBackgroundResource(R.drawable.background_btn_choose);
            btn_choose2.setBackgroundResource(R.drawable.background_btn_choose);
            btn_choose3.setBackgroundResource(R.drawable.background_btn_choose);
            btn_choose4.setBackgroundResource(R.drawable.background_btn_choose);
        }
        chooseBtn();


    }
    void chooseBtn(){

        btn_click.setBackgroundResource(R.drawable.background_btn_choose_color);
        isclickBtn = true;
        valueChoose = btn_click.getText().toString();
    }
}