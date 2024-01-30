package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase(); // or dbHelper.getReadableDatabase();

        // Drop the table if it exists
        db.execSQL("DROP TABLE IF EXISTS quiz");

        // Create your database table here
        db.execSQL("CREATE TABLE IF NOT EXISTS quiz (id INTEGER PRIMARY KEY AUTOINCREMENT, question TEXT, answer TEXT)");

        // Create an instance of the QuizDataHandler
        QuizDataHandler quizDataHandler = new QuizDataHandler(this);

        // Open the database
        quizDataHandler.open();



        // Arrays of questions and answers
        String[] questions = {
                "What programming language is commonly used for Android app development?",
                "What is the main integrated development environment (IDE) for Android development?",
                "What file format is used for Android app layouts?",
                "What is the purpose of the AndroidManifest.xml file in an Android app?",
                "What is an 'Activity' in Android app development?",
                "What is the purpose of the 'Android Emulator'?",
                "What is the 'Gradle' build system used for in Android development?",
                "How do you handle user interface layouts in Android apps?",
                "What is the role of 'Intents' in Android?",
                "What is the Android 'APK' file, and what does it contain?"
        };

        String[] answers = {
                "Java",
                "Android Studio",
                ".xml",
                "It contains metadata about the app.",
                "A user's action in the app.",
                "A virtual device for testing Android apps.",
                "It's a build automation tool.",
                "Through XML layout files.",
                "They are used for inter-component communication.",
                "It's an Android packaging kit."
        };

        // Insert questions and answers into the database
        for (int i = 0; i < questions.length; i++) {
            long newRowId = quizDataHandler.insertQuizQuestion(questions[i], answers[i]);

            // Log the result
            if (newRowId != -1) {
                Log.d("Insertion Result", "Data inserted successfully with ID: " + newRowId);
                Log.d("Question", questions[i]);
                Log.d("Answer", answers[i]);
            } else {
                Log.e("Insertion Result", "Failed to insert data into the database.");
            }
        }

        // Close the database
        quizDataHandler.close();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void main_btn(View view) {
        switch (view.getId()){
            case R.id.btn_play:
                startActivity(new Intent(MainActivity.this , playActivity.class));
                break;
            case R.id.btn_setting:
                startActivity(new Intent(MainActivity.this , settingActivity.class));

                break;
            case R.id.btn_exit:
                this.finishAffinity();
                break;
        }
    }
}