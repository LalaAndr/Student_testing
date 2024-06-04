package com.mirea.studenttesting.app;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.mirea.studenttesting.database.AppDatabase;
import com.mirea.studenttesting.database.QuestionDao;
import com.mirea.studenttesting.database.QuestionDoneDao;

public class App extends Application {

    private static App app;
    private static AppDatabase db;


    //pickImageFrom

    public static Context getAppContext() {
        return app.getApplicationContext();
    }

    public static QuestionDao questionDao() {
        return db.questionDao();
    }

    public static QuestionDoneDao questionDoneDao() {
        return db.questionDoneDao();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;
        db = Room.databaseBuilder(getAppContext(),
                AppDatabase.class, "question-database").allowMainThreadQueries().build();
    }
}