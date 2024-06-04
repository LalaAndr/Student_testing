package com.mirea.studenttesting.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.mirea.studenttesting.entity.Question;
import com.mirea.studenttesting.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FirebaseControll {

    public interface DataListener<T> {
        void data(T t);

        default void error(String error) {
        }
    }

    private final FirebaseAuth fbAuthReference;
    private final FirebaseFirestore mDat;

    public FirebaseControll() {
        this.fbAuthReference = FirebaseAuth.getInstance();
        this.mDat = FirebaseFirestore.getInstance();
    }

    //Регистрация пользователя в FireBase
    public void registration(@NonNull String email, @NonNull String password,
                             DataListener<String> dataListener) {
        fbAuthReference.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        dataListener.data(Objects.requireNonNull(task.getResult().getUser())
                                .getUid());
                    } else {
                        if (task.getException() != null) {
                            dataListener.error(task.getException().getMessage());
                        } else {
                            dataListener.error(Constants.APP_ERROR_NETWORK_UNENABLE);
                        }
                    }
                });
    }

    //Вход пользователя в FireBase
    public void signIn(@NonNull String email, @NonNull String password,
                       DataListener<String> dataListener) {
        fbAuthReference.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                dataListener.data(Objects.requireNonNull(task.getResult().getUser()).getUid());
            } else {
                if (task.getException() != null) {
                    dataListener.error(task.getException().getMessage());
                } else {
                    dataListener.error(Constants.APP_ERROR_NETWORK_UNENABLE);
                }
            }
        });
    }

    public void readQuestions(DataListener<List<Question>> dataListener) {
        List<Question> tempMathList = new ArrayList<>();
        List<Question> tempInformaticsList = new ArrayList<>();
        mDat.collection("Questions")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if ("Math".equals(document.getString("theme"))) {
                                tempMathList.add(document.toObject(Question.class));
                            } else if ("Informatics".equals(document.getString("theme"))) {
                                tempInformaticsList.add(document.toObject(Question.class));
                            }
                        }
                        Collections.shuffle(tempMathList);
                        Collections.shuffle(tempInformaticsList);
                        List<Question> resultList = tempMathList.stream().limit(4).collect(Collectors.toList());
                        resultList.addAll(tempInformaticsList.stream().limit(4)
                                .collect(Collectors.toList()));
                        dataListener.data(resultList);
                    } else {
                        Log.d("TAG", "Error getting documents: ", task.getException());
                    }
                });



//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                value.getDocuments()
//                tempMathList.add(value.getDocuments().getValue(Question.class));
//
//            }

//            @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        System.out.println(snapshot.getValue().toString());
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//
//        mDat.collection(Constants.PATH_TO_SUBJECTS)
//                .child(Constants.PATH_TO_INFORMATICS).child(Constants.PATH_TO_QUESTIONS)
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        System.out.println(snapshot.getValue().toString());
//                        tempInformaticsList.add(snapshot.getValue(Question.class));
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
    }
}