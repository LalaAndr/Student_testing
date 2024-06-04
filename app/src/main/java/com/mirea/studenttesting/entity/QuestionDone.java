package com.mirea.studenttesting.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "question_done")
public class QuestionDone {
    @PrimaryKey
    private int questionId;
    private String title;
    private String answer;
    private Boolean result;
    private String theme;

    public int getQuestionId() {
        return questionId;
    }

    public QuestionDone(int questionId, String title, String answer, Boolean result, String theme) {
        this.questionId = questionId;
        this.title = title;
        this.answer = answer;
        this.result = result;
        this.theme = theme;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
