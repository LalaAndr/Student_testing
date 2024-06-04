package com.mirea.studenttesting.screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mirea.studenttesting.R;
import com.mirea.studenttesting.app.App;
import com.mirea.studenttesting.databinding.FragmentQuestionListBinding;
import com.mirea.studenttesting.entity.Question;
import com.mirea.studenttesting.firebase.FirebaseControll;

import java.util.ArrayList;

public class QuestionsListFragment extends Fragment {
    private FragmentQuestionListBinding binding;
    private final FirebaseControll fb = new FirebaseControll();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentQuestionListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        QuestionListAdapter adapter = new QuestionListAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        //binding.questionsList.setLayoutManager(linearLayoutManager);
        fb.readQuestions(collection -> {
            App.questionDao().deleteAll();
            App.questionDao().insertAll(collection);
            adapter.updateQuestionList(collection);
           // binding.questionsList.setAdapter(adapter);
            System.out.println(collection);
        });
        binding.startTest.setOnClickListener(v -> Navigation.findNavController(binding.getRoot()).navigate(R.id.action_questionsListFragment_to_qestionInfoFragment));
    }
}
