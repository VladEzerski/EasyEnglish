package com.ezerski.vladislav.easyenglish.ui.creating.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.ezerski.vladislav.easyenglish.R;
import com.ezerski.vladislav.easyenglish.db.DataContract;
import com.ezerski.vladislav.easyenglish.db.Words;
import com.ezerski.vladislav.easyenglish.ui.main.activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class CreateWordsFragment extends Fragment implements View.OnClickListener {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private EditText etOriginalWord;
    private EditText etTranslatedWord;
    private ProgressBar prCreate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_words_creation, container, false);
        etOriginalWord = view.findViewById(R.id.et_original_word);
        etTranslatedWord = view.findViewById(R.id.et_translated_word);
        prCreate = view.findViewById(R.id.pb_create_fragment);
        return view;
    }

    public void createWords() {
        if (validData()) {
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            if (currentUser != null && !currentUser.isAnonymous()) {
                prCreate.setVisibility(View.VISIBLE);
                db.collection(DataContract.WordsDbAttributes.WORDS_COLLECTION_NAME)
                        .add(new Words(etOriginalWord.getText().toString(),
                                etTranslatedWord.getText().toString(),
                                new Date(),
                                "vlad",
                                currentUser.getUid(),
                                currentUser.getEmail()))
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isSuccessful()) {
                                    task.getResult().
                                            update(DataContract.WordsDbAttributes.FIELD_DOCUMENT_ID,
                                                    task.getResult().getId());
                                }
                            }
                        });
                exitToMainActivity();
            }
        }
    }

    private boolean validData() {
        boolean valid = true;
        if ((TextUtils.isEmpty(etOriginalWord.getText().toString()))) {
            etOriginalWord.setError("Enter word");
            valid = false;
        } else {
            etOriginalWord.setError(null);
        }
        if (TextUtils.isEmpty(etTranslatedWord.getText().toString())) {
            etTranslatedWord.setError("Enter word");
            valid = false;
        } else {
            etTranslatedWord.setError(null);
        }
        return valid;
    }

    private void exitToMainActivity() {
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        prCreate.setVisibility(View.GONE);
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

    }
}
