package com.example.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class QuizDataHandler {

    private static final String TAG = "QuizDataHandler";
    private SQLiteDatabase database;
    private DBHelper dbHelper;

    public QuizDataHandler(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insertQuizQuestion(String question, String answer) {
        ContentValues values = new ContentValues();
        values.put("question", question);
        values.put("answer", answer);

        long newRowId = database.insert("quiz", null, values);

        if (newRowId != -1) {
            Log.d(TAG, "Inserted row with ID: " + newRowId);
            Log.d(TAG, "Question: " + question);
            Log.d(TAG, "Answer: " + answer);
        } else {
            Log.e(TAG, "Failed to insert data into the database.");
        }

        return newRowId;
    }


    public Cursor getQuizData() {
        String[] projection = {"question", "answer"};
        Cursor cursor = database.query("quiz", projection, null, null, null, null, null);
        return cursor;
    }

    // Method to retrieve all questions and answers
    public Cursor getAllQuestionsAndAnswers() {
        String[] projection = {"question", "answer"};
        return database.query("quiz", projection, null, null, null, null, null);
    }


}
