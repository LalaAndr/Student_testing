package com.mirea.studenttesting.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mirea.studenttesting.entity.Question;

import java.util.List;

@Dao
public interface QuestionDao {
    @Query("SELECT * FROM question")
    List<Question> getAll();

    @Query("SELECT * FROM question WHERE questionId IN (:questionId)")
    List<Question> loadAllByIds(int questionId);

    @Query("SELECT * FROM question ORDER BY RANDOM() LIMIT 1")
    Question getRandomQuestion();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Question> questions);

    @Delete
    void delete(Question question);

    @Query("DELETE FROM question")
    void deleteAll();
}