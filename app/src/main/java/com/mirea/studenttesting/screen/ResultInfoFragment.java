package com.mirea.studenttesting.screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mirea.studenttesting.app.App;
import com.mirea.studenttesting.databinding.FragmentResultInfoBinding;
import com.mirea.studenttesting.entity.QuestionDone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultInfoFragment extends Fragment {
    private FragmentResultInfoBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentResultInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<QuestionDone> questionDone = App.questionDoneDao().getAll();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        QuestionDoneListAdapter adapter = new QuestionDoneListAdapter();
        binding.resultRecyclerView.setLayoutManager(linearLayoutManager);
        binding.resultRecyclerView.setAdapter(adapter);
        adapter.updateQuestionList((ArrayList<QuestionDone>) questionDone);

        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        long correctAnswers = questionDone.stream().filter(QuestionDone::getResult).count();

        Map<String, Object> result = new HashMap<>();
        result.put("correctAnswers", correctAnswers);

        FirebaseFirestore.getInstance().collection("results").document(userEmail).set(result)
                .addOnSuccessListener(documentReference -> {
                    System.out.println("DocumentSnapshot added");
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error adding document" + e);
                });
    }
}