package com.mirea.studenttesting.screen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.mirea.studenttesting.R;
import com.mirea.studenttesting.app.App;
import com.mirea.studenttesting.databinding.FragmentAuthBinding;
import com.mirea.studenttesting.firebase.FirebaseControll;

public class AuthFragment extends Fragment {
    private FragmentAuthBinding binding;
    private FirebaseControll firebaseControll = new FirebaseControll();
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        App.questionDoneDao().deleteAll();
        binding.bSignUp.setOnClickListener(v -> {
            sendCredentials(binding.edEmail.getText().toString(), binding.edPassword.getText().toString(), REGISRTATION);
        });
        binding.bSignIn.setOnClickListener(v -> sendCredentials(binding.edEmail.getText().toString(),
                binding.edPassword.getText().toString(), AUTH));
        sharedPreferences = getActivity().getSharedPreferences("auth", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", null);
        String password = sharedPreferences.getString("password", null);
        System.out.println(email + " " + password);
        if (email != null && password != null) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_authFragment_to_questionsListFragment);
        }
    }
    private String areFieldsValid(String email, String password) {
        System.out.println(email + " " + password);
        if (email.isEmpty() || password.isEmpty()) {
            return "Нужно заполнить поле";
        } else if (!isValidEmail(email)) {
            return "Пожалуйста, введите валидный email";
        } else {
            return null;
        }
    }

    private void sendCredentials(String email, String password, String param) {
        String validationError = areFieldsValid(email, password);
        if (validationError != null) {
            Toast.makeText(getContext(), validationError, Toast.LENGTH_SHORT).show();
            return;
        }
        if (param.equals(REGISRTATION)) {
            firebaseControll.registration(email, password, s -> {
                saveAuthData(email, password);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_authFragment_to_questionsListFragment);
            });
        } else {
            firebaseControll.signIn(email, password, s -> {
                saveAuthData(email, password);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_authFragment_to_questionsListFragment);
            });
        }
    }
    private void saveAuthData(String email, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }

    private static final String REGISRTATION = "reg";
    private static final String AUTH = "auth";
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

}