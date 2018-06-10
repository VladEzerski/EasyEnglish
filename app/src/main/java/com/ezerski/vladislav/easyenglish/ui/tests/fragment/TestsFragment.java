package com.ezerski.vladislav.easyenglish.ui.tests.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ezerski.vladislav.easyenglish.R;
import com.ezerski.vladislav.easyenglish.db.DataContract;
import com.ezerski.vladislav.easyenglish.db.Words;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestsFragment extends Fragment implements View.OnClickListener {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private RelativeLayout relLayout;
    private TextView tvTestWord;
    private TextView tvTestResult;
    private EditText etAnswer;
    private Button btnAnswer;
    private Button btnNext;
    private List<Words> wordsList = new ArrayList<>();
    private Random random = new Random();
    private int number = 1;
    private int correctAnswers = 0;
    private int testsCount = 5;

    public TestsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tests, container, false);
        relLayout = view.findViewById(R.id.rel_layout);
        tvTestWord = view.findViewById(R.id.tv_test_word);
        tvTestResult = view.findViewById(R.id.tv_result);
        etAnswer = view.findViewById(R.id.et_answer);
        btnAnswer = view.findViewById(R.id.btn_answer);
        btnAnswer.setOnClickListener(this);
        btnNext = view.findViewById(R.id.btn_next);
        btnNext.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadWordsList();
    }

    public static List<Words> extractWords(QuerySnapshot queryDocumentSnapshot) {
        List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshot.getDocuments();
        List<Words> wordsList = new ArrayList<>();
        for (DocumentSnapshot doc : documentSnapshots) {
            Words words = new Words(
                    doc.getString(DataContract.WordsDbAttributes.FIELD_ORIGINAL_WORD),
                    doc.getString(DataContract.WordsDbAttributes.FIELD_TRANSLATED_WORD),
                    doc.getDate(DataContract.WordsDbAttributes.FIELD_DATE),
                    doc.getString(DataContract.WordsDbAttributes.FIELD_USER_NAME),
                    doc.getString(DataContract.WordsDbAttributes.FIELD_USER_ID),
                    doc.getString(DataContract.WordsDbAttributes.FIELD_USER_EMAIL)
            );
            wordsList.add(words);
        }
        return wordsList;
    }

    private void loadWordsList() {
        db
                .collection(DataContract.WordsDbAttributes.WORDS_COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            wordsList = extractWords(task.getResult());
                            if (wordsList != null && getContext() != null) {
                                getNewWord();
                            } else {
                                Log.e("MyLog", "Error load words!");
                            }
                        } else {
                            Log.e("MyLog", "Error load words!");
                        }
                    }
                });
    }

    private List<String> getTranslatedWordFromList() {
        List<String> translatedWords = new ArrayList<>();
        for (int i = 0; i < wordsList.size(); i++) {
            translatedWords.add(wordsList.get(i).getTranslatedWord());
        }
        return translatedWords;
    }

    private List<String> getOriginalWordFromList() {
        List<String> originalWords = new ArrayList<>();
        for (int i = 0; i < wordsList.size(); i++) {
            originalWords.add(wordsList.get(i).getOriginalWord());
        }
        return originalWords;
    }

    private void startTest() {
        String answer = etAnswer.getText().toString().trim();
        if (answer.equals(getTranslatedWordFromList().get(number))) {
            correctAnswers++;
            tvTestResult.setText("Верно!");
            relLayout.setBackgroundColor(Color.GREEN);
            btnNext.setVisibility(View.VISIBLE);
        } else {
            tvTestResult.setText("Не верно!");
            relLayout.setBackgroundColor(Color.RED);
            btnNext.setVisibility(View.VISIBLE);
        }
    }

    private void refreshTest() {
        relLayout.setBackgroundColor(Color.WHITE);
        tvTestResult.setText("");
        etAnswer.setText("");
        testsCount--;
        if (testsCount == 1) {
            btnNext.setText("Закончить тест");
        } else if (testsCount == 0) {
            endTest();
            return;
        }
        getNewWord();
    }

    private void getNewWord() {
        number = random.nextInt(wordsList.size());
        tvTestWord.setText(getOriginalWordFromList().get(number));
        btnNext.setVisibility(View.GONE);
    }

    private void endTest() {
        Fragment fragment = new TestResultFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("result", correctAnswers);
        fragment.setArguments(bundle);
        if (getFragmentManager() != null) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fl_tests, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_answer:
                startTest();
                break;
            case R.id.btn_next:
                refreshTest();
                break;
        }
    }
}