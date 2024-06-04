package com.mirea.studenttesting.screen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirea.studenttesting.R;
import com.mirea.studenttesting.entity.QuestionDone;

import java.util.ArrayList;
import java.util.List;

public class QuestionDoneListAdapter extends RecyclerView.Adapter<QuestionDoneListAdapter.ViewHolder> {

    List<QuestionDone> questions;

    QuestionDoneListAdapter() {
        this.questions = new ArrayList<>();
    }

    @NonNull
    @Override
    public QuestionDoneListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_done_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionDoneListAdapter.ViewHolder holder, int position) {
        QuestionDone questionDone = questions.get(position);
        holder.questionTitle.setText(questionDone.getTitle());
        holder.questionAnswer.setText(questionDone.getAnswer());
        if (questionDone.getResult()) {
            holder.questionResult.setImageResource(R.drawable.baseline_assignment_turned_in_24);
        } else {
            holder.questionResult.setImageResource(R.drawable.baseline_block_24);
        }
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView questionTitle;
        private final TextView questionAnswer;

        private final ImageView questionResult;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTitle = itemView.findViewById(R.id.questionDoneTitle);
            questionAnswer = itemView.findViewById(R.id.questionDoneAnswer);
            questionResult = itemView.findViewById(R.id.resultImg);
        }
    }

    public void updateQuestionList(final ArrayList<QuestionDone> userArrayList) {
        this.questions.clear();
        notifyItemChanged(1);
        this.questions.addAll(userArrayList);
        notifyItemRangeChanged(0, questions.size());
    }
}