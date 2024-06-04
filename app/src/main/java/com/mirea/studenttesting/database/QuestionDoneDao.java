package com.mirea.studenttesting.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mirea.studenttesting.entity.QuestionDone;

import java.util.List;

@Dao
public interface QuestionDoneDao {
    @Query("SELECT * FROM question_done")
    List<QuestionDone> getAll();

    @Query("DELETE FROM question_done")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<QuestionDone> questionDone);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(QuestionDone questionDone);

    @Delete
    void delete(QuestionDone questionDone);
}