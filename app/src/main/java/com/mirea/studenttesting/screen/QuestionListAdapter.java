package com.mirea.studenttesting.screen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirea.studenttesting.R;
import com.mirea.studenttesting.entity.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.ViewHolder> {

    List<Question> questions;
    QuestionListAdapter(){
        this.questions = new ArrayList<>();
    }

    @NonNull
    @Override
    public QuestionListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_list_item, parent, false);
        return new QuestionListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionListAdapter.ViewHolder holder, int position) {
        Question questionDone = questions.get(position);
        holder.questionTitle.setText(questionDone.getTitle());
        holder.questionTheme.setText(questionDone.getTheme());
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView questionTitle;
        private final TextView questionTheme;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTitle = itemView.findViewById(R.id.questionListTitle);
            questionTheme = itemView.findViewById(R.id.questionListTheme);
        }
    }

    public void updateQuestionList(final List<Question> userArrayList) {
        this.questions.clear();
        notifyItemChanged(1);
        this.questions.addAll(userArrayList);
        notifyItemRangeChanged(0, questions.size());
    }
}