package com.ezerski.vladislav.easyenglish.ui.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ezerski.vladislav.easyenglish.FirebaseModelParsingException;
import com.ezerski.vladislav.easyenglish.R;
import com.ezerski.vladislav.easyenglish.db.DataContract;
import com.ezerski.vladislav.easyenglish.db.Words;
import com.ezerski.vladislav.easyenglish.ui.creating.activity.CreateActivity;
import com.ezerski.vladislav.easyenglish.ui.main.adapter.WordsRecyclerViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class WordsListFragment extends Fragment implements View.OnClickListener {

    private WordsRecyclerViewAdapter adapter;
    private ProgressBar prLoadWordsList;
    private DocumentSnapshot lastDocument;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public WordsListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_words_list, container, false);
        RecyclerView rvWordsList = view.findViewById(R.id.rv_words_list);
        FloatingActionButton fabCreate = view.findViewById(R.id.fab_create_words);
        prLoadWordsList = view.findViewById(R.id.pr_words_list);
        fabCreate.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvWordsList.setLayoutManager(layoutManager);
        adapter = new WordsRecyclerViewAdapter(null);
        rvWordsList.setAdapter(adapter);
        if (savedInstanceState == null) {
            loadWords(false);
        }
        return view;
    }

    private void loadWords(boolean shouldReloadAll) {
        if (shouldReloadAll) {
            adapter.clear();
            lastDocument = null;
        }
        Query query;
        if (lastDocument == null) {
            query = db
                    .collection(DataContract.WordsDbAttributes.WORDS_COLLECTION_NAME)
                    .orderBy(DataContract.WordsDbAttributes.FIELD_DATE, Query.Direction.DESCENDING);
        } else {
            query = db
                    .collection(DataContract.WordsDbAttributes.WORDS_COLLECTION_NAME)
                    .orderBy(DataContract.WordsDbAttributes.FIELD_DATE, Query.Direction.DESCENDING)
                    .startAfter(lastDocument);
        }
        prLoadWordsList.setVisibility(View.VISIBLE);
        query
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            prLoadWordsList.setVisibility(View.GONE);
                            List<Words> wordsList = extractWords(task.getResult());
                            if (wordsList != null && wordsList.size() != 0) {
                                adapter.updateWordsList(wordsList);
                            }
                        }
                    }
                });
    }

    private List<Words> extractWords(QuerySnapshot queryDocumentSnapshot) {
        List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshot.getDocuments();
        if (documentSnapshots.size() > 0) {
            lastDocument = queryDocumentSnapshot
                    .getDocuments()
                    .get(queryDocumentSnapshot.size() - 1);
        }
        List<DocumentSnapshot> docs = queryDocumentSnapshot.getDocuments();
        List<Words> wordsList = new ArrayList<>();
        try {
            for (DocumentSnapshot doc : docs) {
                wordsList.add(new Words(doc.getData(), doc.getId()));
            }
        } catch (FirebaseModelParsingException exception) {
            displayFailureMessage(exception);
            return null;
        }
        return wordsList;
    }

    private void displayFailureMessage(Throwable throwable) {
        Log.e("MyLog", throwable.getMessage(), throwable);
        Toast
                .makeText(getContext(), "Failed to load!", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(getContext(), CreateActivity.class));
    }
}