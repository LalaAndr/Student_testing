package com.mirea.studenttesting.screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.mirea.studenttesting.R;
import com.mirea.studenttesting.app.App;
import com.mirea.studenttesting.databinding.FragmentQuestionInfoBinding;
import com.mirea.studenttesting.entity.Question;
import com.mirea.studenttesting.entity.QuestionDone;

public class QestionInfoFragment extends Fragment {
    private FragmentQuestionInfoBinding binding;
    private boolean result;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentQuestionInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Question question = App.questionDao().getRandomQuestion();
        binding.questionTitle.setText(question.getTitle());
        binding.answerOne.setText(question.getOptions().get(0));
        binding.answerTwo.setText(question.getOptions().get(1));
        binding.answerThree.setText(question.getOptions().get(2));
        binding.answerFour.setText(question.getOptions().get(3));

        binding.answersList.setOnCheckedChangeListener((radiogroup, id) -> {
            RadioButton radioButton = view.findViewById(id);
            result = question.getAnswer().equals(radioButton.getText().toString());
            App.questionDao().delete(question);
            QuestionDone questionDone = new QuestionDone(
                    question.getQuestionId(), question.getTitle(), question.getAnswer(),
                    result, question.getTheme()
            );
            App.questionDao().delete(question);
            App.questionDoneDao().insert(questionDone);

            if (!App.questionDao().getAll().isEmpty()) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.qestionInfoFragment);
            } else {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_qestionInfoFragment_to_resultInfoFragment);
            }
        });

    }
}
