package com.mirea.studenttesting.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.mirea.studenttesting.entity.Question;
import com.mirea.studenttesting.entity.QuestionDone;

@Database(entities = {Question.class, QuestionDone.class}, version = 1)
@TypeConverters(Converter.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract QuestionDao questionDao();

    public abstract QuestionDoneDao questionDoneDao();
}